package an.evdokimov.discount.watcher.application.ui.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListItem {
    private String productName;
    private String shopName;
    private String availabilityInformation;
    private String date;
    private String priceWithDiscount;
    private String discount;
    private String price;
}
