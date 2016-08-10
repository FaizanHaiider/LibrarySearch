package assignmentthree;

/**
 *
 * @author faizanHaider
 */
public class Reference {
    private String CALL_NUMBER;
    private String YEAR;
    private String TITLE;
    
    public Reference(String callNum, String year, String title) {
        CALL_NUMBER = callNum;
        YEAR = year;
        TITLE = title;
    }
    
    public void setCallNumber(String callNumber) {
        CALL_NUMBER = callNumber;
    }
    public String getCallNumber() {
        return CALL_NUMBER;
    }
    public void setTitle(String title) {
        TITLE = title;
    }
    public String getTitle() {
        return TITLE;
    }
    public void setYear(String year) {
          YEAR = year;
    }
    public String getYear() {
        return YEAR;
    }

    @Override
    public String toString()
    {
        String referenceString = null;
        
        if (this instanceof Book) {
            Book book = (Book)this;
            referenceString = "    - Type: Book \n Title: " + book.getTitle() 
                    + " \n Publisher: " + book.getPublisher()
                    + " \n Year: " + book.getYear()
                    + " \n Call Number: " + book.getCallNumber();
            for(String temp : book.getAuthor()) {
                referenceString = temp + ", ";
            }
        } else if (this instanceof Journal) {
            Journal journal = (Journal)this;
            referenceString = "    - Type: Journal \n Title: " + journal.getTitle() 
                    + " \n Organization: " + journal.getOrganization()
                    + " \n Year: " + journal.getYear()
                    + " \n Call Number: " + journal.getCallNumber();                  
        }
        
        return referenceString;
    }   
}
