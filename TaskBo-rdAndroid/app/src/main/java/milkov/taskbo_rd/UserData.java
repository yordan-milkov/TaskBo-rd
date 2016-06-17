package milkov.taskbo_rd;

import java.net.IDN;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    public static String    userName;
    public static String    mail;
    public static String    displayName;
    public static String    GSM;
    public static List<GroupInfo> groupsList  = new ArrayList<GroupInfo>();
    public static Map<Integer, ArrayList<IssueInfo>> issuesMap   = new HashMap();
}
