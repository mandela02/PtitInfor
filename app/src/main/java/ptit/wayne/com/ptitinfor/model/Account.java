package ptit.wayne.com.ptitinfor.model;

public class Account {
    private String mName;
    private String mPassword;
    private String mID;


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public Account() {
    }

    public Account(String name, String password, String ID) {
        mName = name;
        mPassword = password;
        mID = ID;
    }
}
