package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRepository;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRepositoryImpl;
import an.evdokimov.discount.watcher.application.data.web.user.repository.UserRepository;
import an.evdokimov.discount.watcher.application.data.web.user.repository.UserRepositoryImpl;
import an.evdokimov.discount.watcher.application.service.product.ProductService;
import an.evdokimov.discount.watcher.application.service.product.ProductServiceImpl;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import an.evdokimov.discount.watcher.application.service.user.UserServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application application() {
        return application;
    }

    @Provides
    @Singleton
    public UserRepository userRepository(UserRepositoryImpl userRepositoryImpl) {
        return userRepositoryImpl;
    }

    @Provides
    @Singleton
    public UserService userService(UserServiceImpl userServiceImpl) {
        return userServiceImpl;
    }

    @Provides
    @Singleton
    public ProductRepository productRepository(ProductRepositoryImpl productRepositoryImpl) {
        return productRepositoryImpl;
    }

    @Provides
    @Singleton
    public ProductService productService(ProductServiceImpl productServiceImpl) {
        return productServiceImpl;
    }
}
