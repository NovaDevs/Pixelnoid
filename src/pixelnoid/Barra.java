package pixelnoid;

import java.awt.event.*;

public class Barra extends Elemento
{
    private int vX;
    private boolean drch;
    private boolean izq;
    private static final int VELOCIDAD_BARRA =20;
    
    public Barra(final Pixelnoid escenario, final String nombre, final int x, final int y) {
        super(escenario, nombre);
        setCoordX(x);
        setCoordY(y);
    }
    
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case 39: {
                drch = true;
                break;
            }
            case 37: {
                izq = true;
                break;
            }
        }
        cambiarVelocidad();
    }
    
    public void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case 39: {
                drch = false;
                break;
            }
            case 37: {
                izq = false;
                break;
            }
        }
        cambiarVelocidad();
    }
    
    public void mover() {
        setCoordX(getCoordX() + vX);
        final int coordX = getCoordX();
        getEscenario().getClass();
        if (coordX < 11) {
            getEscenario().getClass();
            setCoordX(11);
        }
        final int n = getCoordX() + getLargo();
        getEscenario().getClass();
        /*if (n > 400) {
            getEscenario().getClass();
            setCoordX(400 - getLargo());
        }*/
        if (n > 490) {
            getEscenario().getClass();
            setCoordX(490 - getLargo());
        }
    }
    
    public void cambioDeBarra(final String nom) {
        setNombre(nom);
        setAlto(getGestorImagenes().getElement(getNombre()).getHeight());
        setLargo(getGestorImagenes().getElement( getNombre()).getWidth());
    }
    
    public void resetearBarra() {
       // cambioDeBarra("deslizador.png");
       cambioDeBarra("deslizador.png");
    }
    
    private void cambiarVelocidad() {
        vX = 0;
        if (drch) {
            vX = 10;
        }
        else if (izq) {
            vX = -10;
        }
    }
}