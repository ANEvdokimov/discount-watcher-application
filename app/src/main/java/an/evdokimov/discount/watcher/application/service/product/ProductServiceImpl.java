package an.evdokimov.discount.watcher.application.service.product;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.product.dto.repository.ProductRepository;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

@Singleton
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final UserService userService;

    @Inject
    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper,
                              UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Single<List<Product>> getAll() {
        return Single.defer(() -> Single.just(getAllSync(true)));
    }

    private List<Product> getAllSync(boolean isFirstTry) throws IOException, ServerException {
        User user = userService.getActive().blockingGet();

        Response<List<ProductResponse>> response = repository.getAll(user.token).execute();

        if (response.isSuccessful()) {
            return mapper.mapFromResponse(response.body());
        }

        if (response.code() == 401 && isFirstTry) {
            userService.relogin().blockingGet();
            return getAllSync(false);
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
