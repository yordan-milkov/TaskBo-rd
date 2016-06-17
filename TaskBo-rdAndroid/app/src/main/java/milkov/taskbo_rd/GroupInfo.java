package milkov.taskbo_rd;

public class GroupInfo
{
    private String   name;
    private Integer  IDnumber;
    private String   description;

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
}
