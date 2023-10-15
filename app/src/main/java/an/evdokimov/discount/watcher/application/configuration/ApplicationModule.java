package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.web.city.repository.CityRepository;
import an.evdokimov.discount.watcher.application.data.web.city.repository.CityRepositoryImpl;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductPriceRepository;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductPriceRepositoryImpl;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRepository;
import an.evdokimov.discount.watcher.application.data.web.product.repository.ProductRepositoryImpl;
import an.evdokimov.discount.watcher.application.data.web.shop.repository.ShopRepository;
import an.evdokimov.discount.watcher.application.data.web.shop.repository.ShopRepositoryImpl;
import an.evdokimov.discount.watcher.application.data.web.user.repository.UserRepository;
import an.evdokimov.discount.watcher.application.data.web.user.repository.UserRepositoryImpl;
import an.evdokimov.discount.watcher.application.service.city.CityService;
import an.evdokimov.discount.watcher.application.service.city.CityServiceImpl;
import an.evdokimov.discount.watcher.application.service.product.ProductPriceService;
import an.evdokimov.discount.watcher.application.service.product.ProductPriceServiceImpl;
import an.evdokimov.discount.watcher.application.service.product.UserProductService;
import an.evdokimov.discount.watcher.application.service.product.UserProductServiceImpl;
import an.evdokimov.discount.watcher.application.service.shop.ShopService;
import an.evdokimov.discount.watcher.application.service.shop.ShopServiceImpl;
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
    public ProductPriceRepository productPriceRepository(ProductPriceRepositoryImpl priceRepoImpl) {
        return priceRepoImpl;
    }

    @Provides
    @Singleton
    public UserProductService productService(UserProductServiceImpl productServiceImpl) {
        return productServiceImpl;
    }

    @Provides
    @Singleton
    public ProductPriceService productPriceService(ProductPriceServiceImpl priceServiceImpl) {
        return priceServiceImpl;
    }

    @Provides
    @Singleton
    public ShopRepository shopRepository(ShopRepositoryImpl shopRepositoryImpl) {
        return shopRepositoryImpl;
    }

    @Provides
    @Singleton
    public ShopService shopService(ShopServiceImpl shopServiceImpl) {
        return shopServiceImpl;
    }

    @Provides
    @Singleton
    public CityRepository cityRepository(CityRepositoryImpl cityRepositoryImpl) {
        return cityRepositoryImpl;
    }

    @Provides
    @Singleton
    public CityService cityService(CityServiceImpl cityServiceImpl) {
        return cityServiceImpl;
    }
}
