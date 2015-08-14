package modules.profile.model.vo;

public class UserVO {
    public int id;
    public String first;
    public String last;
    public String emailAddress;

    public UserVO() {
    }

    public UserVO(int id, String first, String last, String emailAddress) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.emailAddress = emailAddress;
    }
}
