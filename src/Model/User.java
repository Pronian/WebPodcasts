package Model;

public class User
{
    private String userName;
    private String passHash;
    private int userId;

    public User(String userName, String passHash, int userId)
    {
        this.userName = userName;
        this.passHash = passHash;
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassHash()
    {
        return passHash;
    }

    public int getUserId()
    {
        return userId;
    }
}
