package pixelnoid;

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.net.*;

public class GestorImagenes
{
    private HashMap<String, Image> elementos;
    
    public GestorImagenes() {
        elementos = new HashMap<String, Image>();
    }
    
    public BufferedImage getElement(final String nombre) {
        BufferedImage img = (BufferedImage)elementos.get(nombre);
        if (img == null) {
            URL url = null;
            try {
                url = this.getClass().getClassLoader().getResource("resources/" + nombre);
                img = ImageIO.read(url);
            }
            catch (Exception e) {
                System.out.println(nombre);
                System.exit(0);
            }
            elementos.put(nombre, img);
        }
        return img;
    }
}
