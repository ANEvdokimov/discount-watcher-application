package an.evdokimov.discount.watcher.application.data.web.product.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import retrofit2.Call;

public interface ProductRepository {
    Call<List<ProductResponse>> getAll(@Nullable String token,
                                       @NonNull Boolean withPriceHistory,
                                       @NonNull Boolean onlyActive,
                                       @Nullable Shop shop,
                                       @Nullable Boolean monitorAvailability,
                                       @Nullable Boolean monitorDiscount,
                                       @Nullable Boolean monitorPriceChanges);
}
