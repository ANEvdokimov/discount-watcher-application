package an.evdokimov.discount.watcher.application.service.user;

import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import io.reactivex.rxjava3.core.Maybe;

public interface UserService {
    Maybe<User> getActiveUserAsync();

    Maybe<User> loginAsync(String login, String password);

    Maybe<User> registerAsync(String login, String password);
}
