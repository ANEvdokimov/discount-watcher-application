package an.evdokimov.discount.watcher.application.service.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.product.model.UserProduct;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserProductService {
    Single<UserProduct> getUserProductById(@NonNull Long id);

    Single<List<UserProduct>> getAll(@NonNull Boolean onlyActive,
                                     @Nullable Shop shop,
                                     @Nullable Boolean monitorAvailability,
                                     @Nullable Boolean monitorDiscount,
                                     @Nullable Boolean monitorPriceChanges);

    Completable addProduct(@NonNull NewProduct product);

    Completable update(@NonNull UserProduct userProduct);

    Completable delete(@NonNull Long userProductId);
}
