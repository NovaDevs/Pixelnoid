package pixelnoid;

public class Premio extends Elemento
{
    private static final int vY = 4;
    private boolean bajando;
    
    public Premio(final Pixelnoid escenario, final String nombre, final int x, final int y) {
        super(escenario, nombre);
        this.bajando = false;
        this.setCoordX(x);
        this.setCoordY(y);
    }
    
    public boolean estaBajando() {
        return this.bajando;
    }
    
    public void setBajando(final boolean t) {
        this.bajando = t;
    }
    
    public void mover() {
        if (bajando) {
            setCoordY(getCoordY() + 4);
        }
    }
    
    public boolean premioRecogido(final Barra l) {
        return getCoordX() + getLargo() >= l.getCoordX() && getCoordX() <= l.getCoordX() + l.getLargo() && getCoordY() + getAlto() > l.getCoordY() && getCoordY() < l.getCoordY();
    }
}