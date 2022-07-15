package an.evdokimov.discount.watcher.application.service.user;

import java.io.IOException;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.LoginRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.response.LoginResponse;
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

    public Maybe<User> getActiveAsync() {
        return dao.getActive();
    }

    public Maybe<User> loginAsync(String login, String password) {
        return Maybe.defer(() -> loginSync(login, password));
    }

    public Maybe<User> registerAsync(String login, String password) {
        return Maybe.defer(() -> registerSync(login, password));
    }

    private Maybe<User> loginSync(String login, String password) {
        Response<LoginResponse> response;
        try {
            response = repository.login(new LoginRequest(login, password)).execute();
        } catch (IOException e) {
            return Maybe.error(e);
        }
        if (response.isSuccessful()) {
            return saveNewActiveUser(login, password, response.body().getToken());
        } else {
            try {
                return Maybe.error(new ServerException(response.errorBody().string()));//todo parse error message
            } catch (IOException e) {
                return Maybe.error(e);
            }
        }
    }

    private Maybe<User> registerSync(String login, String password) {
        Response<LoginResponse> response;
        try {
            response = repository.register(new RegisterRequest(login, login, password)).execute();
        } catch (IOException e) {
            return Maybe.error(e);
        }
        if (response.isSuccessful()) {
            return saveNewActiveUser(login, password, response.body().getToken());
        } else {
            try {
                return Maybe.error(new ServerException(response.errorBody().string()));//todo parse error message
            } catch (IOException e) {
                return Maybe.error(e);
            }
        }
    }

    private Maybe<User> saveNewActiveUser(String login, String password, String token) {
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
            dao.updateSync(user);
        }
        return Maybe.just(user);
    }
}
