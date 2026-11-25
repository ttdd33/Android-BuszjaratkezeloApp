package hu.nje.dzs_busbusters.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.nje.dzs_busbusters.data.entities.Reservation;
import hu.nje.dzs_busbusters.data.entities.Route;
import hu.nje.dzs_busbusters.data.entities.User;
import hu.nje.dzs_busbusters.data.remote.FirebaseMapper;
import hu.nje.dzs_busbusters.data.remote.FirebaseService;

// Szinkronizálás: letölti a Firebase-ből a felhasználókat, járatokat és foglalásokat,
// és lecseréli velük a helyi Room másolatot. A felület a Room-ot figyeli (LiveData),
// ezért szinkronizálás után magától frissül.
public class SyncService {

    private static final String TAG = "SyncService";

    // Visszahívás: sikerült-e a szinkronizálás (false = nincs internet vagy rossz Firebase cím).
    public interface SyncCallback {
        void onSyncFinished(boolean success);
    }

    private final BusDatabase database;
    private final FirebaseService firebaseService = new FirebaseService();
    private final FirebaseMapper mapper = new FirebaseMapper();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public SyncService(Context context) {
        database = BusDatabase.getDatabase(context);
    }

    // Aszinkron szinkronizálás: háttérszálon fut, az eredményt a fő szálon adja vissza.
    public void sync(SyncCallback callback) {
        BusDatabase.databaseExecutor.execute(() -> {
            boolean success;
            try {
                syncNow();
                success = true;
            } catch (Exception e) {
                Log.e(TAG, "Szinkronizálás sikertelen", e);
                success = false;
            }
            boolean result = success;
            mainHandler.post(() -> callback.onSyncFinished(result));
        });
    }

    // Szinkron változat: csak háttérszálról hívható (a ReservationService foglalás előtt használja).
    public void syncNow() throws Exception {
        // 1. Minden adat letöltése – ha bármelyik nem sikerül, a helyi adatokhoz nem nyúlunk
        List<User> users = mapper.mapUsers(firebaseService.getUsers());
        List<Route> routes = mapper.mapRoutes(firebaseService.getRoutes());

        Set<String> userIds = new HashSet<>();
        for (User user : users) {
            userIds.add(user.getId());
        }
        Set<String> routeIds = new HashSet<>();
        for (Route route : routes) {
            routeIds.add(route.getId());
        }
        List<Reservation> reservations = mapper.mapReservations(
                firebaseService.getReservations(), userIds, routeIds);

        // 2. A helyi másolat cseréje egyetlen tranzakcióban (vagy minden sikerül, vagy semmi)
        database.runInTransaction(() -> {
            database.reservationDao().deleteAllReservations();
            database.routeDao().deleteAllRoutes();
            database.userDao().deleteAllUsers();
            database.userDao().insertUsers(users);
            database.routeDao().insertRoutes(routes);
            database.reservationDao().insertReservations(reservations);
        });
    }
}
