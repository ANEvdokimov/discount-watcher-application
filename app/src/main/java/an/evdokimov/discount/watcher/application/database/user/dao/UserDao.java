package an.evdokimov.discount.watcher.application.database.user.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Optional;

import an.evdokimov.discount.watcher.application.database.user.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE user.id = :id")
    Optional<User> getById(Long id);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE user.is_active = 1")
        // 0 (false) and 1 (true)
    List<User> getActive();

    @Insert
    void add(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);
}
