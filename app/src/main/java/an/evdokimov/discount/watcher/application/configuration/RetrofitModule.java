package an.evdokimov.discount.watcher.application.configuration;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.remote.api.user.repository.UserRepository;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class RetrofitModule {
    private final Retrofit retrofit;

    public RetrofitModule() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.55.15:8080/")
                .client(SelfSignedHttpClient.getUnsafeOkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public UserRepository userRepository() {
        return retrofit.create(UserRepository.class);
    }
}
