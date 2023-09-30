package an.evdokimov.discount.watcher.application.configuration.json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

import an.evdokimov.discount.watcher.application.data.database.product.model.PriceChange;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.LentaProductPriceResponse;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductPriceResponse;

public class ProductPriceResponseDeserializer extends StdDeserializer<ProductPriceResponse> {
    public ProductPriceResponseDeserializer() {
        this(null);
    }

    public ProductPriceResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProductPriceResponse deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        JsonNode jsonNode = ctxt.readTree(p);

        if (jsonNode.has("priceWithCard")) {
            return LentaProductPriceResponse.builder()
                    .id(jsonNode.get("id").longValue())
                    .price(jsonNode.get("price").decimalValue())
                    .discount(jsonNode.get("discount").asDouble())
                    .priceWithDiscount(jsonNode.get("priceWithDiscount").decimalValue())
                    .priceWithCard(jsonNode.get("priceWithCard").decimalValue())
                    .isInStock(jsonNode.get("isInStock").booleanValue())
                    .availabilityInformation(jsonNode.get("availabilityInformation").textValue())
                    .date(LocalDateTime.parse(jsonNode.get("date").textValue()))
                    .priceChange(PriceChange.valueOf(jsonNode.get("priceChange").textValue()))
                    .build();
        } else {
            return ProductPriceResponse.builder()
                    .id(jsonNode.get("id").asLong())
                    .price(jsonNode.get("price").decimalValue())
                    .discount(jsonNode.get("discount").asDouble())
                    .priceWithDiscount(jsonNode.get("priceWithDiscount").decimalValue())
                    .isInStock(jsonNode.get("isInStock").booleanValue())
                    .availabilityInformation(jsonNode.get("availabilityInformation").textValue())
                    .date(LocalDateTime.parse(jsonNode.get("date").textValue()))
                    .priceChange(PriceChange.valueOf(jsonNode.get("priceChange").textValue()))
                    .build();
        }
    }
}
