
package pixelnoid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

class PixelRGB {
   BufferedImage image;
   int width;
   int height;
   
   public PixelRGB() {
      try {
         //File input = new File("blackandwhite.jpg");
         URL url = this.getClass().getClassLoader().getResource("niveles/pokeball.png");
         image = ImageIO.read(url);
         width = image.getWidth();
         height = image.getHeight();
         int[][] arrayNivel = new int[height][width];
          System.out.println("Width: "+image.getWidth());
          System.out.println("Height: "+image.getHeight());
          System.out.println("Array lenght: " + arrayNivel.length);
         int count = 0;
         
         for(int i=0; i<height; i++) {
         
            for(int j=0; j<width; j++) {
            
               count++;
               Color c = new Color(image.getRGB(j, i));
               System.out.println("S.No: " + count + " Red: " + c.getRed() +"  Green: " + c.getGreen() + " Blue: " + c.getBlue());
            }
         }

      } catch (Exception e) {
          System.err.println("File not found.");
      }
   }
   
   static public void main(String args[]) throws Exception {
      PixelRGB obj = new PixelRGB();
      
   }
}