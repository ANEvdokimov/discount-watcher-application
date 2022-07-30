package an.evdokimov.discount.watcher.application.data.web.shop.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
import retrofit2.Call;

public interface ShopRepository {
    Call<List<ShopResponse>> getByUser(@NonNull String token, @Nullable Boolean onlyMy);
}
