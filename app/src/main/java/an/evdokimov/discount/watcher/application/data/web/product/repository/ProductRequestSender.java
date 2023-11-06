package an.evdokimov.discount.watcher.application.data.web.product.repository;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.UserProductRequest;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.ProductResponse;
import an.evdokimov.discount.watcher.application.data.web.product.dto.response.UserProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductRequestSender {
    @GET("server/api/product/{id}")
    Call<ProductResponse> getById(@Header("Authorization") @NonNull String token,
                                  @Path("id") @NonNull Long id);

    @GET("server/api/products/by_user/{id}")
    Call<UserProductResponse> getUserProductById(@Header("Authorization") @NonNull String token,
                                                 @Path("id") @NonNull Long id);

    @GET("server/api/products/by_user")
    Call<List<UserProductResponse>> get(@Header("Authorization") String token,
                                        @Header("only-active") Boolean onlyActive,
                                        @Header("shop-id") @Nullable Long shop,
                                        @Header("monitor-availability") @Nullable Boolean monitorAvailability,
                                        @Header("monitor-discount") @Nullable Boolean monitorDiscount,
                                        @Header("monitor-price-changes") @Nullable Boolean monitorPriceChanges);

    @PUT("server/api/products/add_by_cookies")
    Call<Void> addProduct(@Header("Authorization") String token,
                          @Body NewProduct product);

    @POST("server/api/products/by_user")
    Call<Void> updateUserProduct(@Header("Authorization") @NonNull String token,
                                 @Body @NonNull UserProductRequest userProduct);

    @DELETE("server/api/products/by_user/{id}")
    Call<Void> deleteUserProduct(@Header("Authorization") @NonNull String token,
                                 @Path("id") @NonNull Long id);
}
