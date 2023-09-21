package an.evdokimov.discount.watcher.application.data.web.product.dto.request;

import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewProduct {
    /**
     * A link to a product page in a shop cite.
     */
    private String url;

    /**
     * A shop cookie from product page.
     */
    private String cookies;

    /**
     * A product discount tracking flag.
     */
    private Boolean monitorDiscount;

    /**
     * A product availability in the shop tracking flag.
     */
    private Boolean monitorAvailability;

    /**
     * A price change tracking flag.
     */
    private Boolean monitorPriceChanges;
}
