package an.evdokimov.discount.watcher.application.data.web.product.repository;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductPriceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductPriceRequestSender {
    @GET("api/prices/byProduct/{productId}")
    Call<List<ProductPriceResponse>> getByProductId(@Header("Authorization") String token,
                                                    @Path("productId") Long productId,
                                                    @Header("group") @Nullable Boolean group,
                                                    @Header("start-date") @Nullable LocalDate startDate);
}
