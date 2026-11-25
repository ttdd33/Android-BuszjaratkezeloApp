package hu.nje.dzs_busbusters.data.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hu.nje.dzs_busbusters.data.entities.Reservation;
import hu.nje.dzs_busbusters.data.entities.Route;
import hu.nje.dzs_busbusters.data.entities.User;

// A Firebase válaszaiból Room entitásokat készít
public class FirebaseMapper {

    public List<User> mapUsers(Map<String, UserResponse> responses) {
        List<User> users = new ArrayList<>();
        for (Map.Entry<String, UserResponse> entry : responses.entrySet()) {
            UserResponse r = entry.getValue();
            if (r != null && r.username != null && r.password != null && r.name != null) {
                users.add(new User(entry.getKey(), r.username, r.password, r.name));
            }
        }
        return users;
    }

    public List<Route> mapRoutes(Map<String, RouteResponse> responses) {
        List<Route> routes = new ArrayList<>();
        for (Map.Entry<String, RouteResponse> entry : responses.entrySet()) {
            RouteResponse r = entry.getValue();
            if (r != null && r.name != null && r.departureTime != null && r.arrivalTime != null) {
                routes.add(new Route(entry.getKey(), r.name, r.departureTime, r.arrivalTime,
                        r.plateNumber == null ? "" : r.plateNumber, r.capacity, r.otherReserved,
                        r.stops == null ? "" : r.stops));
            }
        }
        return routes;
    }

    // Csak azokat a foglalásokat vesszük át, amelyek létező felhasználóra és járatra mutatnak,
    // különben a Room idegen kulcs ellenőrzése hibát dobna.
    public List<Reservation> mapReservations(Map<String, ReservationResponse> responses,
                                             Set<String> userIds, Set<String> routeIds) {
        List<Reservation> reservations = new ArrayList<>();
        for (Map.Entry<String, ReservationResponse> entry : responses.entrySet()) {
            ReservationResponse r = entry.getValue();
            if (r != null && r.travelDate != null
                    && userIds.contains(r.userId) && routeIds.contains(r.routeId)) {
                reservations.add(new Reservation(entry.getKey(), r.userId, r.routeId, r.travelDate));
            }
        }
        return reservations;
    }
}
