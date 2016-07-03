package milkov.taskbo_rd;

public class IssueInfo {
    protected int     issueUID;
    protected int     groupUID;
    protected String  name;
    protected String  description;
    protected boolean isResolved;

    public IssueInfo() {
    }

    public IssueInfo( IssueInfo source ){
        setIssueUID(source.getIssueUID());
        setDescription(source.getDescription());
        setName(source.getName());
        setGroupUID(source.getGroupUID());
        setResolved(source.isResolved());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIssueUID() {
        return issueUID;
    }

    public void setIssueUID(int issueUID) {
        this.issueUID = issueUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGroupUID() {
        return groupUID;
    }

    public void setGroupUID(int groupUID) {
        this.groupUID = groupUID;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }
}
