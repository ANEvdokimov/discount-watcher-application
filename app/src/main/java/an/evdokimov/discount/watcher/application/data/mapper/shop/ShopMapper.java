package an.evdokimov.discount.watcher.application.data.mapper.shop;

import org.mapstruct.Mapper;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;

@Mapper
public interface ShopMapper {
    Shop mapFromResponse(ShopResponse response);
}
