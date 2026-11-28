package hu.nje.dzs_busbusters.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import hu.nje.dzs_busbusters.data.entities.Reservation;
import hu.nje.dzs_busbusters.data.entities.Route;
import hu.nje.dzs_busbusters.data.remote.FirebaseService;
import hu.nje.dzs_busbusters.data.remote.ReservationResponse;

//Helyfoglalás és lemondás üzleti logikája
//Foglalás a Firebase-be kerül (közös a dolgozók közt), majd a helyi Room másolatba
//Háttérszálon dolgozik, eredmény főszálon kerül visszaadásra
public class ReservationService {

    private static final String TAG = "ReservationService";

    //Foglalás lehetséges kimenetelei
    public enum Result {
        SUCCESS,
        FULL,
        ALREADY_RESERVED,
        NETWORK_ERROR
    }

    //Visszahívás (callback): eredményt visszaadjuk a Fragmentnek
    public interface ReservationCallback {
        void onResult(Result result);
    }

    //Visszahívás lemondáshoz: sikerült-e
    public interface CancelCallback {
        void onCancelFinished(boolean success);
    }

    private final BusDatabase database;
    private final SyncService syncService;
    private final FirebaseService firebaseService = new FirebaseService();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public ReservationService(Context context) {
        database = BusDatabase.getDatabase(context);
        syncService = new SyncService(context);
    }

    //Helyfoglalás: friss adatok letöltése, dupla foglalás és férőhely ellenőrzése, mentés a Firebase-be
    public void reserve(String userId, String routeId, String travelDate, ReservationCallback callback) {
        BusDatabase.databaseExecutor.execute(() -> {
            Result result;
            try {
                syncService.syncNow();

                if (database.reservationDao().countUserReservations(userId, routeId, travelDate) > 0) {
                    result = Result.ALREADY_RESERVED;
                } else {
                    Route route = database.routeDao().getRouteById(routeId);
                    int reserved = route.getOtherReserved()
                            + database.reservationDao().countReservations(routeId, travelDate);
                    if (reserved >= route.getCapacity()) {
                        result = Result.FULL;
                    } else {
                        String key = firebaseService.addReservation(
                                new ReservationResponse(userId, routeId, travelDate));
                        database.reservationDao().insertReservation(
                                new Reservation(key, userId, routeId, travelDate));
                        result = Result.SUCCESS;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Foglalás sikertelen", e);
                result = Result.NETWORK_ERROR;
            }
            Result finalResult = result;
            mainHandler.post(() -> callback.onResult(finalResult));
        });
    }

    //Foglalás lemondása: törlés a Firebase-ből, majd helyi másolatból
    public void cancel(String reservationId, CancelCallback callback) {
        BusDatabase.databaseExecutor.execute(() -> {
            boolean success;
            try {
                firebaseService.deleteReservation(reservationId);
                database.reservationDao().deleteReservationById(reservationId);
                success = true;
            } catch (Exception e) {
                Log.e(TAG, "Lemondás sikertelen", e);
                success = false;
            }
            boolean finalSuccess = success;
            mainHandler.post(() -> callback.onCancelFinished(finalSuccess));
        });
    }
}
