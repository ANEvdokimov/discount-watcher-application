package an.evdokimov.discount.watcher.application.ui.login;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.configuration.ApplicationContext;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;

public class LoginViewModel extends ViewModel {
    @Getter
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    @Getter
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final UserService userService;
    private final ApplicationContext context;

    @Inject
    LoginViewModel(UserService userService, ApplicationContext context) {
        this.userService = userService;
        this.context = context;
    }

    public void login(String username, String password) {
        userService.loginAsync(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::successLogin,
                        this::error
                );
    }

    public void register(String username, String password) {
        userService.registerAsync(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::successLogin,
                        this::error
                );
    }

    private void successLogin(User user) {
        context.setActiveUser(user);
        loginResult.setValue(new LoginResult());
    }

    private void error(Throwable throwable) {
        loginResult.setValue(new LoginResult(throwable.getMessage()));
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}