package hu.nje.dzs_busbusters.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Egy buszjárat adatai. Az eredeti a Firebase "routes" ágában van
// itt a Room "routes" táblájában tároljuk a helyi másolatát
@Entity(tableName = "routes")
public class Route {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String departureTime;

    @NonNull
    private String arrivalTime;

    @NonNull
    private String plateNumber;

    private int capacity;

    private int otherReserved;

    @NonNull
    private String stops;

    public Route(@NonNull String id, @NonNull String name, @NonNull String departureTime, @NonNull String arrivalTime,
                 @NonNull String plateNumber, int capacity, int otherReserved, @NonNull String stops) {
        this.id = id;
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.plateNumber = plateNumber;
        this.capacity = capacity;
        this.otherReserved = otherReserved;
        this.stops = stops;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDepartureTime() {
        return departureTime;
    }

    @NonNull
    public String getArrivalTime() {
        return arrivalTime;
    }

    @NonNull
    public String getPlateNumber() {
        return plateNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOtherReserved() {
        return otherReserved;
    }

    @NonNull
    public String getStops() {
        return stops;
    }

    //Ez"05:20 – 05:50" formában mutatja az időtartamot.
    public String getTimeRange() {
        return departureTime + " – " + arrivalTime;
    }
}
