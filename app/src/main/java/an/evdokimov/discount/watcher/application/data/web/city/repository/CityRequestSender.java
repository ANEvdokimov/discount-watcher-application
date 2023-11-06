package an.evdokimov.discount.watcher.application.data.web.city.repository;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.city.dto.response.CityResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CityRequestSender {
    @GET("server/api/cities")
    Call<List<CityResponse>> getAll(@Header("Authorization") String token);
}
