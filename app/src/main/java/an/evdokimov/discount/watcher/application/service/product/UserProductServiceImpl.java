package an.evdokimov.discount.watcher.application.service.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.product.model.UserProduct;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductMapper;
import an.evdokimov.discount.watcher.application.data.mapper.product.UserProductMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.UserProductResponse;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRepository;
import an.evdokimov.discount.watcher.application.service.BaseService;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

@Singleton
public class UserProductServiceImpl extends BaseService<UserProductResponse>
        implements UserProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final UserProductMapper userProductMapper;

    @Inject
    public UserProductServiceImpl(ProductRepository repository, ProductMapper productMapper,
                                  UserService userService, UserProductMapper userProductMapper) {
        super(userService);
        this.repository = repository;
        this.productMapper = productMapper;
        this.userProductMapper = userProductMapper;
    }

    @Override
    public Single<List<UserProduct>> getAll(@NonNull Boolean onlyActive,
                                            @Nullable Shop shop,
                                            @Nullable Boolean monitorAvailability,
                                            @Nullable Boolean monitorDiscount,
                                            @Nullable Boolean monitorPriceChanges) {
        return Single.defer(() -> Single.just(getAllSync(
                onlyActive,
                shop,
                monitorAvailability,
                monitorDiscount,
                monitorPriceChanges
        )));
    }

    @Override
    public Completable addProduct(@NonNull NewProduct product) {
        return Completable.fromAction(() -> addProductSync(product));
    }

    private List<UserProduct> getAllSync(@NonNull Boolean onlyActive,
                                         @Nullable Shop shop,
                                         @Nullable Boolean monitorAvailability,
                                         @Nullable Boolean monitorDiscount,
                                         @Nullable Boolean monitorPriceChanges)
            throws IOException, ServerException {
        Response<List<UserProductResponse>> response = executeForMultiply(token -> repository.getAll(
                token,
                onlyActive,
                shop,
                monitorAvailability,
                monitorDiscount,
                monitorPriceChanges
        ));

        if (response.isSuccessful()) {
            return response.body().stream()
                    .map(userProductMapper::mapFromResponse)
                    .collect(Collectors.toList());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }

    private void addProductSync(@NonNull NewProduct product) throws ServerException, IOException {
        Response<Void> response = executeForVoid(token -> repository.addProduct(token, product));

        if (!response.isSuccessful()) {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
