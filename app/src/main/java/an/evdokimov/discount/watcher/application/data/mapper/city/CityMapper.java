package an.evdokimov.discount.watcher.application.data.mapper.city;

import org.mapstruct.Mapper;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.city.model.City;
import an.evdokimov.discount.watcher.application.data.web.city.dto.response.CityResponse;

@Mapper
public interface CityMapper {
    City mapFromResponse(CityResponse response);

    List<City> mapFromResponse(List<CityResponse> response);
}
