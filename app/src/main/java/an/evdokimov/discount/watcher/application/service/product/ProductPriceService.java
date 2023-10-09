package an.evdokimov.discount.watcher.application.service.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import io.reactivex.rxjava3.core.Single;

public interface ProductPriceService {
    Single<List<ProductPrice>> getByProductId(@NonNull Long productId,
                                              @Nullable Boolean group,
                                              @Nullable LocalDate startDate);
}
