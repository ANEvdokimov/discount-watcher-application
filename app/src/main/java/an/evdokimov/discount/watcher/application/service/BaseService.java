package an.evdokimov.discount.watcher.application.service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import an.evdokimov.discount.watcher.application.data.web.ServerException;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseService<T> {
    protected final UserService userService;

    protected BaseService(UserService userService) {
        this.userService = userService;
    }

    protected Response<T> execute(Function<String, Call<T>> request)
            throws IOException, ServerException {
        Response<T> response = request.apply(userService.getActive().blockingGet().token).execute();

        if (response.isSuccessful()) {
            return response;
        }

        if (response.code() == 401) {
            String newToken = userService.relogin().blockingGet().token;
            return request.apply(newToken).execute();
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }

    protected Response<List<T>> executeForMultiply(
            Function<String, Call<List<T>>> request) throws IOException, ServerException {
        Response<List<T>> response =
                request.apply(userService.getActive().blockingGet().token).execute();

        if (response.isSuccessful()) {
            return response;
        }

        if (response.code() == 401) {
            String newToken = userService.relogin().blockingGet().token;
            return request.apply(newToken).execute();
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }

    protected Response<Void> executeForVoid(Function<String, Call<Void>> request)
            throws IOException, ServerException {
        Response<Void> response = request.apply(userService.getActive().blockingGet().token).execute();

        if (response.isSuccessful()) {
            return response;
        }

        if (response.code() == 401) {
            String newToken = userService.relogin().blockingGet().token;
            return request.apply(newToken).execute();
        } else {
            throw new ServerException(response.errorBody().string());//TODO parse error message
        }
    }
}
