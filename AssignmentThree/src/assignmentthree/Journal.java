package assignmentthree;

/**
 *
 * @author faizanHaider
 */
public class Journal extends Reference {
    private String ORGANIZATION;
    
    public Journal(String callNum, String year, String title, String organization) {
        super(callNum, year, title);
        this.ORGANIZATION = organization;
    }

    public void setOrganization(String organiz) {
            ORGANIZATION = organiz;
    }
    public String getOrganization() {
            return ORGANIZATION;
    }
}
