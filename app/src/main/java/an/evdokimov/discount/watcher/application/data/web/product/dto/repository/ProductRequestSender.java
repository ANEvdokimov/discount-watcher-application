package an.evdokimov.discount.watcher.application.data.web.product.dto.repository;


import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProductRequestSender {
    @GET("api/products")
    Call<List<ProductResponse>> get(@Header("Authorization") String token,
                                    @Header("with-price-history") Boolean withPriceHistory,
                                    @Header("only-active") Boolean onlyActive,
                                    @Header("shop-id") @Nullable Long shop);
}
