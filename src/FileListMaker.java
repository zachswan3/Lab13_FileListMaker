import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileListMaker {

    public static boolean newUnsavedFile = false;
    public static ArrayList<String> myArrList = new ArrayList<>();
    public static boolean loadedFile = false;
    public static Path currentFile;
    public static Scanner pipe = new Scanner(System.in);

    public static void main(String[] args) {
        boolean done = false;
        System.out.println("Welcome!");
        do{
            String response = menu(pipe, "What would you like to do? [Open-Add-Delete-Clear-View-Save-Quit]", "[AaDdVvQqOoSsCc]",myArrList);
            response = response.toUpperCase();
            switch (response) {
                case "A": //adds a entry to the array list
                    String add = SafeInput.getNonZeroLenString(pipe,"What would you like to add?");
                    myArrList.add(add);
                    newUnsavedFile = true;
                    break;
                case "D": //deletes an entry to the array list
                    displayNumberedArray(myArrList);
                    int delete = SafeInput.getInt(pipe,"What entry would you like to delete?") - 1;
                    myArrList.remove(delete);
                    newUnsavedFile = true;
                    break;
                case "V": //displays the array list in full
                    displayNumberedArray(myArrList);
                    break;
                case "Q": //quits the program
                    if(newUnsavedFile){
                        done = SafeInput.getYNConfirm(pipe,"You have an unsaved file. Are you sure you would like to quit?");
                        done = !done;
                    }else{
                        done = SafeInput.getYNConfirm(pipe,"Would you like to continue? [Y/N]");
                    }
                    break;
                case "O": //opens a file and adds it to the list
                    myArrList = openFile();
                    break;
                case "C": //erases the entire ArrayList
                    myArrList.clear();
                    break;
                case "S": //Saves the file to whatever file is currently in use, but only if newUnsavedFile is currently true
                    saveFile();
                    break;
            }
        }while (!done);
    }
    private static String menu(Scanner pipe,String prompt,String regEx,ArrayList<String> myArrList){
        if(myArrList.size() != 0){
            for(String value: myArrList){
                System.out.printf("%s ",value);
            }
        }else{
            System.out.println("Your list will be displayed here once something is added to the array.");
        }
        return SafeInput.getRegExString(pipe,prompt,regEx);
    }
    private static void displayNumberedArray(ArrayList<String> myArrList){
        int i = 1;
        System.out.println("");
        for(String value: myArrList){
            System.out.printf("%d(%s) ",i,value);
            i = i + 1;
        }
        System.out.println("");
    }
    private static ArrayList<String> openFile(){
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String line;
        String[] arrayBuffer;
        if(loadedFile && newUnsavedFile){
            System.out.println("Please save your file before loading a new one");
        }else{
            try{
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);
                System.out.println("1");
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("2");
                    selectedFile = chooser.getSelectedFile();
                    Path file = selectedFile.toPath();
                    currentFile = selectedFile.toPath();

                    InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    while(reader.ready()){
                        line = reader.readLine();
                        arrayBuffer = line.split(" ");
                        for(int i=0;i<arrayBuffer.length;i++){
                            myArrList.add(arrayBuffer[i]);
                        }
                    }
                    loadedFile = true;
                    reader.close();
                }else{
                    System.out.println("You have failed to select a file");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return myArrList;
    }
    private static void saveFile(){
        if(newUnsavedFile){
            if(loadedFile){
                File workingDirectory = new File(System.getProperty("user.dir"));

                try{
                    OutputStream out = new BufferedOutputStream(Files.newOutputStream(currentFile, CREATE));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                    for(String rec: myArrList){
                        writer.write(rec,0,rec.length());
                        writer.newLine(); //writer shifts to a new line
                    }
                    writer.close(); //ends the writer
                    System.out.println("Data has been successfully written to "+currentFile);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                newUnsavedFile = false;
            }else{
                String newFileName = SafeInput.getNonZeroLenString(pipe,"Enter the name of the new file");
                File workingDirectory = new File(System.getProperty("user.dir"));
                Path file = Paths.get(workingDirectory.getPath()+"\\src\\"+newFileName+".txt");

                try{
                    OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                    for(String rec: myArrList){
                        writer.write(rec,0,rec.length());
                        writer.newLine(); //writer shifts to a new line
                    }
                    writer.close(); //ends the writer
                    System.out.println("Data has been successfully written to "+file);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                newUnsavedFile = false;
            }
        }else{
            System.out.println("No changes have been made to the file");
        }
    }
}