import java.awt.Color;

public enum Style {
    // background colors --> these use bg1() and bg2() for shorthand access
    BACKGROUND1(Color.WHITE),                     // currently white
    BACKGROUND2(new Color(240,240,240)),  // currently a light gray
    
    // text colors
    TEXTCOLOR1(Color.BLACK),
    TEXTCOLOR2(Color.GRAY),  // dark gray

    // extra
    SUCCESSCOLOR(new Color(3, 182, 0)),
    HIGHLIGHTCOLOR(new Color(255, 234, 218)),
    TRANSPARENT(new Color(0,0,0,0)),
    TRANSLUCENT(new Color(250,250,250,230));




    
    private final Color color;
    
    public Color getColor() {
        return this.color;
    }

    Style(Color color) {
        this.color = color;
    }
    
    // Shorthand helper methods
    public static Color bg1() { return BACKGROUND1.color; }
    public static Color bg2() { return BACKGROUND2.color; }

    public static Color text1() { return TEXTCOLOR1.color; }
    public static Color text2() { return TEXTCOLOR2.color; }

    public static Color success() { return SUCCESSCOLOR.color; }
    public static Color highlight() { return HIGHLIGHTCOLOR.color; }
    public static Color transparent() { return TRANSPARENT.color; }
    public static Color translucent() { return TRANSLUCENT.color; }

}
