
public enum CharacterScales {
    
    SMALL("@%#*+=-:. "),
    SMALL_SHARP("M@S#+c*':. "),
    SHARP("MB@$S%#=+oc^*s';:,. "),
    MEDIUM("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. "),
    SMOOTH("@N%Q&WMgm$0BDRH#8dObUAqhGwkpXk9V6P]Eyun[41ojae2S5Yfzx(lr)F3{CtJviT7srz\\Lc/?*!+<;^=\",:_'.-` "),
    LARGE("@MBHENR#KWXDFPQASUZbdehx*8Gm&04LOVYkpq5Tagns69owz$CIu23Jcfry%1v7l+it[] {}?j|()=~!-/<>\"^_';,:`. ");


    private String scale;

    CharacterScales(String scale){
        this.scale = scale;
    }

    public String getScale(){
        return scale;
    }

    public char[] getScaleAsCharArray(){
        return scale.toCharArray();
    }


}//End of class
