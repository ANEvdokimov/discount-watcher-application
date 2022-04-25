package an.evdokimov.discount.watcher.application;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.service.user.UserService;

public class MainActivity extends AppCompatActivity {
    @Inject
    public UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }
}