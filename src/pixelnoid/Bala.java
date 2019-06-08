package pixelnoid;

import java.awt.*;

public class Bala extends Elemento
{
    //private static final int VELOCIDAD_BALA = -8;
    private static final int VELOCIDAD_BALA = 20;
    private int vY;
    private boolean disparo;
    
    public Bala(final Pixelnoid e, final String n) {
        super(e, n);
        vY = -VELOCIDAD_BALA;
        disparo = false;
    }
    
    public boolean estaDisparando() {
        return this.disparo;
    }
    
    public void mover() {
        if (disparo) {
            setCoordY(this.getCoordY() + this.vY);
        }
    }
    
    public void disparando(final int x, final int y) {
        setCoordX(x);
        setCoordY(y);
        disparo = true;
    }
    
    @Override
    public void paint(final Graphics g) {
        if (disparo) {
            super.paint(g);
        }
    }
    
    public boolean destruir(final Ladrillo l) {
        final Rectangle r = new Rectangle(l.getCoordX(), l.getCoordY(), l.getLargo(), l.getAlto());
        final Rectangle r2 = new Rectangle(getCoordX(), getCoordY(), getLargo(), getAlto());
        final boolean aux = r2.intersects(r);
        if (aux || fueraLimites()) {
            disparo = false;
        }
        return aux;
    }
    
    private boolean fueraLimites() {
        final int coordY = getCoordY();
        getEscenario().getClass();
        return coordY < 10;
    }
}
