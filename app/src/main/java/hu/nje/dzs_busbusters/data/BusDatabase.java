package hu.nje.dzs_busbusters.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hu.nje.dzs_busbusters.data.daos.ReservationDao;
import hu.nje.dzs_busbusters.data.daos.RouteDao;
import hu.nje.dzs_busbusters.data.daos.UserDao;
import hu.nje.dzs_busbusters.data.entities.Reservation;
import hu.nje.dzs_busbusters.data.entities.Route;
import hu.nje.dzs_busbusters.data.entities.User;

// Az alkalmazás helyi Room adatbázisa
// A közös adatok a Firebase-ben vannak; ez a helyi másolatuk, amit a felület LiveData-val figyel,
// így internet nélkül is látszanak a legutóbb letöltött adatok.
@Database(entities = {User.class, Route.class, Reservation.class}, version = 2, exportSchema = false)
public abstract class BusDatabase extends RoomDatabase {
    private static volatile BusDatabase INSTANCE;

    // Közös háttérszál-készlet az adatbázis- és hálózati műveletekhez 
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(2);

    public abstract UserDao userDao();
    public abstract RouteDao routeDao();
    public abstract ReservationDao reservationDao();

    public static BusDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BusDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BusDatabase.class, "bus_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
