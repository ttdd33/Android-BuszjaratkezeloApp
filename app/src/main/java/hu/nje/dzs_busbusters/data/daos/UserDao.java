package hu.nje.dzs_busbusters.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hu.nje.dzs_busbusters.data.entities.User;

// A felhasználókhoz tartozó helyi (Room) adatbázis-műveletek.
@Dao
public interface UserDao {

    // Bejelentkezés: visszaadja a felhasználót, ha a név és a jelszó stimmel, különben null.
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Insert
    void insertUsers(List<User> users);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
