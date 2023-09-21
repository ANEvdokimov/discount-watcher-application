package an.evdokimov.discount.watcher.application.data.web.product.repository;


import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ProductRequestSender {
    @GET("api/products")
    Call<List<ProductResponse>> get(@Header("Authorization") String token,
                                    @Header("with-price-history") Boolean withPriceHistory,
                                    @Header("only-active") Boolean onlyActive,
                                    @Header("shop-id") @Nullable Long shop,
                                    @Header("monitor-availability") @Nullable Boolean monitorAvailability,
                                    @Header("monitor-discount") @Nullable Boolean monitorDiscount,
                                    @Header("monitor-price-changes") @Nullable Boolean monitorPriceChanges);

    @PUT("api/products/add_by_cookies")
    Call<Void> addProduct(@Header("Authorization") String token,
                          @Body NewProduct product);
}
