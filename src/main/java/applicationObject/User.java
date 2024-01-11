package applicationObject;


/**
 * Represents a User within the application. This is primarily used for the current user.
 */
public class User extends ApplicationObject{

    private final String userPassword;


    /**
     * Constructs a new User instance with the specified user ID, username, and password.
     *
     * @param userId       The unique identifier for the user.
     * @param userName     The username of the user.
     * @param userPassword The password associated with the user.
     */
    public User(int userId, String userName, String userPassword) {

        this.id = userId;
        this.name = userName;

        this.userPassword = userPassword;
    }

    /**
     * Gets the user's Id
     *
     * @return The user Id
     */
    public int getUserId() { return id; }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() { return name; }

    /**
     * Gets the password of the user.
     *
     * @return The password associated with the user.
     */
    public String getUserPassword() { return userPassword; }

    //Question [] Ask if there are any security concerns with this code. We are technically containing the password of our user on local application memory and it could be retrieved.
}