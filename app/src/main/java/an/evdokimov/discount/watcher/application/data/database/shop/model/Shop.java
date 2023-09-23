package an.evdokimov.discount.watcher.application.data.database.shop.model;

import java.io.Serializable;

import an.evdokimov.discount.watcher.application.data.database.city.model.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop implements Serializable {
    public Long id;
    private ShopChain shopChain;
    private String name;
    private City city;
    private String address;
}