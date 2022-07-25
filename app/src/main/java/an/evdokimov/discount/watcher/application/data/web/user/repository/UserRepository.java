package an.evdokimov.discount.watcher.application.data.web.user.repository;

import java.io.IOException;

import an.evdokimov.discount.watcher.application.data.web.user.dto.request.LoginRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Response;

public interface UserRepository {
    Response<LoginResponse> login() throws IOException;

    Call<LoginResponse> login(LoginRequest request);

    Call<LoginResponse> register(RegisterRequest request);
}
