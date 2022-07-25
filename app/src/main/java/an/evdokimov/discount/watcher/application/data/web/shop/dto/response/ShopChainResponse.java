package an.evdokimov.discount.watcher.application.data.web.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopChainResponse {
    private Long id;
    private String name;
    private String cyrillicName;
}
