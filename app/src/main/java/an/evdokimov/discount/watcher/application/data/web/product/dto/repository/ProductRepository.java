package an.evdokimov.discount.watcher.application.data.web.product.dto.repository;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import retrofit2.Call;

public interface ProductRepository {
    Call<List<ProductResponse>> getAll(String token);
}
