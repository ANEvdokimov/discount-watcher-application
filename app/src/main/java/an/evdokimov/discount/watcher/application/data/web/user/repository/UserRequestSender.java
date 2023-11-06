package an.evdokimov.discount.watcher.application.data.web.user.repository;

import an.evdokimov.discount.watcher.application.data.web.user.dto.request.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface UserRequestSender {
    @GET("user_service/login")
    Call<Void> login(@Header("login") String login, @Header("password") String password);

    @PUT("user_service/register")
    Call<Void> register(@Body RegisterRequest request);
}
