package an.evdokimov.discount.watcher.application.configuration;

import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.mapper.city.CityMapper;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductMapper;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductPriceMapper;
import an.evdokimov.discount.watcher.application.data.mapper.product.UserProductMapper;
import an.evdokimov.discount.watcher.application.data.mapper.shop.ShopMapper;
import an.evdokimov.discount.watcher.application.data.mapper.user.UserMapper;
import dagger.Module;
import dagger.Provides;

@Module
public class MapStructModule {
    @Provides
    @Singleton
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Provides
    @Singleton
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Provides
    @Singleton
    public UserProductMapper userProductMapper() {
        return Mappers.getMapper(UserProductMapper.class);
    }

    @Provides
    @Singleton
    public ProductPriceMapper productPriceMapper() {
        return Mappers.getMapper(ProductPriceMapper.class);
    }

    @Provides
    @Singleton
    public ShopMapper shopMapper() {
        return Mappers.getMapper(ShopMapper.class);
    }

    @Provides
    @Singleton
    public CityMapper cityMapper() {
        return Mappers.getMapper(CityMapper.class);
    }
}
