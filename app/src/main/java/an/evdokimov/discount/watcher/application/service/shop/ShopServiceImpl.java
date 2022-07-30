package an.evdokimov.discount.watcher.application.service.shop;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.mapper.shop.ShopMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
import an.evdokimov.discount.watcher.application.data.web.shop.repository.ShopRepository;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class ShopServiceImpl implements ShopService {
    private final ShopRepository repository;
    private final UserService userService;
    private final ShopMapper mapper;

    @Inject
    public ShopServiceImpl(ShopRepository repository,
                           UserService userService,
                           ShopMapper mapper) {
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Shop>> getAllUserShops() {
        return Single.defer(() -> Single.just(getAllUserShopsSync()));
    }

    private List<Shop> getAllUserShopsSync() throws IOException, ServerException {
        User user = userService.getActive().blockingGet();
        Response<List<ShopResponse>> response =
                repository.getByUser(user.token, true).execute();

        if (response.isSuccessful()) {
            return response.body().stream()
                    .map(mapper::mapFromResponse)
                    .collect(Collectors.toList());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
