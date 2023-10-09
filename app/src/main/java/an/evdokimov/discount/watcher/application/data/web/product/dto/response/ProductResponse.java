package an.evdokimov.discount.watcher.application.data.web.product.dto.response;

import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
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
    private ShopResponse shop;
    private ProductPriceResponse lastPrice;
}
