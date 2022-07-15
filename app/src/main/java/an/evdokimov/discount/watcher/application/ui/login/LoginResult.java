package an.evdokimov.discount.watcher.application.ui.login;

import androidx.annotation.Nullable;

import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import lombok.Getter;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    @Getter
    private String ErrorMessage;

    @Nullable
    @Getter
    private User activeUser;

    public LoginResult(User user) {
        activeUser = user;
    }

    public LoginResult(@Nullable String errorMessage) {
        ErrorMessage = errorMessage;
    }
}