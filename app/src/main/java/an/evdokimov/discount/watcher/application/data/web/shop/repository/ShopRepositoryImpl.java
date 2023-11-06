package an.evdokimov.discount.watcher.application.data.web.shop.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
import retrofit2.Call;

public class ShopRepositoryImpl implements ShopRepository {
    private final ShopRequestSender requestSender;

    @Inject
    public ShopRepositoryImpl(ShopRequestSender requestSender) {
        this.requestSender = requestSender;
    }

    @Override
    public Call<List<ShopResponse>> getByUser(@NonNull String token, @Nullable Boolean onlyMy) {
        return requestSender.getByUser(token, onlyMy);
    }
}
