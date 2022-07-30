package an.evdokimov.discount.watcher.application.data.web.shop.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ShopRequestSender {
    @GET("/api/shops")
    Call<List<ShopResponse>> getByUser(@NonNull @Header("Authorization") String token,
                                       @Nullable @Header("only-my") Boolean onlyMy);
}
