// Contains code for making and handling fonts
import java.io.File;
import java.awt.Font;

public class FontMaker {

    public static Font loadFont(String path, float size) {
        Font font = null;
        try {
            font = Font.createFont(0, new File(path)).deriveFont(size);
        } catch (Exception e) {
            System.out.println("Couldn't get font: " + e);
        }
        return font;
    }

    // preset fonts
    public static final Font h1 = loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.07*Main.scaley));
    public static final Font p = loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.023*Main.scaley));
}




