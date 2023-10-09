package an.evdokimov.discount.watcher.application.data.database.product.model;

import java.io.Serializable;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private Long id;
    private ProductInformation productInformation;
    private Shop shop;
    private ProductPrice lastPrice;
}