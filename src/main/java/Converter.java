import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Converter {

    char [] shade_scale; // Array of characters sorted from characters that are "darker" towards ones that are "lighter"
    int target_width, target_height;
    float unit; // Used in mapping a gray scale value to a character on the shade scale

    public Converter(CharacterScales scale, int target_width, int target_height){
        this.target_width = target_width;
        this.target_height = target_height;

        shade_scale = scale.getScaleAsCharArray();
        unit = 255.0f/(float)(shade_scale.length - 1);
    }

    public char[][] convertImageFile(File f){
        BufferedImage original = null;
        BufferedImage scaled = null;

        try{
            original = ImageIO.read(f);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            // If an image object has been created, clear the backing memory buffers it uses
            if(original != null){
                scaled = getResizedImage(original);
                original.flush(); 
            }
            else
                return null;
        }

        char res[][] = new char[target_height][target_width];
        for(int y = 0; y < target_height; y++){
            for(int x = 0; x < target_width; x++){
                int pixel = scaled.getRGB(x, y);

                int gray_scale = getGrayScale(pixel);
                char c = getCorrespondingChar(gray_scale);

                res[y][x] = c;
            }
        }

        scaled.flush();

        return res;
    }

    private int getGrayScale(int pixel){
        int R,G,B;

        R = (pixel >> 16) & 0xFF;
        G = (pixel >> 8) & 0xFF;
        B = pixel & 0xFF;

        return (R+G+B)/3;
    }

    private char getCorrespondingChar(int grayScale){
        int pos = (int)(grayScale/unit);

        return shade_scale[pos];
    }

    private BufferedImage getResizedImage(BufferedImage source){
        BufferedImage target = new BufferedImage(target_width, target_height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = target.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(source, 0, 0, target_width, target_height, null);
        g2d.dispose();

        return target;
    }

}//end of class
