package an.evdokimov.discount.watcher.application.data.web.product.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;

import an.evdokimov.discount.watcher.application.configuration.json.deserializer.ProductPriceResponseDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@JsonDeserialize(using = ProductPriceResponseDeserializer.class)
public class LentaProductPriceResponse extends ProductPriceResponse {
    private BigDecimal priceWithCard;
}
