
package pixelnoid;

/**
 *
 * @author SISTEMAS
 */
public class TestPixel2Level {

   public static void main(String args[]) throws Exception {
        Pixel2Level pix = new Pixel2Level();
        int[][] miNivel = pix.pixelToArray("src/niveles/snake.png");

        for (int i = 0; i < miNivel.length; i++) {
            System.out.print("{ ");
            for (int j = 0; j < miNivel[i].length; j++) {
                if (i == miNivel.length - 1 && j == miNivel[i].length - 1) {
                    System.out.print(miNivel[i][j] + " }");
                } else if (j == miNivel[i].length - 1) {
                    System.out.print(miNivel[i][j] + " },");
                } else {
                    System.out.print(miNivel[i][j] + ", ");
                }
            }
            System.out.println();
        }

    }
    
}
