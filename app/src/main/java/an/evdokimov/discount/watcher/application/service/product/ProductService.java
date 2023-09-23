package an.evdokimov.discount.watcher.application.service.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ProductService {
    Single<List<ProductPrice>> getAllPrices(@NonNull Long productId);

    Single<List<Product>> getAll(@NonNull Boolean withPriceHistory,
                                 @NonNull Boolean onlyActive,
                                 @Nullable Shop shop,
                                 @Nullable Boolean monitorAvailability,
                                 @Nullable Boolean monitorDiscount,
                                 @Nullable Boolean monitorPriceChanges);

    Completable addProduct(@NonNull NewProduct product);
}
