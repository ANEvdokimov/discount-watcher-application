package an.evdokimov.discount.watcher.application.service.user;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.database.user.model.User;
import io.reactivex.rxjava3.core.Maybe;

public class UserService {
    private final UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Maybe<User> getActiveUser() {
        return userDao.getActive();
    }
}
