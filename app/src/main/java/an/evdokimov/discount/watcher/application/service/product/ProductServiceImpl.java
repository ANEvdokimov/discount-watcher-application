package an.evdokimov.discount.watcher.application.service.product;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
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
    private final UserService userService;

    @Inject
    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper,
                              UserService userService) {
        super(userService);
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Single<List<Product>> getAll() {
        return Single.defer(() -> Single.just(getAllSync()));
    }

    private List<Product> getAllSync() throws IOException, ServerException {
        Response<List<ProductResponse>> response = executeForMultiply(repository::getAll);

        if (response.isSuccessful()) {
            return mapper.mapFromResponse(response.body());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
