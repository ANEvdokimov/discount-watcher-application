package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.database.ApplicationDatabase;
import an.evdokimov.discount.watcher.application.database.user.dao.UserDao;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private final ApplicationDatabase database;

    public RoomModule(Application application) {
        database = Room.databaseBuilder(
                application, ApplicationDatabase.class, "discount_watcher_database"
        ).build();
    }

    @Provides
    @Singleton
    public ApplicationDatabase applicationDatabase() {
        return database;
    }

    @Provides
    @Singleton
    public UserDao userDao() {
        return database.userDao();
    }
}
