package an.evdokimov.discount.watcher.application.data.mapper.product;

import org.mapstruct.Mapper;

import an.evdokimov.discount.watcher.application.data.database.product.model.ProductInformation;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductInformationResponse;

@Mapper
public interface ProductInformationMapper {
    ProductInformation mapFromResponse(ProductInformationResponse response);
}
