package an.evdokimov.discount.watcher.application.data.mapper.product;

import org.mapstruct.Mapper;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;

@Mapper
public interface ProductMapper {
    Product mapFromResponse(ProductResponse response);

    List<Product> mapFromResponse(List<ProductResponse> responses);
}
