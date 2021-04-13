package pixelnoid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class TestPixelRGB {
   BufferedImage image;
   int width;
   int height;
   
   public TestPixelRGB() {
      try {
         //String path = new File("src/niveles/pokeball.png").getAbsolutePath();
         File input = new File("src/niveles/pokeball.png");
         //System.out.println(path);
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         int[][] arrayNivel = new int[height][width];
          System.out.println("Width: "+image.getWidth());
          System.out.println("Height: "+image.getHeight());
          System.out.println("Array lenght: " + arrayNivel.length);

         for(int i=0; i<height; i++) {
         
            for(int j=0; j<width; j++) {
               Color c = new Color(image.getRGB(j, i));
               System.out.printf("Pixel[%d][%d], Red: %d, Green: %d, Blue: %d%n",i,j, c.getRed(), c.getGreen(), c.getBlue());
            }
         }

      } catch (Exception e) {
          System.err.println("File not found.");
      }
   }
   
   static public void main(String args[]) throws Exception {
      TestPixelRGB obj = new TestPixelRGB();
      
   }
}