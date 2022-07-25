package an.evdokimov.discount.watcher.application.data.mapper.product;

import org.mapstruct.Mapper;

import an.evdokimov.discount.watcher.application.data.database.product.model.LentaProductPrice;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.LentaProductPriceResponse;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductPriceResponse;

@Mapper
public abstract class ProductPriceMapper {
    public ProductPrice mapFromResponse(ProductPriceResponse response) {
        if (response instanceof LentaProductPriceResponse) {
            return mapLentaProductPrice((LentaProductPriceResponse) response);
        } else {
            return mapDefaultProductPrice(response);
        }
    }

    public abstract ProductPrice mapDefaultProductPrice(ProductPriceResponse response);

    public abstract LentaProductPrice mapLentaProductPrice(LentaProductPriceResponse response);
}
