package an.evdokimov.discount.watcher.application.data.database.product.model;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private ProductInformation productInformation;
    private Shop shop;
    private List<ProductPrice> prices;
}