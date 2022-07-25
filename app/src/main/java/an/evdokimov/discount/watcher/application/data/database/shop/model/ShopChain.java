package an.evdokimov.discount.watcher.application.data.database.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopChain {
    private Long id;
    private String name;
    private String cyrillicName;
}