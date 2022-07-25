package an.evdokimov.discount.watcher.application.data.database.shop.model;

import an.evdokimov.discount.watcher.application.data.web.city.dto.response.CityResponse;
import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopChainResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    public Long id;
    private ShopChainResponse shopChain;
    private String name;
    private CityResponse city;
    private String address;
}