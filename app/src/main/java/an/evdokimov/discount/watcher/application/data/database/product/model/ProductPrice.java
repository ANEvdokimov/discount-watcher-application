package an.evdokimov.discount.watcher.application.data.database.product.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice {
    private Long id;
    private BigDecimal price;
    private Double discount;
    private BigDecimal priceWithDiscount;
    private boolean isInStock;
    private String availabilityInformation;
    private LocalDateTime date;
}