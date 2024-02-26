package database.server;

/**
 * @author Albert Beaupre
 */
public class DatabaseSubUser implements DatabaseAccess {

    private String name;
    private byte accessRights;

    public byte getAccessRights() {
        return accessRights;
    }

    public boolean hasRights(int rights) {
        return (this.accessRights & rights) == 0;
    }

    public String getName() {
        return name;
    }
}
