package milkov.taskbo_rd;

public class CheckItem
{
    public boolean  isChecked;
    public String   description;
    public int      chekcID;

    public CheckItem(){}


    public  CheckItem(String description) {
        this.description = description;
    }
}