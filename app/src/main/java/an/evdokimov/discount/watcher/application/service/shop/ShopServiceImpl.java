package an.evdokimov.discount.watcher.application.service.shop;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.mapper.shop.ShopMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
import an.evdokimov.discount.watcher.application.data.web.shop.repository.ShopRepository;
import an.evdokimov.discount.watcher.application.service.BaseService;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class ShopServiceImpl extends BaseService<ShopResponse> implements ShopService {
    private final ShopRepository repository;
    private final ShopMapper mapper;

    @Inject
    public ShopServiceImpl(ShopRepository repository,
                           UserService userService,
                           ShopMapper mapper) {
        super(userService);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Shop>> getAllUserShops() {
        return Single.defer(() -> Single.just(getAllUserShopsSync()));
    }

    private List<Shop> getAllUserShopsSync() throws IOException, ServerException {
        Response<List<ShopResponse>> response =
                executeForMultiply(token -> repository.getByUser(token, true));

        if (response.isSuccessful()) {
            return mapper.mapFromResponse(response.body());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
