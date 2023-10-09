package an.evdokimov.discount.watcher.application.data.mapper.product;

import androidx.annotation.Nullable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.mapper.shop.ShopMapper;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;

@Mapper
public abstract class ProductMapper {
    private final ProductPriceMapper productPriceMapper;
    private final ProductInformationMapper productInformationMapper;
    private final ShopMapper shopMapper;

    protected ProductMapper() {
        this.productPriceMapper = Mappers.getMapper(ProductPriceMapper.class);
        this.productInformationMapper = Mappers.getMapper(ProductInformationMapper.class);
        this.shopMapper = Mappers.getMapper(ShopMapper.class);
    }

    public Product mapFromResponse(@Nullable ProductResponse response) {
        if (response == null) {
            return null;
        }

        Product product = new Product();

        product.setId(response.getId());
        product.setShop(shopMapper.mapFromResponse(response.getShop()));
        product.setProductInformation(
                productInformationMapper.mapFromResponse(response.getProductInformation()));
        product.setLastPrice(productPriceMapper.mapFromResponse(response.getLastPrice()));

        return product;
    }

    public abstract List<Product> mapFromResponse(List<ProductResponse> responses);
}
