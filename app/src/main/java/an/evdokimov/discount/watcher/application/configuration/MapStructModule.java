package an.evdokimov.discount.watcher.application.configuration;

import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;

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
}
