package pixelnoid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Pixel2Level {

    BufferedImage image;
    int width;
    int height;

    public int[][] pixelToArray(String url_imagen) {

        int[][] arrayNivel = null;
        int pixRed, pixGreen, pixBlue = 0;

        try {
            image = ImageIO.read(new File(url_imagen));
            width = image.getWidth();
            height = image.getHeight();
            arrayNivel = new int[height][width];

            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    PixelColor pc = new PixelColor();
                    pixRed = c.getRed();
                    pixGreen = c.getGreen();
                    pixBlue = c.getBlue();
                    arrayNivel[i][j] = pc.getColorCode(pixRed, pixGreen, pixBlue);
                }
            }
        } catch (IOException e) {
            System.err.println("File not found.");
        }
        return arrayNivel;
    }

}
