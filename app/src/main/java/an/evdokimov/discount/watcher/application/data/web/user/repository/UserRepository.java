package an.evdokimov.discount.watcher.application.data.web.user.repository;

import java.io.IOException;

import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import retrofit2.Call;
import retrofit2.Response;

public interface UserRepository {
    Response<Void> login() throws IOException;

    Call<Void> login(String login, String password);

    Call<Void> register(RegisterRequest request);
}
