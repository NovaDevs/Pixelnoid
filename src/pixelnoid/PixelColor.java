package pixelnoid;

public class PixelColor {

    int colorCode = 0;
    //Array de  colores
    int[][] colorArray = {
        {163, 189, 191, 0},//Relleno
        {0, 0, 0, 1},//Negro
        {128, 128, 28, 3},//Gris
        {255, 255, 0, 4},//Amarillo
        {255, 255, 255, 6},//Blanco
        {255, 165, 0, 7},//Naranja
        {255, 192, 203, 8},//Rosa
        {255, 0, 255, -1},//Cyan
        {169, 169, 169, -2},//Gris Oscuro
        {255, 215, 0, -3},//Dorado
        {255, 0, 0, 5},//Rojo
        {0, 255, 0, 2},//Verde
        {0, 0, 255, 9},//Azul
    };

    int getColorCode(int red, int green, int blue) {
        for (int[] color : colorArray) {
            if (color[0] == red && color[1] == green && color[2] == blue) {
                colorCode = color[3];
            }
        }
        return colorCode;
    };
};
