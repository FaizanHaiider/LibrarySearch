/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentthree;

import static assignmentthree.AssignmentThree.fileOutput;
import static assignmentthree.AssignmentThree.setInput;
import static assignmentthree.AssignmentThree.writeToFile;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;


/**
 *
 * @author faizanHaider
 */

public class GUI extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    
    public static final String ADD_PANEL = "Add";
    public static final String SEARCH_PANEL = "Search";
    public static final String QUIT = "Quit";
    
    public String[] options = {"Add", "Search", "Quit"};
    
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        gui.setVisible(true);
        
        ArrayList<String> fileInput = new ArrayList<>();
        boolean isRepeat = false;
        
        if(args.length > 1) {
            String fileName = args[0];
            String line = null;
            int newBookJournalCounter = 0;
            
            try {
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null) {
                    if(!line.isEmpty()) {
                        String[] temp;
                        
                        fileOutput.add(line);
                        if(!line.contains("type")) {
                            temp = line.split(" = ");
                            fileInput.add(temp[1].substring(1, temp[1].length()-1));
                            newBookJournalCounter++;
                            isRepeat = false;
                        }
                        if(line.contains("Journal")) {
                            newBookJournalCounter ++;
                        }
                    } else {
                        isRepeat = true;
                    }
                    if(newBookJournalCounter % 5 == 0 && newBookJournalCounter != 0 && !isRepeat) {
                        setInput(fileInput);
                        fileInput.clear();
                    }
                }
                bufferedReader.close();         
            }
            catch(FileNotFoundException ex) {
                System.out.println("ERROR: Could not open file.");               
            }
        }
        writeToFile(args[1]);
    }
    
    public GUI() {
        super("Library Search");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        LibrarySearch libSearch = new LibrarySearch();
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JPanel cards;
        
        cards = new JPanel(new CardLayout());
        
        JPanel searchPanel = new JPanel();
        searchPanel = libSearch.createSearchCard();
        
        JPanel addPanel = new JPanel();
        addPanel = libSearch.createAddCard();
        
        cards.add(addPanel, ADD_PANEL);
        cards.add(searchPanel, SEARCH_PANEL);
        
        JPanel messages = new JPanel();
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        JLabel welcome = new JLabel("Welcome to Library Search.");
        JLabel instructions = new JLabel("Please choose one of the commands from the drop down menu.");
        
        messages.add(welcome);
        messages.add(instructions);
        add(messages, BorderLayout.NORTH);
        
        menuBar = new JMenuBar();
        menu = new JMenu("Commands");
        
        menu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(menu);
        
        menuItem = new JMenuItem(ADD_PANEL, KeyEvent.VK_M);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setContentPane((JPanel) cards);
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, ADD_PANEL);
                repaint();
                revalidate();
            }
        });
        
        menuItem = new JMenuItem(SEARCH_PANEL, KeyEvent.VK_Q);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setContentPane(cards);
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, SEARCH_PANEL);
                repaint();
                revalidate();
            }
        });

        menu.addSeparator();
        menuItem = new JMenuItem(QUIT, KeyEvent.VK_Q);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        
        setJMenuBar(menuBar);
    }
}
