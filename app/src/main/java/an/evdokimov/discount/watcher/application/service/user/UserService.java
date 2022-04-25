package an.evdokimov.discount.watcher.application.service.user;

import java.util.List;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.database.user.model.User;

public class UserService {
    private final UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getActiveUsers() {
        return userDao.getActive();
    }
}
