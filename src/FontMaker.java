// Contains code for making and handling fonts
import java.io.InputStream;
import java.awt.Font;

public class FontMaker {

	public static Font loadFont(String path, float size) {
	    Font font = null;
	    try {
	        InputStream is = FontMaker.class.getResourceAsStream(path);

	        if (is == null) {
	            throw new RuntimeException("Font not found: " + path);
	        }

	        font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return font;
	}

    // preset fonts
    public static final Font h1 = loadFont("/Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.07));
    public static final Font h2 = loadFont("/Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.05));
    public static final Font h3 = loadFont("/Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.033));

    public static final Font p = loadFont("/Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.023));
    public static final Font p2 = loadFont("/Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.015));


    public static final Font big = loadFont("/Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.15));
}




