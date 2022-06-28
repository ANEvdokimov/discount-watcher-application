package an.evdokimov.discount.watcher.application;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.configuration.ApplicationContext;
import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.configuration.ExceptionService;
import an.evdokimov.discount.watcher.application.database.user.model.User;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Inject
    public UserService userService;

    @Inject
    public ApplicationContext context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        userService.getActiveUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::showContent,
                        ExceptionService::throwException,
                        this::openLoginActivityBeforeShowingContent
                );
    }

    protected void openLoginActivityBeforeShowingContent() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        setContentView(R.layout.activity_main);
    }

    protected void showContent(User activeUser) {
        context.setActiveUser(activeUser);
        setContentView(R.layout.activity_main);
    }
}