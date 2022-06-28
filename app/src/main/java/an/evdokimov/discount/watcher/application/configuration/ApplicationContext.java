package an.evdokimov.discount.watcher.application.configuration;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.database.user.model.User;

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
