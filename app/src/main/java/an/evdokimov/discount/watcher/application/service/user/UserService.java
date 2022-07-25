package an.evdokimov.discount.watcher.application.service.user;

import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

public interface UserService {
    Flowable<User> observeActiveUser();

    Maybe<User> getActive();

    Maybe<User> login(String login, String password);

    Maybe<User> relogin();

    Maybe<User> register(String login, String password);
}
