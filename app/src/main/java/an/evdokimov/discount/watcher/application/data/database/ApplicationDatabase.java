package an.evdokimov.discount.watcher.application.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import an.evdokimov.discount.watcher.application.data.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.database.util.LocalDateTimeConverter;

@Database(entities = {User.class}, version = 3)
@TypeConverters(LocalDateTimeConverter.class)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
