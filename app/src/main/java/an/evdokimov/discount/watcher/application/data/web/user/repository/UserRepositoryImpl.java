package an.evdokimov.discount.watcher.application.data.web.user.repository;

import java.io.IOException;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.user.dao.UserDao;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {
    private final UserRequestSender requestSender;
    private User activeUser;

    @Inject
    public UserRepositoryImpl(UserRequestSender requestSender, UserDao userDao) {
        this.requestSender = requestSender;
        userDao.observeActive()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(user -> activeUser = user);
    }

    public Response<Void> login() throws IOException {
        return requestSender.login(activeUser.login, activeUser.password).execute();
    }

    public Call<Void> login(String login, String password) {
        return requestSender.login(login, password);
    }

    public Call<Void> register(RegisterRequest request) {
        return requestSender.register(request);
    }
}
