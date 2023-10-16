package an.evdokimov.discount.watcher.application.data.web.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProductRequest {
    private Long id;
    private Long productId;
    private boolean monitorDiscount;
    private boolean monitorAvailability;
    private boolean monitorPriceChanges;
}
