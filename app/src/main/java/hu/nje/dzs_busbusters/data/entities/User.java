package hu.nje.dzs_busbusters.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// Egy dolgozó (felhasználó) adatai. Az eredeti a Firebase "users" ágában van,
// itt a Room "users" táblájában a helyi másolatát tároljuk.
@Entity(tableName = "users",
        indices = {@Index(value = "username", unique = true)})
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String name;

    public User(@NonNull String id, @NonNull String username, @NonNull String password, @NonNull String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
