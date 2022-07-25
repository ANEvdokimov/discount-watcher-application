package an.evdokimov.discount.watcher.application.data.mapper.product;

import org.mapstruct.Mapper;

import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductPriceResponse;

@Mapper
public interface ProductPriceMapper {
    ProductPrice mapFromResponse(ProductPriceResponse response);
}
