package pixelnoid;

import java.awt.*;

public class Ladrillo extends Elemento
{
    private int posYInicial;
    private int posXInicial;
    private boolean vivo;
    private int color;
    private Premio p;
    private int vidas;
    private int puntos;
    
    public Ladrillo(final Pixelnoid escenario, final String nombre, final int color, final int vidas, final int puntos) {
        super(escenario, nombre);
        this.posYInicial = 51;
        this.posXInicial = 10;
        this.vivo = true;
        this.color = color;
        this.p = null;
        this.vidas = vidas;
        this.puntos = puntos;
    }
    
    public int getPosYInicial() {
        return this.posYInicial;
    }
    
    public int getPosXInicial() {
        return this.posXInicial;
    }
    
    public boolean estaVivo() {
        return this.vivo;
    }
    
    public Premio getPremio() {
        return this.p;
    }
    
    public int getPuntos() {
        return this.puntos;
    }
    
    /*
    @Override
    public void paint(final Graphics g) {
        switch (color) {
            case 1: {
                g.setColor(Color.GREEN);
                break;
            }
            case 2: {
                g.setColor(Color.CYAN);
                break;
            }
            case 3: {
                g.setColor(Color.YELLOW);
                break;
            }
            case 4: {
                g.setColor(Color.RED);
                break;
            }
            case 5: {
                g.setColor(Color.WHITE);
                break;
            }
            case 6: {
                g.setColor(Color.ORANGE);
                break;
            }
            case 7: {
                g.setColor(Color.PINK);
                break;
            }
            case 8: {
                g.setColor(Color.BLUE);
                break;
            }
            case -1: {
                g.setColor(Color.GRAY);
                break;
            }
            case -2: {
                g.setColor(new Color(255, 215, 0));
                break;
            }
            case -3: {
                g.setColor(Color.DARK_GRAY);
                break;
            }
        }*/
    @Override
    public void paint(final Graphics g) {
        switch (color) {
            case 1: {
                //
                //g.setColor(Color.BLACK);
                g.setColor(new Color(30,30,30));
                break;
            }
            case 2:{
                g.setColor(Color.GREEN);
                break;
            }
            case 3: {
                g.setColor(Color.GRAY);
                break;
            }
            case 4: {
                g.setColor(Color.YELLOW);
                break;
            }
            case 5: {
                g.setColor(Color.RED);
                break;
            }
            case 6: {
                g.setColor(Color.WHITE);
                break;
            }
            case 7: {
                g.setColor(Color.ORANGE);
                break;
            }
            case 8: {
                g.setColor(Color.PINK);
                break;
            }
            case 9: {
                g.setColor(Color.BLUE);
                break;
            }
            case -1: {
                g.setColor(Color.CYAN);
                break;
            }
            case -2:{
                g.setColor(new Color(255, 215, 0));
                break;
            }
            case -3: {
                g.setColor(Color.DARK_GRAY);
                break;
            }
        }
    
        g.fillRect(getCoordX(), getCoordY(), getLargo(), getAlto());
        super.paint(g);
    }
    
    public void setPremio(final int i) {
        switch (i) {
            case 1: {
                p = new Nitro(getEscenario(), "nitro.png", getCoordX(), getCoordY());
                break;
            }
            case 2: {
                p = new Reductor(getEscenario(), "reductor.png", getCoordX(), getCoordY());
                break;
            }
            case 3: {
                p = new Laser(getEscenario(), "laser.png", getCoordX(), getCoordY());
                break;
            }
            case 4: {
                p = new Expansor(getEscenario(), "expandir.png", getCoordX(), getCoordY());
                break;
            }
            case 5: {
                p = new Vida(getEscenario(), "vida.png", getCoordX(), getCoordY());
                break;
            }
        }
    }
    
    public void rebota(final Elemento e) {
        --vidas;
        if ((e instanceof Pelota || e instanceof Bala) && vivo && vidas < 0) {
            vivo = false;
        }
    }
}
