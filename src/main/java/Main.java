import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

    public Main(){//Standard char res 769x300
        run();
        System.out.println("DONE!");
    }

    public static void main(String args[]){
        new Main();
    }

    private void run(){
        int response;
        JFileChooser openDialog = new JFileChooser();
        FileFilter imgFilter = new FileNameExtensionFilter("Image file", "png", "jpg", "gif");
        openDialog.setFileFilter(imgFilter);

        JFileChooser saveDialog = new JFileChooser();
        FileFilter txtFilter = new FileNameExtensionFilter("Text file", "txt");
        saveDialog.setFileFilter(txtFilter);

        response = openDialog.showOpenDialog(null);
        if(response != JFileChooser.APPROVE_OPTION)
            System.exit(0);

        Generator gen = new Generator();
        int wch,hch;

        wch = askForNumber("Enter the width in characters.");
        hch = askForNumber("Enter the heigth in characters.");

        char [][] asciiData = gen.generateFor(openDialog.getSelectedFile(), wch, hch);

        response = saveDialog.showSaveDialog(null);
        if(response != JFileChooser.APPROVE_OPTION)
            System.exit(0);

        FileOutputStream fout;
        PrintWriter writer;

        try{
            fout = new FileOutputStream(saveDialog.getSelectedFile().toString()+".txt");
            writer = new PrintWriter(fout);

            for(int y=0;y<hch;y++){
                writer.print(asciiData[y]);
                writer.print('\n');
            }

            writer.close();
            fout.close();
        }
        catch(FileNotFoundException fnfe){fnfe.printStackTrace();}
        catch(IOException ioe){ioe.printStackTrace();}

        JOptionPane.showMessageDialog(null, "File converted!");
    }

    private int askForNumber(String message){
        int number;
        try{
            String response = JOptionPane.showInputDialog(message);
            number = Integer.parseInt(response);
        }
        catch(Exception e){
            int choice = JOptionPane.showOptionDialog(null,"You didn't enter a number. By default that value will be set to 100.\nDo you wish to continue?",
                    "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            if(choice == JOptionPane.YES_OPTION){number = 100;}
            else number = -1;
        }
        return number;
    }

}//end of class
