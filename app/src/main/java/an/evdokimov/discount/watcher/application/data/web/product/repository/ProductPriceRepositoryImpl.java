package an.evdokimov.discount.watcher.application.data.web.product.repository;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductPriceResponse;
import retrofit2.Call;

public class ProductPriceRepositoryImpl implements ProductPriceRepository {
    private final ProductPriceRequestSender requestSender;

    @Inject
    public ProductPriceRepositoryImpl(ProductPriceRequestSender requestSender) {
        this.requestSender = requestSender;
    }

    @Override
    public Call<List<ProductPriceResponse>> getByProductId(String token,
                                                           Long productId,
                                                           @Nullable Boolean group,
                                                           @Nullable LocalDate startDate) {
        return requestSender.getByProductId("Bearer " + token, productId, group, startDate);
    }
}
