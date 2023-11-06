package an.evdokimov.discount.watcher.application.data.web.city.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.web.city.dto.response.CityResponse;
import retrofit2.Call;

@Singleton
public class CityRepositoryImpl implements CityRepository {
    private final CityRequestSender requestSender;

    @Inject
    public CityRepositoryImpl(CityRequestSender requestSender) {
        this.requestSender = requestSender;
    }

    @Override
    public Call<List<CityResponse>> getAll(String token) {
        return requestSender.getAll(token);
    }
}
