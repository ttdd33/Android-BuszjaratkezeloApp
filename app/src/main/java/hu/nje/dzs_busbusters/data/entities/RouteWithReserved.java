package hu.nje.dzs_busbusters.data.entities;

import androidx.room.Embedded;

// Lekérdezési eredmény: egy járat + az adott napra foglalt helyek száma.
public class RouteWithReserved {

    @Embedded
    private Route route;

    private int reservedCount;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
    }

    public boolean isFull() {
        return reservedCount >= route.getCapacity();
    }
}
