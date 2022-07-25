package an.evdokimov.discount.watcher.application.data.web.product.dto.response;

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
public class ProductResponse {
    private Long id;
    private ProductInformationResponse productInformation;
    private Shop shop;
    private List<ProductPriceResponse> prices;
}
