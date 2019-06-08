package pixelnoid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

class Pixel2Level {

    BufferedImage image;
    int width;
    int height;

    public int[][] pixelToArray(String url_imagen) {
        
        int[][] arrayNivel = null;
        
        try {
            image = ImageIO.read(new File(url_imagen));
            width = image.getWidth();
            height = image.getHeight();
            System.out.println(width);
            System.out.println(height);
            arrayNivel = new int[height][width];


            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    if (c.getRed() == 163 && c.getGreen() == 189 && c.getBlue() == 191) {//Color de relleno
                        arrayNivel[i][j] = 0;
                    } else if (c.getGreen() == 0 && c.getBlue() == 0 && c.getRed() == 0) {//Negro
                        arrayNivel[i][j] = 1;
                    } else if (c.getRed() == 128 && c.getGreen() == 128 && c.getBlue() == 128 ) {//Gris
                        arrayNivel[i][j] = 3;
                    } else if (c.getRed() == 255 && c.getGreen() == 255&&c.getBlue()==0) {//Amarillo
                        arrayNivel[i][j] = 4;
                    } else if (c.getRed() == 255 && c.getGreen() == 255 && c.getBlue() == 255) {//Blanco
                        arrayNivel[i][j] = 6;
                    } else if (c.getRed() == 255 && c.getGreen() == 165&&c.getBlue()==0) {//Naranja
                        arrayNivel[i][j] = 7;
                    } else if (c.getRed() == 255 && c.getGreen() == 192 && c.getBlue() == 203) {//Rosa
                        arrayNivel[i][j] = 8;
                    } else if (c.getRed() == 255 && c.getGreen() == 165&&c.getBlue()==0) {//Naranja
                        arrayNivel[i][j] = 9;
                    } else if (c.getRed() == 255&&c.getGreen()==0&&c.getBlue() == 255 ) {//Cyan
                        arrayNivel[i][j] =-1;
                    } else if (c.getRed() == 169 && c.getGreen() == 169 && c.getBlue() == 169) {//Gris Oscuro
                        arrayNivel[i][j] = -2;
                    } else if (c.getRed() == 255 && c.getGreen() == 215&&c.getBlue()==0) {//Dorado
                        arrayNivel[i][j] = -3;
                    } else if (c.getRed() == 255&&c.getGreen()==0&&c.getBlue()==0) {//Rojo
                        arrayNivel[i][j] = 5;
                    } else if (c.getRed()==0&&c.getGreen() == 255&&c.getBlue()==0) {//Verde
                        arrayNivel[i][j] = 2;
                    } else if (c.getRed()==0&&c.getGreen()==0&&c.getBlue() == 255) {//Azul
                        arrayNivel[i][j]=9;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File not found.");
        }
        return arrayNivel;
    }

    

}
