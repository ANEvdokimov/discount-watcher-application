package an.evdokimov.discount.watcher.application.configuration;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRequestSender;
import an.evdokimov.discount.watcher.application.data.web.shop.repository.ShopRequestSender;
import an.evdokimov.discount.watcher.application.data.web.user.repository.UserRequestSender;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class RetrofitModule {
    private final Retrofit retrofit;

    public RetrofitModule() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.55.14:8080/")
                .client(SelfSignedHttpClient.getUnsafeOkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public UserRequestSender userRequestSender() {
        return retrofit.create(UserRequestSender.class);
    }

    @Provides
    @Singleton
    public ProductRequestSender productRequestSender() {
        return retrofit.create(ProductRequestSender.class);
    }

    @Provides
    @Singleton
    public ShopRequestSender shopRequestSender() {
        return retrofit.create(ShopRequestSender.class);
    }
}
