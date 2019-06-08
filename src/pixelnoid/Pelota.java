package pixelnoid;

public class Pelota extends Elemento
{
    private static final int MOV_X_INICIAL = 4;
    private static final int MOV_Y_INICIAL = 6;
    private int vX;
    private int vY;
    private int movXInicial;
    private int movYInicial;
    private final int MAX_Y = 8;// distancia maxima a la que rebota la bola en la parte superior
    //private final int MAX_Y = 270;  
    private boolean estaParada;
    
    public Pelota(final Pixelnoid escenario, final String nombre) {
        super(escenario, nombre);
        this.movXInicial = 4;
        this.movYInicial = 6;
        this.estaParada = true;
        this.vX = 0;
        this.vY = 0;
    }
    
    public boolean estaParada() {
        return this.estaParada;
    }
    
    public void pelotaStart() {
        estaParada = false;
        vY = movYInicial;
    }
    
    public void resetearPelota(final int x, final int y) {
        movXInicial = 4;
        movYInicial = 6;
        setCoordX(x);
        setCoordY(y);
        estaParada = true;
        vY = 0;
        vX = 0;
    }
    
    public void acelera() {
        if (movXInicial < 6) {
            movXInicial *= (int)1.5;
            movYInicial *= (int)1.5;
            vX *= (int)1.5;
            vY *= (int)1.5;
        }
    }
    
    public void reducir() {
        if (movXInicial > 2) {
            movYInicial /= (int)1.5;
            movXInicial /= (int)1.5;
            vY /= (int)1.5;
            vX /= (int)1.5;
        }
    }
    
    public void mover(final Barra b) {
        if (!estaParada) {
            setCoordX(getCoordX() + vX);
            setCoordY(getCoordY() + vY);
        }
        else {
            setCoordX(b.getCoordX() + b.getLargo() / 2 - getLargo() / 2);
            setCoordY(b.getCoordY() - getAlto());
        }
    }
    
    public boolean pasoBarra(final Barra l) {
        if (getCoordY() >= l.getCoordY() + l.getAlto()) {
            getEscenario().getClass();
            setCoordY(660-100);
            getEscenario().getClass();
            setCoordX(480 / 2);
            return true;
        }
        return false;
    }
    
    public boolean rebota(final Elemento l) {
        if (l instanceof Ladrillo && choqueLadrillo((Ladrillo)l)) {
            return true;
        }
        if (l instanceof Barra && choqueBarra((Barra)l)) {
            return true;
        }
        final int coordX = getCoordX();
        getEscenario().getClass();
        
        if (coordX < 11) {
            getEscenario().getClass();
            setCoordX(11);
            vX = -vX;
            return true;
        }
        
        final int n = getCoordX() + getLargo();
        getEscenario().getClass();
       if (n > 490) {
            getEscenario().getClass();
            setCoordX(490 - getLargo());
            vX = -vX;
            return true;
        }
        final int coordY = getCoordY();
        getEscenario().getClass();
        //----REBOTES EN LA PARTE SUPERIOR DE LA PANTALLA-----//
        if (coordY < MAX_Y) {
            getEscenario().getClass();
            setCoordY(MAX_Y);
            vY = -vY;
            return true;
        }
        /*if (coordY < 290) {
            getEscenario().getClass();
            setCoordY(290);
            vY = -vY;
            return true;
        }*/
        //----------------------------------------------------//
        return false;
    }
    
    private boolean estaEntreEjesXYDeLaBarra(final Elemento l) {
        return getCoordX() + getLargo() >= l.getCoordX() && getCoordX() <= l.getCoordX() + l.getLargo() && getCoordY() + getAlto() > l.getCoordY() && getCoordY() < l.getCoordY();
    }
    
    private boolean estaEnLateralIzquierdo(final Elemento l) {
        return getCoordX() + getLargo() / 2 > l.getCoordX() && getCoordX() + getLargo() / 2 < l.getCoordX() + l.getLargo() / 5;
    }
    
