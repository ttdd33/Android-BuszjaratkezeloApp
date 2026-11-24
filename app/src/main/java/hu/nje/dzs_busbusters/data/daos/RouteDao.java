package hu.nje.dzs_busbusters.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hu.nje.dzs_busbusters.data.entities.Route;
import hu.nje.dzs_busbusters.data.entities.RouteWithReserved;

//A jáaratokhoz tartozó helyi (Room) adatbázis-műveletek
@Dao
public interface RouteDao {

    // Az összes járat az adott napra, a foglalt helyek számával együtt, indulási idő szerint rendezve.
    // Foglalt = más rendszerből foglalt helyek + az alkalmazásban erre a napra leadott foglalások száma.
    @Query("SELECT r.*, " +
            "(r.otherReserved + (SELECT COUNT(*) FROM reservations res " +
            "WHERE res.routeId = r.id AND res.travelDate = :travelDate)) AS reservedCount " +
            "FROM routes r ORDER BY r.departureTime")
    LiveData<List<RouteWithReserved>> getRoutesForDate(String travelDate);

    //Egy járat adatai az adott napra (a részletes nézethez)
    @Query("SELECT r.*, " +
            "(r.otherReserved + (SELECT COUNT(*) FROM reservations res " +
            "WHERE res.routeId = r.id AND res.travelDate = :travelDate)) AS reservedCount " +
            "FROM routes r WHERE r.id = :routeId")
    LiveData<RouteWithReserved> getRouteForDate(String routeId, String travelDate);

    //Egy járat szinkron lekérdezése ( azaz a foglaláskor a férőhely ellenőrzéséhez)
    @Query("SELECT * FROM routes WHERE id = :routeId")
    Route getRouteById(String routeId);

    @Insert
    void insertRoutes(List<Route> routes);

    @Query("DELETE FROM routes")
    void deleteAllRoutes();
}
