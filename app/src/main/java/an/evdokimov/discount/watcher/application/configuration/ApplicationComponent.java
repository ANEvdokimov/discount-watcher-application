package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.ui.main.MainActivity;
import an.evdokimov.discount.watcher.application.ui.login.LoginActivity;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        RoomModule.class,
        RetrofitModule.class,
        MapStructModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    Application application();
}
