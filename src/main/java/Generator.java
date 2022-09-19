import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Generator {

    char [] shadeScale;
    BufferedImage image;
    int imgWidth, imgHeight;
    float unit;

    public Generator(){
        image = null;

        String bigScale = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
        String smallScale = "@%#*+=-:. ";
        String smallSharpScale = "M@S#+c*':. ";
        String smoothScale = "@N%Q&WMgm$0BDRH#8dObUAqhGwkpXk9V6P]Eyun[41ojae2S5Yfzx(lr)F3{CtJviT7srz\\Lc/?*!+<;^=\",:_'.-` ";
        String largeScale = "@MBHENR#KWXDFPQASUZbdehx*8Gm&04LOVYkpq5Tagns69owz$CIu23Jcfry%1v7l+it[] {}?j|()=~!-/<>\"^_';,:`. ";
        String sharpScale = "MB@$S%#=+oc^*s';:,. ";

        String currentScale = smallSharpScale;

        shadeScale = new char[currentScale.length()];
        unit = 255.0f/(float)(currentScale.length()-1);

        for(int i=0;i<currentScale.length();i++)
            shadeScale[i] = currentScale.charAt(i);
    }

    public char[][] generateFor(File f, int charWidth, int charHeight){
        try{
            image = ImageIO.read(f);
        }
        catch(IOException ioe){ioe.printStackTrace();return null;}

        imgWidth = image.getWidth();
        imgHeight = image.getHeight();

        image = getResizedImage(image, charWidth, charHeight);

        char res[][] = new char[charHeight][charWidth];

        for(int j=0;j<charHeight;j++){
            for(int i=0;i<charWidth;i++){
                int pixel = image.getRGB(i, j);
                res[j][i] = getCorrespondingChar(getGrayScale(pixel));
            }
        }
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
        return shadeScale[pos];
    }

    private BufferedImage getResizedImage(BufferedImage bfi, int newWidth, int newHeight){
        Image image = bfi.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage sbfi = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = sbfi.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        imgWidth = sbfi.getWidth();
        imgHeight = sbfi.getHeight();

        return sbfi;
    }

}//end of class
