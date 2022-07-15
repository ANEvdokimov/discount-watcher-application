package an.evdokimov.discount.watcher.application.data.web.user.repository;

import java.io.IOException;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.LoginRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.response.LoginResponse;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepository {
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserRequestSender requestSender;
    private User activeUser;

    @Inject
    public UserRepository(UserRequestSender requestSender, UserDao userDao) {
        this.requestSender = requestSender;
        userDao.observeActive()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(user -> activeUser = user);
    }

    public Response<LoginResponse> login() throws IOException {
        return requestSender.login(new LoginRequest(activeUser.login, activeUser.password))
                .execute();
    }

    public Call<LoginResponse> login(LoginRequest request) {
        return requestSender.login(request);
    }

    public Call<LoginResponse> register(RegisterRequest request) {
        return requestSender.register(request);
    }
}
