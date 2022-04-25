package an.evdokimov.discount.watcher.application.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import an.evdokimov.discount.watcher.application.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.database.user.model.User;
import an.evdokimov.discount.watcher.application.database.util.LocalDateTimeConverter;

@Database(entities = {User.class}, version = 1)
@TypeConverters(LocalDateTimeConverter.class)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
