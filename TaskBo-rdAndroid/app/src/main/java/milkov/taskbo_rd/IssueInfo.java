package milkov.taskbo_rd;

public class IssueInfo {
    private Integer issueUID;
    private String  name;
    private String  description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIssueUID() {
        return issueUID;
    }

    public void setIssueUID(Integer issueUID) {
        this.issueUID = issueUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
