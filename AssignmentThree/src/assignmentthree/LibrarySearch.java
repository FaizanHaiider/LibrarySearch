package assignmentthree;

import static assignmentthree.GUI.ADD_PANEL;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author faizanHaider
 */
public class LibrarySearch {
    public static HashMap<String, ArrayList<Integer>> map = new HashMap<>();
    public static JTextField TcallNum;
    public static JTextField Ttitle;
    public static JTextField TstartYear;
    public static JTextField TendYear;
    public static JTextField Tauthor;
    public static JTextField Tmessage;
    public static JTextArea Tdesc;
    
    public void addRefence(ArrayList<Reference> references)  {
        String title = references.get(references.size()-1).getTitle();
        String[] array = title.split(" ");

        // add string and arraylist to map if string is not already present
        // does not add a location in index
        for(String i : array) {
            if(!map.containsKey(i)) {
                ArrayList<Integer> index = new ArrayList<>();
                map.put(i.toLowerCase(), index);
            }
        }

        // add index to keywords
        for(String i : array) {
            for(String j : map.keySet()) {
                if(i.toLowerCase().equals(j)) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp = map.get(i);
                    temp.add(references.size()-1);
                    map.replace(j, temp);
                }
            }
        }    
    }
    public void searchReference(ArrayList<Reference> references, ArrayList<String> keywords, String[] HM_keywords) {
        int finalList;
        int i;
        ArrayList<ArrayList<Integer>> indices = new ArrayList<>();
        ArrayList<Integer> commonIndices = new ArrayList<>();
        
        // if no search criteria, print all books/journals
        if(keywords.isEmpty() && (HM_keywords[0].isEmpty())) {   
            for(i=0; i<references.size(); i++) {
                printReferenceInfo(references.get(i));
            }
            return;
        }
        
        // searches HashMap for any matching keywords
        for(String s : HM_keywords) {
            if(map.containsKey(s.toLowerCase())) {
                ArrayList<Integer> temp = map.get(s.toLowerCase());
                indices.add(temp); // adds ArrayList to indices
            } 
        }
        
        // if keywords matched with HashMap keywords, store them in indices ArrayList
        if(!indices.isEmpty()) {
            finalList = indices.size() == 1 ? 0 : indices.size()-1;
            for(i=0; i<finalList; i++) {
                indices.get(i).retainAll(indices.get(i+1));
            }
            commonIndices = indices.get(finalList);
            
            if(!keywords.isEmpty()) {    // if there is extra information
                for(i=0; i<indices.get(finalList).size(); i++) {
                    if(references.get(commonIndices.get(i)).getCallNumber().equalsIgnoreCase(keywords.get(0)) &&
                       isYearPresent(keywords.get(1), keywords.get(2), references.get(commonIndices.get(i)).getYear())) {
                        printReferenceInfo(references.get(commonIndices.get(i)));
                    } 
                } 
            } else {
                for(i=0; i<commonIndices.size(); i++) {
                    printReferenceInfo(references.get(commonIndices.get(i)));
                }
            }
            return;
        }
        
        // if no matches with HashMap but still other keywords e.g year, publisher/organization, etc
        if(keywords.isEmpty()) {
            for(i=0; i<references.size(); i++) {
                if(references.get(i).getCallNumber().equalsIgnoreCase(keywords.get(0)) &&
                   isYearPresent(keywords.get(1), keywords.get(2), references.get(i).getYear())) {
                    printReferenceInfo(references.get(i));
                } 
            }
        }
    }
    
    // boolean method that returns if book's year is in user inputed year  
    public boolean isYearPresent(String startYear, String endYear, String bookYear) {
        if((startYear == null && endYear == null) || startYear.isEmpty() && endYear.isEmpty()) { // it matches directly, return true
            return true;
        }		
        int start = Integer.valueOf(startYear);
        int end = Integer.valueOf(endYear);
        int target = Integer.valueOf(bookYear);
        
        if(target <= end && target >= start) {
            return true;
        }
        return false;
    }
    
    // print reference information
    public void printReferenceInfo(Reference ref) {
        Tdesc.setText(ref.toString());
    }
    
    public JPanel createAddCard() {   
        String temp = null;
        String[] types = {"Book", "Journal"};
        JComboBox type = new JComboBox(types);
        JLabel callNum = new JLabel("Call Number:");
        JLabel title = new JLabel("Title:");
        JLabel year = new JLabel("Year");
        JLabel organPub = new JLabel("Organ/Publi:");
        JLabel message = new JLabel("Messages:");
        JLabel authors = new JLabel("Author(s):");
        
        TcallNum = new JTextField(10);
        Ttitle = new JTextField(10);
        TstartYear = new JTextField(10);
        TendYear = new JTextField(10);
        Tauthor = new JTextField(10);
        Tdesc = new JTextArea(5,5);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());
        addPanel.add(type, BorderLayout.PAGE_START);
        
        type.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
              if(e.getItem().toString().equals("Book")) {
                   authors.setVisible(true);
                   Tauthor.setVisible(true);
              } else {
                  authors.setVisible(false);
                  Tauthor.setVisible(false);
              }
            }
         });
        
        JComponent panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().
                addComponent(callNum).addComponent(title).
                addComponent(year).addComponent(organPub).
                addComponent(authors));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(TcallNum).addComponent(Ttitle).
                addComponent(TstartYear).addComponent(TendYear).
                addComponent(Tauthor));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(callNum).addComponent(TcallNum));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(title).addComponent(Ttitle));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(year).addComponent(TstartYear));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(organPub).addComponent(TendYear));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(authors).addComponent(Tauthor));
        layout.setVerticalGroup(vGroup);
       
        addPanel.add((JPanel) panel);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,1,4,4));
        
        JButton add = new JButton("Add");
        JButton reset = new JButton("Reset");
        
        buttons.add(add);
        buttons.add(reset);
        
        add.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                if(type.getSelectedItem().toString().equals("Book")) {
                    AssignmentThree.setInput(AssignmentThree.getInput("a"));
                } else {
                    AssignmentThree.getInput("b");
                }
            }
        });
        reset.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                TcallNum.setText("");
                Ttitle.setText("");
                TstartYear.setText("");
                TendYear.setText("");
                Tauthor.setText("");
                Tdesc.setText("");
            }
        });
        
        addPanel.add(buttons, BorderLayout.LINE_END);
        
        JPanel extraDesc = new JPanel();
        extraDesc.setLayout(new BorderLayout());
        extraDesc.add(Tdesc);
        
        extraDesc.setBorder(new TitledBorder ( new EtchedBorder (), "Messages"));
        JScrollPane scroll = new JScrollPane(Tdesc);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        extraDesc.add(scroll);
        
        addPanel.add(extraDesc, BorderLayout.PAGE_END);
        
        return addPanel;
    }
    
    public JPanel createSearchCard() {
        JLabel callNum = new JLabel("Call Number:");
        JLabel title = new JLabel("Title:");
        JLabel startYear = new JLabel("Start Year:");
        JLabel endYear = new JLabel("End Year:");
        
        TcallNum = new JTextField(10);
        Ttitle = new JTextField(10);
        TstartYear = new JTextField(10);
        TendYear = new JTextField(10);
        Tdesc = new JTextArea(5,5);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());
        
        JComponent panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().
                addComponent(callNum).addComponent(title).
                addComponent(startYear).addComponent(endYear));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(TcallNum).addComponent(Ttitle).
                addComponent(TstartYear).addComponent(TendYear));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(callNum).addComponent(TcallNum));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(title).addComponent(Ttitle));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(startYear).addComponent(TstartYear));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                 addComponent(endYear).addComponent(TendYear));
        layout.setVerticalGroup(vGroup);
       
        addPanel.add((JPanel) panel);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,1,4,4));
        
        JButton reset = new JButton("Reset");
        JButton search = new JButton("Search");
        
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AssignmentThree.search();
            }
        });
        reset.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                TcallNum.setText("");
                Ttitle.setText("");
                TstartYear.setText("");
                TendYear.setText("");
                Tauthor.setText("");
                Tdesc.setText("");
            }
        });
        
        buttons.add(reset);
        buttons.add(search);
        
        addPanel.add(buttons, BorderLayout.LINE_END);
        
        JPanel extraDesc = new JPanel();
        extraDesc.setLayout(new BorderLayout());
        extraDesc.add(Tdesc);
        
        extraDesc.setBorder(new TitledBorder ( new EtchedBorder (), "Search Results"));
        JScrollPane scroll = new JScrollPane(Tdesc);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        extraDesc.add(scroll);
        
        addPanel.add(extraDesc, BorderLayout.PAGE_END);
        
        return addPanel;
    }
    
    
    
    
    
    
    
}
