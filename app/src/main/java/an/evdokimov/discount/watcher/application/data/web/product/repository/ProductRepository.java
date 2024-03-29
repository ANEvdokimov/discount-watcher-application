package an.evdokimov.discount.watcher.application.data.web.product.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.UserProductRequest;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.UserProductResponse;
import retrofit2.Call;

public interface ProductRepository {

    Call<ProductResponse> getById(@NonNull String token, @NonNull Long id);

    Call<UserProductResponse> getUserProductById(@NonNull String token, @NonNull Long id);

    Call<List<UserProductResponse>> getAll(@NonNull String token,
                                           @NonNull Boolean onlyActive,
                                           @Nullable Shop shop,
                                           @Nullable Boolean monitorAvailability,
                                           @Nullable Boolean monitorDiscount,
                                           @Nullable Boolean monitorPriceChanges);

    Call<Void> addProduct(@NonNull String token, @NonNull NewProduct product);

    Call<Void> update(@NonNull String token, @NonNull UserProductRequest userProduct);

    Call<Void> delete(@NonNull String token, @NonNull Long id);
}
