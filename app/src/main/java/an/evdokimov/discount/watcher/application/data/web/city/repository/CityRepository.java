package an.evdokimov.discount.watcher.application.data.web.city.repository;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.city.dto.response.CityResponse;
import retrofit2.Call;

public interface CityRepository {
    Call<List<CityResponse>> getAll(String token);
}
