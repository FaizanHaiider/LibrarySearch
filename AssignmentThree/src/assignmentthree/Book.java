package assignmentthree;

import java.util.ArrayList;

/**
 *
 * @author faizanHaider
 */
public class Book extends Reference{
    private String PUBLISHER;
    private ArrayList<String> AUTHOR = new ArrayList<>();
    
    public Book(String callNum, String year, String title, String publisher, ArrayList<String> authors) {
        super(callNum, year, title);
        this.PUBLISHER = publisher;
        this.AUTHOR = authors;
    }

    public void setPublisher(String publisher) {
            PUBLISHER = publisher;
    }
    public String getPublisher() {
        return PUBLISHER;
    }
    public void setAuthor(String author) {
            AUTHOR.add(author);
    }
    public ArrayList<String> getAuthor() {
            return AUTHOR;
    }
}