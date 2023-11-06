package an.evdokimov.discount.watcher.application.data.database;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Active user not found in the database");
    }
}
