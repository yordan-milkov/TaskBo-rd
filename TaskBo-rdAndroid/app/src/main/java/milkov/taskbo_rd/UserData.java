package milkov.taskbo_rd;

import java.net.IDN;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    public static UserInfo  userInfo = new UserInfo();
    public static String    mail;
    public static String    GSM;
    public static List<GroupInfo> groupsList  = new ArrayList<>();
    public static Map<Integer, ArrayList<IssueInfo>> issuesMap   = new HashMap();
}
