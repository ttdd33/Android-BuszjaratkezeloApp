package hu.nje.dzs_busbusters.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hu.nje.dzs_busbusters.data.entities.Reservation;
import hu.nje.dzs_busbusters.data.entities.ReservationWithRoute;

//Foglalásokhoz tartozó helyi adatbázis-műveletek (mentés, törlés, listázás)
@Dao
public interface ReservationDao {

    @Insert
    void insertReservation(Reservation reservation);

    @Insert
    void insertReservations(List<Reservation> reservations);

    @Query("DELETE FROM reservations WHERE id = :reservationId")
    void deleteReservationById(String reservationId);

    @Query("DELETE FROM reservations")
    void deleteAllReservations();

    //Hány foglalás van egy járatra egy adott napon
    @Query("SELECT COUNT(*) FROM reservations WHERE routeId = :routeId AND travelDate = :travelDate")
    int countReservations(String routeId, String travelDate);

    //Foglalt-e már a felhasználó erre a járatra ezen a napon
    @Query("SELECT COUNT(*) FROM reservations " +
            "WHERE userId = :userId AND routeId = :routeId AND travelDate = :travelDate")
    int countUserReservations(String userId, String routeId, String travelDate);

    //Aktuális foglalások: mai és jövőbeli napok, időrendben
    @Query("SELECT res.id AS reservationId, res.travelDate, r.id AS routeId, r.name AS routeName, " +
            "r.departureTime, r.arrivalTime, r.plateNumber " +
            "FROM reservations res INNER JOIN routes r ON res.routeId = r.id " +
            "WHERE res.userId = :userId AND res.travelDate >= :today " +
            "ORDER BY res.travelDate, r.departureTime")
    LiveData<List<ReservationWithRoute>> getActualReservations(String userId, String today);

    //Korábbi foglalások: múltbeli napok, legfrissebb elöl
    @Query("SELECT res.id AS reservationId, res.travelDate, r.id AS routeId, r.name AS routeName, " +
            "r.departureTime, r.arrivalTime, r.plateNumber " +
            "FROM reservations res INNER JOIN routes r ON res.routeId = r.id " +
            "WHERE res.userId = :userId AND res.travelDate < :today " +
            "ORDER BY res.travelDate DESC, r.departureTime")
    LiveData<List<ReservationWithRoute>> getPreviousReservations(String userId, String today);
}
