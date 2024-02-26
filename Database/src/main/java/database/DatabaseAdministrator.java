package database;

/**
 * @author Albert Beaupre
 */
public class DatabaseAdministrator {

    /**
     * This is used as a default value for invalid administrators.
     */
    public static final DatabaseAdministrator INVALID = new DatabaseAdministrator("", "");

    static {
        INVALID.revokeAccess();
    }

    private final String username;
    private String token;
    private boolean grantedAccess;

    public DatabaseAdministrator(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void grantAccess() {
        this.grantedAccess = true;
    }

    public void revokeAccess() {
        this.grantedAccess = false;
    }

    public boolean isGrantedAccess() {
        return grantedAccess;
    }


}
