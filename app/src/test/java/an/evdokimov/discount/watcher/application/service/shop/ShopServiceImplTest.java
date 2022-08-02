package an.evdokimov.discount.watcher.application.service.shop;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.data.mapper.shop.ShopMapper;
import an.evdokimov.discount.watcher.application.data.web.shop.dto.response.ShopResponse;
import an.evdokimov.discount.watcher.application.data.web.shop.repository.ShopRepository;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import io.reactivex.rxjava3.core.Maybe;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ShopServiceImplTest {

    @Test
    public void getAllUserShops_relogin_listOfShops() throws IOException {
        UserService userServiceMock = mock(UserService.class);
        ShopRepository shopRepositoryMock = mock(ShopRepository.class);
        ShopMapper shopMapperMock = mock(ShopMapper.class);

        ShopResponse shopResponse = ShopResponse.builder().id(666L).build();
        Shop expectedShop = Shop.builder().id(666L).build();

        when(shopMapperMock.mapFromResponse(shopResponse)).thenReturn(expectedShop);

        Call invalidCallMock = mock(Call.class);
        Response invalidResponse = Response.error(
                401,
                ResponseBody.create(MediaType.get("text/plain"), "ERROR")
        );
        when(invalidCallMock.execute()).thenReturn(invalidResponse);

        Call validCallMock = mock(Call.class);
        Response validResponse = Response.success(List.of(shopResponse));
        when(validCallMock.execute()).thenReturn(validResponse);

        String invalidToken = "invalid_token";
        String validToken = "valid_token";

        User invalidUser = User.builder().id(1L).token(invalidToken).build();
        User validUser = User.builder().id(1L).token(validToken).build();

        when(userServiceMock.getActive()).thenReturn(Maybe.just(invalidUser));
        when(userServiceMock.relogin()).thenReturn(Maybe.just(validUser));

        when(shopRepositoryMock.getByUser(eq(invalidToken), anyBoolean()))
                .thenReturn(invalidCallMock);
        when(shopRepositoryMock.getByUser(eq(validToken), anyBoolean()))
                .thenReturn(validCallMock);

        ShopServiceImpl shopService =
                new ShopServiceImpl(shopRepositoryMock, userServiceMock, shopMapperMock);

        List<Shop> shops = shopService.getAllUserShops().blockingGet();

        assertEquals(expectedShop, shops.get(0));
    }
}