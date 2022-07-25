package an.evdokimov.discount.watcher.application.data.web.product.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import an.evdokimov.discount.watcher.application.configuration.json.deserializer.ProductPriceResponseDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = ProductPriceResponseDeserializer.class)
public class ProductPriceResponse {
    private Long id;
    private BigDecimal price;
    private Double discount;
    private BigDecimal priceWithDiscount;
    private Boolean isInStock;
    private String availabilityInformation;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
}
