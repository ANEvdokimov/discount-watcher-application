package an.evdokimov.discount.watcher.application.data.database.user.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE user.id = :id")
    Maybe<User> getById(Long id);

    @Query("SELECT * FROM user")
    Maybe<List<User>> getAll();

    @Query("SELECT * FROM user WHERE user.is_active = 1")
        // 0 (false) and 1 (true)
    Flowable<User> observeActive();

    @Query("SELECT * FROM user WHERE user.is_active = 1")
        // 0 (false) and 1 (true)
    Maybe<User> getActive();

    @Query("SELECT * FROM user WHERE user.is_active = 1")
        // 0 (false) and 1 (true)
    User getActiveSync();

    @Query("UPDATE user SET is_active = 0 WHERE is_active = 1")
    void deactivateUsersSync();

    @Query("SELECT * FROM user WHERE login = :login")
    User getByLoginSync(String login);

    @Insert
    Completable add(User... users);

    @Insert
    void addSync(User... users);

    @Update
    Completable update(User... users);

    @Update
    void updateSync(User... users);

    @Delete
    Completable delete(User... users);
}
