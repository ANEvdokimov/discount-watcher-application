package an.evdokimov.discount.watcher.application.data.mapper.product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import an.evdokimov.discount.watcher.application.data.database.product.model.UserProduct;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.UserProductResponse;

@Mapper
public abstract class UserProductMapper {
    private final ProductMapper productMapper;

    protected UserProductMapper() {
        this.productMapper = Mappers.getMapper(ProductMapper.class);
    }

    public UserProduct mapFromResponse(UserProductResponse response) {
        if (response == null) {
            return null;
        }

        return UserProduct.builder()
                .id(response.getId())
                .product(productMapper.mapFromResponse(response.getProduct()))
                .monitorDiscount(response.isMonitorDiscount())
                .monitorAvailability(response.isMonitorAvailability())
                .monitorPriceChanges(response.isMonitorPriceChanges())
                .build();
    }
}
