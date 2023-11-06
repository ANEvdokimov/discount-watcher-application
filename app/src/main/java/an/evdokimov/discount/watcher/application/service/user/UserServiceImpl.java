package an.evdokimov.discount.watcher.application.service.user;

import java.io.IOException;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import an.evdokimov.discount.watcher.application.data.web.user.repository.UserRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import retrofit2.Response;

public class UserServiceImpl implements UserService {
    private final UserDao dao;
    private final UserRepository repository;

    @Inject
    public UserServiceImpl(UserDao userDao, UserRepository userRepository) {
        this.dao = userDao;
        this.repository = userRepository;
    }

    public Flowable<User> observeActiveUser() {
        return dao.observeActive();
    }

    public Maybe<User> getActive() {
        return dao.getActive();
    }

    public Maybe<User> login(String login, String password) {
        return Maybe.defer(() -> Maybe.just(loginSync(login, password)));
    }

    public Maybe<User> relogin() {
        User activeUser = dao.getActive().blockingGet();
        return login(activeUser.login, activeUser.password);
    }

    private User loginSync(String login, String password) throws IOException, ServerException {
        Response<Void> response =
                repository.login(login, password).execute();

        if (response.isSuccessful()) {
            return saveNewActiveUser(login, password, response.headers().get("Authorization"));
        } else {
            throw new ServerException(response.errorBody().string());
        }
    }

    public Maybe<User> register(String login, String password) {
        return Maybe.defer(() -> Maybe.just(registerSync(login, password)));
    }

    private User registerSync(String login, String password) throws IOException, ServerException {
        Response<Void> response =
                repository.register(new RegisterRequest(login, password)).execute();

        if (response.isSuccessful()) {
            return saveNewActiveUser(login, password, response.headers().get("Authorization"));
        } else {
            throw new ServerException(response.errorBody().string());
        }
    }

    private User saveNewActiveUser(String login, String password, String token) {
        dao.deactivateUsersSync();
        User user = dao.getByLoginSync(login);
        if (user == null) {
            User newUser = User.builder()
                    .login(login)
                    .password(password)
                    .token(token)
                    .isActive(true)
                    .build();
            dao.addSync(newUser);
            user = newUser;//TODO get updated user
        } else {
            user.token = token;
            user.password = password;
            user.isActive = true;
            dao.updateSync(user);
        }
        return user;
    }
}
