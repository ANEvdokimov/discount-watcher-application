package an.evdokimov.discount.watcher.application.service.shop;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import io.reactivex.rxjava3.core.Single;

public interface ShopService {
    Single<List<Shop>> getAllUserShops();
}
