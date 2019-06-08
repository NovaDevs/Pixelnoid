package pixelnoid;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SISTEMAS
 */
public class Fonts {
    private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 24);

private Font getArcadeFont() {
    Font font = null;
    String font_file_name = "src/fonts/VCR_OSD_MONO_1.001.ttf";
    

    try {
        // load from a cache map, if exists
        InputStream is = this.getClass().getResourceAsStream(font_file_name);
        font = Font.createFont(Font.TRUETYPE_FONT, is);
        
        
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        ge.registerFont(font);

       
    } catch (Exception ex) {
        System.err.println("Font not found.");
        font = SERIF_FONT;
    }
    return font;
}
}
 

