package an.evdokimov.discount.watcher.application.ui.login;

import androidx.annotation.Nullable;

import lombok.Getter;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    @Getter
    private String ErrorMessage;

    public LoginResult() {
    }

    public LoginResult(@Nullable String errorMessage) {
        ErrorMessage = errorMessage;
    }
}