package an.evdokimov.discount.watcher.application.service.city;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.city.model.City;
import io.reactivex.rxjava3.core.Single;

public interface CityService {
    Single<List<City>> getAll();
}
