package an.evdokimov.discount.watcher.application.data.web.user.repository;

import an.evdokimov.discount.watcher.application.data.web.user.dto.request.LoginRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import an.evdokimov.discount.watcher.application.data.web.user.dto.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRequestSender {
    @POST("api/users/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/users/registration")
    Call<LoginResponse> register(@Body RegisterRequest request);
}
