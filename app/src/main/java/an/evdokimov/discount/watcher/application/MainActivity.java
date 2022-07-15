package an.evdokimov.discount.watcher.application;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.configuration.ExceptionService;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.databinding.ActivityMainBinding;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import an.evdokimov.discount.watcher.application.ui.login.LoginActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Inject
    public UserService userService;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        userService.getActiveAsync()
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

        setContentView(binding.getRoot());
    }

    protected void showContent(User activeUser) {
        setContentView(binding.getRoot());
    }
}