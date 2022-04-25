package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    Application application();
}
