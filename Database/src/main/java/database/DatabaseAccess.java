package database;

/**
 * The DatabaseAccess interface defines a set of constants representing different
 * levels of access permissions within a database system. These constants are used
 * to specify the access rights of users or roles within the database, ensuring
 * appropriate levels of data access and manipulation based on the user's responsibilities.
 *
 * @author Albert Beaupre
 */
public interface DatabaseAccess {

    /**
     * Allows users to view and retrieve data without the ability to modify it.
     * This is crucial for users who need to analyze data or generate reports
     * but do not need to alter the data itself.
     */
    byte READ = 0x1;

    /**
     * Enables users to insert, update, or delete data.
     * This access level is necessary for users who are responsible for
     * maintaining the accuracy and freshness of the database content.
     */
    byte WRITE = 0x2;

    /**
     * Grants all privileges, including the ability to manage user access,
     * and perform database maintenance tasks.
     * This is typically reserved for database administrators.
     */
    byte ADMIN = 0x4;

}
