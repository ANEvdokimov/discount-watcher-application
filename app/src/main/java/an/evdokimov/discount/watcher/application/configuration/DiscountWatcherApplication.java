package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

public class DiscountWatcherApplication extends Application {
    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .retrofitModule(new RetrofitModule())
                .mapStructModule(new MapStructModule())
                .build();

        super.onCreate();
    }
}
