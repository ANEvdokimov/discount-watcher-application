package an.evdokimov.discount.watcher.application.data.web.product.dto.repository;

import java.util.List;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import retrofit2.Call;

public class ProductRepositoryImpl implements ProductRepository {
    private final ProductRequestSender requestSender;

    @Inject
    public ProductRepositoryImpl(ProductRequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public Call<List<ProductResponse>> getAll(String token) {
        return requestSender.get("Bearer " + token, false, true, null);
    }
}
