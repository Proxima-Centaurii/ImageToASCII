import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

    JFileChooser open_dialog, save_dialog;
    FileFilter open_dialog_filter, save_dialog_filter;

    public Main(){
        // File chooser for input images
        open_dialog = new JFileChooser();
        open_dialog_filter = new FileNameExtensionFilter("Image file", "png", "jpg", "jpeg");
        open_dialog.setFileFilter(open_dialog_filter);

        // File chooser for choosing the location and name of the resulted text file
        save_dialog = new JFileChooser();
        save_dialog_filter = new FileNameExtensionFilter("Text file", "txt");
        save_dialog.setFileFilter(save_dialog_filter);

    }

    public static void main(String args[]){
        new Main().run();
        System.out.println("Application finished.");
    }

    private void run(){
        // This variable is used to track the response from the pop up dialogs at each step of the process
        int response;

        response = open_dialog.showOpenDialog(null);
        if(response != JFileChooser.APPROVE_OPTION)
            return;

        // Ask the user for numbers
        // Recommended resolution 769x300
        int width_in_characters = askForNumber("Enter the width in characters of the output");
        if(width_in_characters <= 0)
            return;

        int heigth_in_characters = askForNumber("Enter the heigth in characters of the output");
        if(heigth_in_characters <= 0)
            return;

        CharacterScales scale = askForCharacterScale("Select a character scale:");
    
        System.out.println("Converting");
        Converter gen = new Converter(scale, width_in_characters, heigth_in_characters);
        char [][] asciiData = gen.convertImageFile(open_dialog.getSelectedFile());

        if(asciiData == null){
            JOptionPane.showMessageDialog(null, "Failed to convert file!");
            return;
        }

        // Choose save destination and write to file
        response = save_dialog.showSaveDialog(null);
        if(response != JFileChooser.APPROVE_OPTION)
            return;

        FileOutputStream fout;
        PrintWriter writer;

        try{
            fout = new FileOutputStream(save_dialog.getSelectedFile().toString()+".txt");
            writer = new PrintWriter(fout);

            for(int y = 0; y < heigth_in_characters; y++){
                writer.print(asciiData[y]);
                writer.print('\n');
            }

            writer.close();
            fout.close();
        }
        catch(FileNotFoundException fnfe){fnfe.printStackTrace();}
        catch(IOException ioe){ioe.printStackTrace();}

        // Show confirmation message
        JOptionPane.showMessageDialog(null, "File converted!");
    }

    private int askForNumber(String message){
        int number;
        try{
            String response = JOptionPane.showInputDialog(message);
            number = Integer.parseInt(response);
        }
        catch(Exception e){
            int choice = JOptionPane.showOptionDialog(null,"You did not enter a number. By default that value will be set to 100.\nDo you wish to continue? (selecting no will close the application)",
                    "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            if(choice == JOptionPane.YES_OPTION){number = 100;}
            else number = -1;
        }
        return number;
    }

    private CharacterScales askForCharacterScale(String message){
        Map<String, CharacterScales> scales = new HashMap<String, CharacterScales>();

        for(CharacterScales scale : CharacterScales.values()){
            String display_name = scale.name().toLowerCase().replace('_', ' ');
            scales.put(display_name, scale);
        }
        
        String selected = (String) JOptionPane.showInputDialog(null, 
                                                                "Choose a character scale:", 
                                                                "Character scale", 
                                                                JOptionPane.INFORMATION_MESSAGE, null, 
                                                                scales.keySet().toArray(), 
                                                                scales.get("small sharp")
                                                                );
        
        
        return scales.get(selected);
    }

}//end of class
