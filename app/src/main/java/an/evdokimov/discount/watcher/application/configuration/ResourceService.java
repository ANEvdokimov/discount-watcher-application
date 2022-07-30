package an.evdokimov.discount.watcher.application.configuration;

import android.app.Application;

import javax.inject.Inject;

public class ResourceService {
    private final Application application;

    @Inject
    public ResourceService(Application application) {
        this.application = application;
    }

    public String getStringResource(int resourceId) {
        return application.getString(resourceId);
    }
}
