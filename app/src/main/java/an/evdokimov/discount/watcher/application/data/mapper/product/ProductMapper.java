package an.evdokimov.discount.watcher.application.data.mapper.product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

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

    public Product mapFromResponse(ProductResponse response) {
        Product product = new Product();

        product.setId(response.getId());
        product.setShop(shopMapper.mapFromResponse(response.getShop()));
        product.setProductInformation(
                productInformationMapper.mapFromResponse(response.getProductInformation()));
        product.setPrices(response.getPrices().stream()
                .map(productPriceMapper::mapFromResponse)
                .collect(Collectors.toList()));

        return product;
    }

    public abstract List<Product> mapFromResponse(List<ProductResponse> responses);
}
