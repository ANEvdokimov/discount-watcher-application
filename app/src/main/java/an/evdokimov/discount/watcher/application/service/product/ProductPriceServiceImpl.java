package an.evdokimov.discount.watcher.application.service.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductPriceMapper;
import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductPriceResponse;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductPriceRepository;
import an.evdokimov.discount.watcher.application.service.BaseService;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class ProductPriceServiceImpl
        extends BaseService<ProductPriceResponse>
        implements ProductPriceService {
    private final ProductPriceRepository repository;
    private final ProductPriceMapper mapper;

    @Inject
    public ProductPriceServiceImpl(UserService userService,
                                   ProductPriceRepository repository,
                                   ProductPriceMapper mapper) {
        super(userService);
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Single<List<ProductPrice>> getByProductId(@NonNull Long productId,
                                                     @Nullable Boolean group,
                                                     @Nullable LocalDate startDate) {
        return Single.defer(() -> Single.just(getByProductIdSync(productId, group, startDate)));
    }

    public List<ProductPrice> getByProductIdSync(@NonNull Long productId,
                                                 @Nullable Boolean group,
                                                 @Nullable LocalDate startDate)
            throws ServerException, IOException {
        Response<List<ProductPriceResponse>> response = executeForMultiply(token ->
                repository.getByProductId(token, productId, group, startDate));

        if (response.isSuccessful()) {
            return response.body().stream()
                    .map(mapper::mapFromResponse)
                    .collect(Collectors.toList());
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
