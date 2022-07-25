package an.evdokimov.discount.watcher.application.data.mapper.shop;

import org.mapstruct.Mapper;

import an.evdokimov.discount.watcher.application.data.database.shop.model.ShopChain;
import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopChainResponse;

@Mapper
public interface ShopChainMapper {
    ShopChain mapFromResponse(ShopChainResponse response);
}