    private boolean estaEntreLateralIzqYMedio(final Elemento l) {
        return getCoordX() + getLargo() / 2 > l.getCoordX() + l.getLargo() / 5 && getCoordX() + getLargo() / 2 < l.getCoordX() + 2 * l.getLargo() / 5;
    }
    
    private boolean estaEnElMedio(final Elemento l) {
        return getCoordX() + getLargo() / 2 > l.getCoordX() + 2 * l.getLargo() / 5 && getCoordX() + getLargo() / 2 < l.getCoordX() + 3 * l.getLargo() / 5;
    }
    
    private boolean estaEntreMedioYLateralDrch(final Elemento l) {
        return getCoordX() + getLargo() / 2 > l.getCoordX() + 3 * l.getLargo() / 5 && getCoordX() + getLargo() / 2 < l.getCoordX() + 4 * l.getLargo() / 5;
    }
    
    private boolean estaEnLateralDerecho(final Elemento l) {
        return getCoordX() + getLargo() / 2 > l.getCoordX() + 4 * l.getLargo() && getCoordX() + getLargo() / 2 < l.getCoordX() + l.getLargo();
    }
    
    private boolean estaExtremoIzquierdo(final Elemento l) {
        return getCoordX() + getLargo() / 2 < l.getCoordX();
    }
    
    private boolean estaExtremoDerecho(final Elemento l) {
        return getCoordX() + getLargo() / 2 > l.getCoordX() + l.getLargo();
    }
    
    private boolean choqueBarra(final Barra l) {
        if (vY >= 0 && estaEntreEjesXYDeLaBarra(l)) {
            if (estaEnLateralIzquierdo(l)) {
                vX = (int)(-movXInicial * 1.5);
            }
            else if (estaEntreLateralIzqYMedio(l)) {
                vX = -movXInicial;
            }
            else if (estaEnElMedio(l)) {
                if (vX > 0) {
                    vX = movXInicial / 2;
                }
                else {
                    vX = -movXInicial / 2;
                }
                if (vX == 0) {
                    vX = 2;
                }
            }
            else if (estaEntreMedioYLateralDrch(l)) {
                vX = (int)(movXInicial * 1.5);
            }
            else if (estaEnLateralDerecho(l)) {
                vX = (int)(movXInicial * 1.5);
            }
            else if (estaExtremoIzquierdo(l)) {
                vX = -2 * movXInicial;
            }
            else if (estaExtremoDerecho(l)) {
                vX = 2 * movXInicial;
            }
            vY = -vY;
            return true;
        }
        return false;
    }
    
    private boolean choqueLadrillo(final Ladrillo l) {
        if (getCoordY() >= l.getCoordY() - getAlto() / 2 && getCoordY() <= l.getCoordY() + l.getAlto() / 2 && getCoordX() + getLargo() > l.getCoordX() - 6 && getCoordX() + getLargo() <= l.getCoordX() + 6 && vX > 0) {
            vX = -vX;
            return true;
        }
        if (getCoordY() >= l.getCoordY() - getAlto() / 2 && getCoordY() <= l.getCoordY() + l.getAlto() / 2 && getCoordX() >= l.getCoordX() + l.getLargo() - 6 && getCoordX() < l.getCoordX() + l.getLargo() + 6 && vX < 0) {
            vX = -vX;
            return true;
        }
        if (getCoordX() + getLargo() >= l.getCoordX() && getCoordX() <= l.getCoordX() + l.getLargo()) {
            if (vY > 0) {
                if (getCoordY() + getAlto() >= l.getCoordY() && getCoordY() < l.getCoordY() + l.getAlto() && getCoordY() < l.getCoordY()) {
                    vY = -vY;
                    return true;
                }
            }
            else if (getCoordY() <= l.getCoordY() + l.getAlto() && getCoordY() > l.getCoordY() && getCoordY() + getAlto() > l.getCoordY()) {
                vY = -vY;
                return true;
            }
        }
        return false;
    }
}
