package an.evdokimov.discount.watcher.application.service.city;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.city.model.City;
import an.evdokimov.discount.watcher.application.data.mapper.city.CityMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.city.dto.response.CityResponse;
import an.evdokimov.discount.watcher.application.data.web.city.repository.CityRepository;
import an.evdokimov.discount.watcher.application.service.BaseService;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

@Singleton
public class CityServiceImpl extends BaseService<CityResponse> implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Inject
    public CityServiceImpl(CityRepository cityRepository,
                           UserService userService,
                           CityMapper cityMapper) {
        super(userService);
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public Single<List<City>> getAll() {
        return Single.defer(() -> Single.just(getAllSync()));
    }

    private List<City> getAllSync() throws ServerException, IOException {
        Response<List<CityResponse>> response = executeForMultiply(cityRepository::getAll);

        if (response.isSuccessful()) {
            return cityMapper.mapFromResponse(response.body());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
