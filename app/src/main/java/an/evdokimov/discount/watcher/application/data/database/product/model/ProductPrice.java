package an.evdokimov.discount.watcher.application.data.database.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice implements Serializable {
    private Long id;
    private BigDecimal price;
    private Double discount;
    private BigDecimal priceWithDiscount;
    private boolean isInStock;
    private String availabilityInformation;
    private LocalDateTime date;
}