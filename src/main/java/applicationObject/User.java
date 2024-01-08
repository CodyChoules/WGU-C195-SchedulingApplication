package applicationObject;

public class User extends ApplicationObject{

    private int userId;
    private String userName;
    private String userPassword;

    public User(int userId, String userName, String userPassword) {

        this.id = userId;
        this.name = userName;

        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserId() { return id; }

    public String getUserName() { return name; }

    public String getUserPassword() { return userPassword; }

    //Question [] Ask if there are any security concerns with this code. We are technically containing the passwords on local application and it could be retrieved.
}