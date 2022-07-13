package an.evdokimov.discount.watcher.application.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.user.model.User;

@Singleton
public class ApplicationContext {
    private User activeUser;

    @Inject
    public ApplicationContext() {
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}
