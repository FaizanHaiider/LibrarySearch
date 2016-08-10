/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentthree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author faizanHaider
 */
public class AssignmentThree {
    public static Scanner scanner = new Scanner(System.in);
    public static String pattern = ".*[" + Pattern.quote("~`:;/!@#$%^&*()_+=\"\"") + "].*";
    public static ArrayList<Reference> library = new ArrayList<>();
    public static ArrayList<String> fileOutput = new ArrayList<>();

    public static ArrayList<String> getInput(String choice) {
        ArrayList<String> userInput = new ArrayList<>();
        
        userInput.add(isValid_General(isValid_Empty(LibrarySearch.TcallNum.getText())));
        if(choice.equals("a")) {
            userInput.add(isValid_General(LibrarySearch.Tauthor.getText())); 
        }
        userInput.add(isValid_General(isValid_Empty(LibrarySearch.Ttitle.getText())));
        userInput.add(isValid_General(LibrarySearch.TendYear.getText()));
        userInput.add(isYearValid(LibrarySearch.TstartYear.getText()));

        return userInput;
    }
    // fills book / journal details

    /**
     *
     * @param array
     */
    public static void setInput(ArrayList<String> array) {
        LibrarySearch lib = new LibrarySearch();
        
        if(array.size() > 4) {
            ArrayList<String> authors = new ArrayList<>();
            String[] temp = array.get(1).split(", ");
            for(int i=0; i<temp.length; i++) {
                authors.add(temp[i]);
            }
            Book book = new Book(array.get(0), array.get(4), array.get(2), array.get(3), authors);
            library.add(book);
        } else {
            Journal journal = new Journal(array.get(0), array.get(4), array.get(2), array.get(3));
            library.add(journal);
        }
        lib.addRefence(library);
    }
    
    // returns requested call number and year

    public static void search() { 
        LibrarySearch libSearch = new LibrarySearch();
        ArrayList<String> userSearch = new ArrayList<>();
        String keyword = LibrarySearch.Ttitle.getText();
        String[] keywords = keyword.split(" ");
        userSearch.add(isValid_General(isValid_Empty(LibrarySearch.TcallNum.getText())));
        System.out.println(LibrarySearch.TcallNum.getText());
        System.out.println(LibrarySearch.TstartYear.getText());
        System.out.println(LibrarySearch.TendYear.getText());
        
        userSearch.add(isYearValid(LibrarySearch.TstartYear.getText()));
        userSearch.add(isYearValid(LibrarySearch.TendYear.getText()));
        libSearch.searchReference(library, userSearch, keywords);
    }
    
    // prints ArrayList<String> to file

    /**
     *
     * @param fileName
     */
    public static void writeToFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // print ArrayList to file
            for(String s : fileOutput) {
                if(s.contains("type")) {
                    bufferedWriter.newLine();
                }
                bufferedWriter.write(s + "\n");
                
            }
            bufferedWriter.write("\n");

            // close file
            bufferedWriter.close();
        }
        // catch any exceptions
        catch(IOException ex) {
            System.out.println("ERROR: could not write to file.");
        }
    }   
    // convet user input into file appropriate output

    /**
     *
     * @param array
     */
        public static void makeFileOutput(ArrayList<String> array) {
        boolean bool = array.size() > 4;
        
        fileOutput.add("type = \"" + (bool ? "book" : "journal") + "\"");
        fileOutput.add("callnumber = \"" + array.get(0) + "\"");
        if(bool) {
            fileOutput.add("author(s) = \"" + array.get(1) + "\"");
            fileOutput.add("title = \"" + array.get(2) + "\"");
            fileOutput.add("publisher = \"" + array.get(3) + "\"");
            fileOutput.add("year = \"" + array.get(4) + "\"");
        } else {
            fileOutput.add("title = \"" + array.get(1) + "\"");
            fileOutput.add("organization = \"" + array.get(2) + "\"");
            fileOutput.add("year = \"" + array.get(3) + "\"");
        }
    }
    // validates that year is properly entered  

    /**
     *
     * @param year
     * @return
     */
        public static String isYearValid(String year) {
        boolean condition = true;
        
        return year;
    }
    // makes sure there isn't any special chars in any inputs

    /**
     *
     * @param input
     * @return
     */
    public static String isValid_General(String input) {
        String temp = input;
        if(temp.matches(pattern)) {
            JOptionPane.showMessageDialog(null, "This language just gets better and better!");
        }
        return temp;
    }
    // makes sure there isn't any empty inputs for mandatory fields 

    /**
     *
     * @param input
     * @return
     */
        public static String isValid_Empty(String input) {
        while(input.isEmpty()) {
            System.out.println("Cannot leave blank. Try again: ");
            input = scanner.nextLine();
        }
        return input;
    }   
}