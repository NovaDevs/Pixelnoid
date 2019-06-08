package pixelnoid;

import java.awt.*;
import java.awt.image.*;

public abstract class Elemento
{
    private int coordX;
    private int coordY;
    private int largo;
    private int alto;
    private String nombre;
    private Pixelnoid escenario;
    private static GestorImagenes gestor;
    
    public Elemento(final Pixelnoid escenario, final String nombre) {
        this.escenario = escenario;
        Elemento.gestor = escenario.getGestorImagenes();
        this.nombre = nombre;
        largo = Elemento.gestor.getElement(nombre).getWidth();
        alto = Elemento.gestor.getElement(nombre).getHeight();
    }
    
    public void paint(final Graphics g) {
        g.drawImage(Elemento.gestor.getElement(nombre), coordX, coordY, escenario);
    }
    
    public int getCoordX() {
        return this.coordX;
    }
    
    public int getCoordY() {
        return this.coordY;
    }
    
    public int getLargo() {
        return this.largo;
    }
    
    public int getAlto() {
        return this.alto;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public GestorImagenes getGestorImagenes() {
        return Elemento.gestor;
    }
    
    public Pixelnoid getEscenario() {
        return this.escenario;
    }
    
    public void setCoordX(final int x) {
        this.coordX = x;
    }
    
    public void setCoordY(final int y) {
        this.coordY = y;
    }
    
    public void setAlto(final int a) {
        this.alto = a;
    }
    
    public void setLargo(final int l) {
        this.largo = l;
    }
    
    public void setNombre(final String s) {
        this.nombre = s;
    }
}
