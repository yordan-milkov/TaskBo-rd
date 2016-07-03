package milkov.taskbo_rd;

import java.util.ArrayList;
import java.util.List;

public class IssueData extends IssueInfo {

    private List< CheckItem >   checkItemList       = new ArrayList<>();
    private List< UserInfo >    participantsList    = new ArrayList<>();

    public IssueData(IssueInfo info) {
        super(info);
    }

    public void AddCheck(CheckItem item)
    {
        checkItemList.add(item);
    }

    public void AddParticipant(UserInfo user)
    {
        participantsList.add(user);
    }

    public List<CheckItem> getCheckItemList() {
        return checkItemList;
    }

    public List<UserInfo> getParticipantsList() {
        return participantsList;
    }

}
