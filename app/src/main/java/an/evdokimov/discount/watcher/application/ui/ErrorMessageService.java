package an.evdokimov.discount.watcher.application.ui;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

public class ErrorMessageService {
    private final Application application;

    @Inject
    public ErrorMessageService(Application application) {
        this.application = application;
    }

    public void showErrorMessage(String errorMessage) {
        showErrorMessage(application, errorMessage);
    }

    public void showErrorMessage(Context context, String errorMessage) {
        Toast.makeText(
                context,
                errorMessage,
                Toast.LENGTH_LONG
        ).show();
    }
}
