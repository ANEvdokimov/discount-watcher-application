package an.evdokimov.discount.watcher.application.data.web.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInformationResponse {
    private Long id;
    private String name;
    private String url;
}
