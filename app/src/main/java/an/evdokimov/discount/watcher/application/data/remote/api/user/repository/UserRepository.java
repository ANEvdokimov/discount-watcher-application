package an.evdokimov.discount.watcher.application.data.remote.api.user.repository;

import an.evdokimov.discount.watcher.application.data.remote.api.user.dto.request.LoginRequest;
import an.evdokimov.discount.watcher.application.data.remote.api.user.dto.request.RegisterRequest;
import an.evdokimov.discount.watcher.application.data.remote.api.user.dto.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRepository {
    @POST("api/users/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/users/registration")
    Call<LoginResponse> register(@Body RegisterRequest request);
}
