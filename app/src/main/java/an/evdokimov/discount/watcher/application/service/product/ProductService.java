package an.evdokimov.discount.watcher.application.service.product;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import io.reactivex.rxjava3.core.Single;

public interface ProductService {
    Single<List<Product>> getAll();
}
