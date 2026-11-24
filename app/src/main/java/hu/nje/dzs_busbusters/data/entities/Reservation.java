package hu.nje.dzs_busbusters.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//Helyfoglalás: melyik dolgozó, melyik járatra, melyik napra
@Entity(tableName = "reservations",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Route.class,
                        parentColumns = "id",
                        childColumns = "routeId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(value = {"userId", "routeId", "travelDate"}, unique = true),
                @Index(value = "routeId")
        })
public class Reservation {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String userId;

    @NonNull
    private String routeId;

    @NonNull
    private String travelDate;

    public Reservation(@NonNull String id, @NonNull String userId, @NonNull String routeId, @NonNull String travelDate) {
        this.id = id;
        this.userId = userId;
        this.routeId = routeId;
        this.travelDate = travelDate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getRouteId() {
        return routeId;
    }

    @NonNull
    public String getTravelDate() {
        return travelDate;
    }
}
