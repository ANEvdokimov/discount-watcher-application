package an.evdokimov.discount.watcher.application.service.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRepository;
import an.evdokimov.discount.watcher.application.service.BaseService;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

@Singleton
public class ProductServiceImpl extends BaseService<ProductResponse> implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Inject
    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper,
                              UserService userService) {
        super(userService);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Product>> getAll(@NonNull Boolean withPriceHistory,
                                        @NonNull Boolean onlyActive,
                                        @Nullable Shop shop,
                                        @Nullable Boolean monitorAvailability,
                                        @Nullable Boolean monitorDiscount,
                                        @Nullable Boolean monitorPriceChanges) {
        return Single.defer(() -> Single.just(getAllSync(
                withPriceHistory,
                onlyActive,
                shop,
                monitorAvailability,
                monitorDiscount,
                monitorPriceChanges
        )));
    }

    private List<Product> getAllSync(@NonNull Boolean withPriceHistory,
                                     @NonNull Boolean onlyActive,
                                     @Nullable Shop shop,
                                     @Nullable Boolean monitorAvailability,
                                     @Nullable Boolean monitorDiscount,
                                     @Nullable Boolean monitorPriceChanges)
            throws IOException, ServerException {
        Response<List<ProductResponse>> response = executeForMultiply(token -> repository.getAll(
                token,
                withPriceHistory,
                onlyActive,
                shop,
                monitorAvailability,
                monitorDiscount,
                monitorPriceChanges
        ));

        if (response.isSuccessful()) {
            return mapper.mapFromResponse(response.body());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
