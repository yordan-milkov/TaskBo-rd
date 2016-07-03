package milkov.taskbo_rd;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

public class GroupInfo
{
    private String   name;
    private Integer  IDnumber;
    private String   description;
    private List<UserInfo> users;

    public GroupInfo(){}

    public GroupInfo(String name, Integer IDNumber, String description) {
        this.name = name;
        this.IDnumber = IDnumber;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIDnumber() {
        return IDnumber;
    }

    public void setIDnumber(Integer IDnumber) {
        this.IDnumber = IDnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }


}
