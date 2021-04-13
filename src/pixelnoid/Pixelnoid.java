package pixelnoid;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.Font;

public class Pixelnoid extends JPanel
{
    public final int LARGO_VENTANA = 756;
    public final int ALTO_VENTANA = 668;
    public final int LARGO_ESCENARIO = 480;
    public final int ALTO_ESCENARIO = 660;
    public static final int VELOCIDAD = 60;
    public static final int MAX_NIVEL = 8;
    public static final int NUM_VIDAS = 15;
    public static final int VIDAS_INICIALES = 8;
    //---------------LIMITE IZQUIERDO DEL AREA DEL JUEGO ------------//
    public final int LIMITE_IZQUIERDO = 9;
    //---------------LIMITE DERECHO DEL AREA DEL JUEGO --------------//
    public final int LIMITE_DERECHO = 489;
    //---------------LIMITE SUPERIOR DEL AREA DEL JUEGO -------------//
    public final int LIMITE_SUPERIOR = 8 ;
    private GestorImagenes gestor;
    private Barra barra;
    private Pelota pel;
    private int nivelActual;
    private ArrayList<Ladrillo> ladrillos;
    private ArrayList<Premio> premios;
    private int vidas;
    private Bala bala;
    private ArrayList<Bala> balas;
    private SistemaPuntuacion s;
    private long timeEnRanking;
    private Font arcade;
    private Font arcade2;
    private Font arcade3;
    private Font fuenteJuego;
    private Font fuenteRanking;
    private Font fuenteRanking2;
    private Font fuenteRanking3;
    private Font fuenteRankingDefault;
    private int seleccion_menu;
    private boolean pedirDatos;
    private JFrame frame;
    private long tiempoEnGameOver;
    private long tiempoNivelSuperado;
    private int puntuacion;
    private int mejorPuntuacion;
    private Estado estadoActual;
    
    
    public Pixelnoid() {
        
        //------------Fuentes personalizadas ----------//
        
         try {
        
        arcade = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/silkworm.TTF")).deriveFont(20f);
        arcade2 = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/VCR_OSD_MONO_1.001.ttf")).deriveFont(20f);
        arcade3 = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/PressStart2P.ttf")).deriveFont(20f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(arcade);
        } catch (IOException e) {
             System.err.println("File not found....");
         }catch(Exception ex){
             System.err.println("Custom font not found.");
        }
         
        fuenteJuego = arcade.deriveFont(35);
        fuenteRanking = arcade3.deriveFont(50);
        fuenteRanking2= arcade3.deriveFont(50);
        fuenteRanking3= arcade3.deriveFont(50);
        fuenteRankingDefault = arcade3.deriveFont(30);
        
        //------------------------------------------//
        
        seleccion_menu = 0;
        gestor = new GestorImagenes();
        s = new SistemaPuntuacion();
        
        premios = new ArrayList<>();
        ladrillos = new ArrayList<>();
        try {
            
            final File f = new File(this.getClass().getClassLoader().getResource("puntuaciones").toURI());
            s = SistemaPuntuacion.recuperaFichTexto(f.getAbsolutePath());
        }
        catch (Exception e) {
            System.out.println("Casco");
        }
        mejorPuntuacion = s.getMejorPuntuacion().getPuntos();
        setPreferredSize(new Dimension(LARGO_VENTANA, ALTO_VENTANA));
        
        (frame = new JFrame("Pixelnoid | v1.1")).setDefaultCloseOperation(3);  //JFrame.EXIT_ON_CLOSE
        
        //----------------Elimina los botones de la ventana----------------//
        //frame.setUndecorated(true); 
        //-----------------------------------------------------------------//
        
        frame.add(this, "Center");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((pantalla.width - 640) / 2, (pantalla.height - 560) / 2);
        frame.setIconImage(this.gestor.getElement("iconoJuego.png"));
        setPreferredSize(new Dimension(LARGO_VENTANA, ALTO_VENTANA));
        cambiarEstado(Estado.PANTALLA_INICIO);
        frame.setResizable(false);
        frame.pack();
        setFocusable(true);
        frame.setVisible(true);
        addKeyListener(new Manejador());
        
        //--------------------- imprime coordenadas donde se da click con el mouse -------------------//
        
        frame.addMouseListener(new MouseAdapter() {
        // provides empty implementation of all
        // MouseListener`s methods, allowing us to
        // override only those which interests us                                                                     
        @Override //I override only one method for presentation
        public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());}});

        //--------------------------------------------------------------------------------------------//
    }
    
    public void inicializa() {
        barra = new Barra(this, "deslizador.png", 200, 640);
        bala = new Bala(this, "bala.png");
        pel = new Pelota(this, "pelota.png");
        vidas = VIDAS_INICIALES;
        nivelActual = 1;
        bala = null;
        pedirDatos = false;
        puntuacion = 0;
        generarNivel();
    }
    
    public void actualiza() {
        barra.mover();
        pel.mover(barra);
        for (int i = 0; i < premios.size(); ++i) {
            premios.get(i).mover();
        }
        if (bala != null) {
            bala.mover();
        }
        for (int i = 0; i < ladrillos.size(); ++i) {
            if (!ladrillos.get(i).estaVivo()) {
                ladrillos.remove(i);
            }
        }
        this.comprobarNivel();
    }
    
    @Override
    public void paint(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g;
        switch (estadoActual) {
            case PANTALLA_INICIO: {
                g2.drawImage(gestor.getElement("pantalla_principal" + seleccion_menu + ".jpg"), 0, 0, this);
                break;
            }
            case INSTRUCCIONES: {
                g2.drawImage(gestor.getElement("instrucciones.jpg"), 0, 0, this);
                break;
            }
            case JUGANDO: {
                this.paintZonaJuego(g);
                break;
            }
            case PAUSA: {
                this.paintZonaJuego(g);
                g.drawImage(gestor.getElement("pausa.png"),  9 , 8 , this);
                break;
            }
            case NIVEL_SUPERADO: {
                this.paintZonaJuego(g);
                g.drawImage(gestor.getElement("nivelsuperado.png"), 9 , 8 , this);
                break;
            }
            case JUEGO_SUPERADO: {
                this.paintZonaJuego(g);
                g2.drawImage(gestor.getElement("juegosuperado.png"), 9, 8, this);
                break;
            }
            case GAME_OVER: {
                this.paintZonaJuego(g);
                g2.drawImage(gestor.getElement("gameover.png"), 0, 0, this);
                break;
            }
            case RANKING: {
                g.drawImage(gestor.getElement("ranking.jpg"), 0, 0, this);
                g.setColor(Color.WHITE);
                for (int i = 0; i < 4; ++i) {
                    String nombre = s.getPuntuacion(i).getNombre();
                    int puntos = s.getPuntuacion(i).getPuntos();
                    switch (i) {
                        case 0: {
                            g.setFont(fuenteRanking);
                            g.drawString(nombre, 140, 270);
                            g.drawString(new StringBuilder(String.valueOf(puntos)).toString(), 420, 270);
                            break;
                        }
                        case 1: {
                            g.setFont(fuenteRanking2);
                            g.drawString(nombre, 140, 360);
                            g.drawString(new StringBuilder(String.valueOf(puntos)).toString(), 420, 360);
                            break;
                        }
                        case 2: {
                            g.setFont(fuenteRanking3);
                            g.drawString(nombre, 140, 440);
                            g.drawString(new StringBuilder(String.valueOf(puntos)).toString(), 420, 440);
                            break;
                        }
                        default: {
                            g.setFont(fuenteRankingDefault);
                            g.drawString(nombre, 140, 520);
                            g.drawString(new StringBuilder(String.valueOf(puntos)).toString(), 420, 520);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }
    
    public void paintZonaJuego(final Graphics g1) {
        g1.drawImage(gestor.getElement("plantilla_cereza.jpg"), 0, 0, this);
        
        g1.drawImage(gestor.getElement("fondo" + nivelActual + ".jpg"), 9, 8, this);
        g1.setFont(fuenteJuego);
        g1.setColor(Color.WHITE);
        final String puntos = Integer.toString(this.puntuacion);
        
        //Posicion de puntacion
        
        g1.drawString(puntos, 560, 240);
        
        //posicion mejorPuntuacion
        
        g1.drawString(new StringBuilder(String.valueOf(mejorPuntuacion)).toString(), 560, 350);
        switch (estadoActual) {
            case GAME_OVER: {
                for (int i = 0; i < ladrillos.size(); ++i) {
                    if (ladrillos.get(i).estaVivo()) {
                        ladrillos.get(i).paint(g1);
                    }
                }
                break;
            } 
            
            /*
            case JUEGO_SUPERADO: {
                g1.drawString("Superado!", 445, 315);
                
                //posicion de vidas
                
                g1.drawString(new StringBuilder(String.valueOf(vidas)).toString(), 640, 580);
                break;
            }
            */
            
            default: {
                
                //posicion de nivel actual
                
                switch (nivelActual) {
                    case 1: {
                        g1.drawString("1", 570, 460);
                        break;
                    }
                    case 2: {
                        g1.drawString("2", 570, 460);
                        break;
                    }
                    case 3: {
                        g1.drawString("3", 570, 460);
                        break;
                    }
                    case 4: {
                        g1.drawString("4", 570, 460);
                        break;
                    }
                    case 5: {
                        g1.drawString("5", 570, 460);
                        break;
                    }
                    case 6: {
                        g1.drawString("6", 570, 460);
                        break;
                    }
                    case 7: {
                        g1.drawString("7", 570, 460);
                        break;
                    }
                    case 8: {
                        g1.drawString("8", 570, 460);
                        break;
                    }
                }
                
                //posicion vidas 
                
                g1.drawString(new StringBuilder(String.valueOf(vidas)).toString(), 630, 580);
                barra.paint(g1);
                pel.paint(g1);
                for (int i = 0; i < ladrillos.size(); ++i) {
                    if (ladrillos.get(i).estaVivo()) {
                        ladrillos.get(i).paint(g1);
                    }
                }
                int posicion = 11;
                for (int j = 0; j < vidas; ++j) {
                    final BufferedImage b = gestor.getElement("des_vida.png");
                    g1.drawImage(b, posicion, 658, this); //455
                    posicion += b.getWidth() + 5;
                }
                for (final Premio p : premios) {
                    if (p.estaBajando()) {
                        p.paint(g1);
                    }
                }
                if (bala != null) {
                    bala.paint(g1);
                    break;
                }
                break;
            }
        }
    }
    
    public void comprobarNivel() {
        if (ladrillos.isEmpty() && nivelActual <= 8) {
            premios.clear();
            cambiarEstado(Estado.NIVEL_SUPERADO);
        }
    }
    
    public GestorImagenes getGestorImagenes() {
        return this.gestor;
    }
    
    public void jugar() {
        inicializa();
        while (true) {
            switch (estadoActual) {
                case PANTALLA_INICIO: {
                    try {
                        repaint();
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException ex) {}
                    continue;
                }
                case INSTRUCCIONES: {
                    try {
                        repaint();
                        Thread.sleep(200L);
                    }
                    catch (Exception ex2) {}
                    continue;
                }
                case JUGANDO: {
                    long proximaEjecucion = System.currentTimeMillis() + 20L;
                    choques();
                    actualiza();
                    repaint();
                    proximaEjecucion -= System.currentTimeMillis();
                    try {
                        Thread.sleep((proximaEjecucion > 0L) ? proximaEjecucion : 0L);
                    }
                    catch (InterruptedException ex3) {}
                    continue;
                }
                case PAUSA: {
                    try {
                        repaint();
                        Thread.sleep(100L);
                    }
                    catch (Exception ex4) {}
                    continue;
                }
                case NIVEL_SUPERADO: {
                    try {
                        if (System.currentTimeMillis() > tiempoNivelSuperado + 1000L) {  //default 5000L
                            if (nivelActual >= 8) {
                                cambiarEstado(Estado.JUEGO_SUPERADO);
                                continue;
                            }
                            ++nivelActual;
                            generarNivel();
                            cambiarEstado(Estado.JUGANDO);
                        }
                        repaint();
                        Thread.sleep(200L);
                    }
                    catch (Exception ex5) {}
                    continue;
                }
                case GAME_OVER: {
                    try {
                        if (pedirDatos) {
                            //String seleccion = JOptionPane.showInputDialog(frame, "Introduce tu nombre", "")
                            JOptionPane nombre = new JOptionPane();
                            nombre.setLocation(100,520);
                            
                            String seleccion = nombre.showInputDialog(frame, "Introduce tu nombre", "", JOptionPane.NO_OPTION);
                            
                            if (seleccion.length() > 8) {
                                seleccion = seleccion.substring(0, 8);
                            }
                            s.anadirPuntuacion(seleccion, puntuacion);
                            pedirDatos = false;
                            tiempoEnGameOver = System.currentTimeMillis();
                            
                        }
                        else if (System.currentTimeMillis() > tiempoEnGameOver + 5000L) {
                            
                            cambiarEstado(Estado.RANKING);
                            continue;
                        }
                        repaint();
                        Thread.sleep(250L);
                    }
                    catch (Exception ex6) {}
                    continue;
                }
                case JUEGO_SUPERADO: {
                    try {
                        repaint();
                        if (pedirDatos) {
                            JOptionPane nombre = new JOptionPane();
                            nombre.setLocation(120, 520);
                            String seleccion = JOptionPane.showInputDialog(frame, "Introduce tu nombre", "");
                            
                            if (seleccion.length() > 8) {
                                seleccion = seleccion.substring(0, 8);
                            }
                            s.anadirPuntuacion(seleccion, puntuacion);
                            pedirDatos = false;
                        }
                        Thread.sleep(200L);
                    }
                    catch (Exception ex7) {}
                    continue;
                }
                case RANKING: {
                    try {
                        if (System.currentTimeMillis() > timeEnRanking + 15000L) {
                            cambiarEstado(Estado.PANTALLA_INICIO);
                        }
                        repaint();
                        Thread.sleep(100L);
                    }
                    catch (Exception ex8) {}
                    continue;
                }
            }
        }
    }
    
    public void choques() {
        pel.rebota(null);
        if (pel.pasoBarra(barra)) {
            resetBarraBolaBala();
            --vidas;
            if (vidas < 0) {
                cambiarEstado(Estado.GAME_OVER);
            }
        }
        
        else {
            pel.rebota(barra);
            for (Ladrillo l : ladrillos) {
                if (l.estaVivo()) {
                    if (bala != null && bala.estaDisparando() && bala.destruir(l)) {
                        l.rebota(bala);
                        if (!l.estaVivo()) {
                            puntuacion += l.getPuntos();
                            if (mejorPuntuacion < puntuacion) {
                                mejorPuntuacion = puntuacion;
                            }
                        }
                    }
                    if (!pel.rebota(l)) {
                        continue;
                    }
                    l.rebota(pel);
                    if (!l.estaVivo()) {
                        puntuacion += l.getPuntos();
                        if (mejorPuntuacion < puntuacion) {
                            mejorPuntuacion = puntuacion;
                        }
                    }
                    if (l.getPremio() == null || l.estaVivo()) {
                        continue;
                    }
                    l.getPremio().setBajando(true);
                }
            }
            for (int i = 0; i < premios.size(); ++i) {
                Premio p = premios.get(i);
                if (p.estaBajando()) {
                    if (p.premioRecogido(barra)) {
                        if (p instanceof Nitro) {
                            pel.acelera();
                        }
                        else if (p instanceof Vida) {
                            ++vidas;
                        }
                        else if (p instanceof Reductor) {
                            pel.reducir();
                        }
                        else if (p instanceof Laser) {
                            barra.cambioDeBarra("des_laser.png");
                            if (bala == null) {
                                bala = new Bala(this, "bala.png");
                            }
                            
                            
                        } 
                        else if (p instanceof Expansor) {
                            barra.cambioDeBarra("ext_deslizador.png");
                            if (bala != null) {
                                bala = null;
                            }
                            
                        }
                        premios.remove(i);
                    }

                    
                    else if (p.getCoordY() + p.getAlto() > 660) {
                        premios.remove(i);
                    }
                }
            }
        }
    }
    
    public void generarNivel() {
        Pixel2Level pixIm = new Pixel2Level();
        int[][] arrayLadrillos = null;
        resetBarraBolaBala();
        switch (nivelActual) {
            
            case 1:
                //------------------------Test de niveles----------------------------------//
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/space_invaders.png");//1
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/snake.png");//2
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/feather.png");//3
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/star.png");//4
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/moon.png");//5
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/coin.png");//6
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/boo.png");//7
                //arrayLadrillos = pixIm.pixImToArray("src/niveles/pacman.png");//8
                //-------------------------------------------------------------------------//
                arrayLadrillos = pixIm.pixImToArray("src/niveles/space_invaders.png");
                break;
            
            case 2: 
                arrayLadrillos = pixIm.pixImToArray("src/niveles/snake.png");
                     
                break;
            case 3: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/feather.png");

                break;
            }
            case 4: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/star.png");
                  
                break;
            }
            case 5: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/moon.png");
                break;
            }
            case 6: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/coin.png");
                break;
            }
            case 7: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/boo.png");
                break;
            }
            case 8: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/pacman.png");
                break;
            }
            
            //----------test de final de juego----------------------------------------//
            /*
           case 1:
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                break;
            
            case 2: 
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                     
                break;
            case 3: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");

                break;
            }
            case 4: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                  
                break;
            }
            case 5: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                break;
            }
            case 6: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                break;
            }
            case 7: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                break;
            }
            case 8: {
                arrayLadrillos = pixIm.pixImToArray("src/niveles/test_end_game.png");
                break;
            }*/
            //-----------------------------------------------------------------------//
        }
        ladrillos.clear();
        premios.clear();
        for (int i = 0; i < arrayLadrillos.length; ++i) {
            for (int j = 0; j < arrayLadrillos[0].length; ++j) {
                if (arrayLadrillos[i][j] > 0) {
                    final Ladrillo e = new Ladrillo(this, "ladrillo.png", arrayLadrillos[i][j], 0, 100);
                    e.setCoordX(e.getLargo() * j + e.getPosXInicial());
                    e.setCoordY(e.getAlto() * i + e.getPosYInicial());
                    ladrillos.add(e);
                }
                else if (arrayLadrillos[i][j] < 0) {
                    final Ladrillo e = new Ladrillo(this, "ladrillo.png", arrayLadrillos[i][j], Math.abs(arrayLadrillos[i][j]), Math.abs(arrayLadrillos[i][j]) * 250);
                    e.setCoordX(e.getLargo() * j + e.getPosXInicial());
                    e.setCoordY(e.getAlto() * i + e.getPosYInicial());
                    ladrillos.add(e);
                }
            }
        }
        
        
        
        int numPremios = 0;
        final Random r = new Random();
        //numPremios = r.nextInt(7) + 6;
        numPremios = r.nextInt(7) + 8;
        if (Math.random() > 0.5) {
            final int ladrilloPremiado = r.nextInt(ladrillos.size());
            final Ladrillo aux = ladrillos.get(ladrilloPremiado);
            aux.setPremio(5);
            premios.add(aux.getPremio());
            --numPremios;
        }
        for (int k = 1; k < numPremios; ++k) {
            final int ladrilloPremiado = r.nextInt(ladrillos.size());
            final Ladrillo aux = ladrillos.get(ladrilloPremiado);
            final int premio = r.nextInt(4) + 1;
            aux.setPremio(premio);
            premios.add(aux.getPremio());
        }
    }
    
    private void resetBarraBolaBala() {
        barra.resetearBarra();
        pel.resetearPelota(barra.getCoordX(), barra.getCoordY());
        bala = null;

    }
    
    private void cambiarEstado(final Estado estado) {
        switch (estado) {
            case PANTALLA_INICIO: {
                inicializa();
                break;
            }
            case NIVEL_SUPERADO: {
                tiempoNivelSuperado = System.currentTimeMillis();
                break;
            }
            case GAME_OVER: {
                if (s.entroEnRanking(puntuacion)) {
                    pedirDatos = true;
                }
                this.tiempoEnGameOver = System.currentTimeMillis();
                break;
            }
            case JUEGO_SUPERADO: {
                if (s.entroEnRanking(puntuacion)) {
                    pedirDatos = true;
                    break;
                }
                break;
            }
            case RANKING: {
                timeEnRanking = System.currentTimeMillis();
                break;
            }
        }
        estadoActual = estado;
    }
    
    public static void main(final String[] args) {
        final Pixelnoid arkanoid = new Pixelnoid();
        arkanoid.jugar();
    }
    
    class Manejador implements KeyListener
    {
        @Override
        public void keyPressed(final KeyEvent e) {
            switch (estadoActual) {
                case PANTALLA_INICIO: {
                    if (e.getKeyCode() == 40) {
                        seleccion_menu=(seleccion_menu + 1) % 4;
                        //break;
                    }
                    if (e.getKeyCode() == 38) {
                        seleccion_menu = seleccion_menu - 1;
                        if (seleccion_menu < 0) {
                            seleccion_menu = 3;
                            //break;
                        }
                       // break;
                    }
                    else {
                        if (e.getKeyCode() == 10 && seleccion_menu == 0) {
                            cambiarEstado(Estado.JUGANDO);
                        }
                        if (e.getKeyCode() == 10 && seleccion_menu == 1) {
                            cambiarEstado(Estado.INSTRUCCIONES);
                        }
                        if (e.getKeyCode() == 10 && seleccion_menu == 2) {
                            cambiarEstado(Estado.RANKING);
                        }
                        if (e.getKeyCode() == 10 && seleccion_menu == 3) {
                            System.exit(0);
                            break;
                        }
                        break;
                    }
                    break;
                }
                case INSTRUCCIONES: {
                    if (e.getKeyCode() == 27) {
                        cambiarEstado(Estado.PANTALLA_INICIO);
                        break;
                    }
                    break;
                }
                case JUGANDO: {
                    if (e.getKeyCode() == 86 && vidas < NUM_VIDAS) {
                        vidas++;
                        break;
                    }
                    if (e.getKeyCode() == 80) {
                        cambiarEstado(Estado.PAUSA);
                        break;
                    }
                    //si se presiona esc cuando se ha cargado la pantalla del primer nivel y aún no se ha empezado
                    //a jugar se vuelve a la pantalla inicial
                    
                    if (e.getKeyCode() == 27&&puntuacion==0) {  //esc -> key code 27
                        cambiarEstado(Estado.PAUSA);
                        cambiarEstado(Estado.PANTALLA_INICIO);
                        break;
                    }
                    
                    //----------------------------------------------------------------------------//
                    barra.keyPressed(e);
                    if (e.getKeyCode() == 32 && pel.estaParada()) {
                        pel.pelotaStart();
                    }
                    if (e.getKeyCode() == 32 && bala != null&& !bala.estaDisparando() && !pel.estaParada()) { 
                        bala.disparando(barra.getCoordX() + 3 * bala.getLargo() / 2, barra.getCoordY() - bala.getAlto());
                        
                        break;
                    }
                    break;
                }
                case PAUSA: {
                    if (e.getKeyCode() == 80) {
                        cambiarEstado(Estado.JUGANDO);
                        break;
                    }
                    break;
                }
                case JUEGO_SUPERADO: {
                    if (e.getKeyCode() == 10) {
                        cambiarEstado(Estado.RANKING);
                        break;
                    }
                    break;
                }
                case RANKING: {
                    if (e.getKeyCode() == 27) {
                        cambiarEstado(Estado.PANTALLA_INICIO);
                        break;
                    }
                    break;
                }
                //---------CASE GAME_OVER---------//
                case GAME_OVER: {
                    if (e.getKeyCode() == 27) {  //press esc to continue
                        cambiarEstado(Estado.PANTALLA_INICIO);
                        break;
                    }
                    break;
                }
                
                //--------------------------------//
            }
        }
        
        @Override
        public void keyReleased(final KeyEvent e) {
            switch (estadoActual) {
                case JUGANDO: {
                    if (e.getKeyCode() != 10) {
                        barra.keyReleased(e);
                        break;
                    }
                    break;
                }
                case PAUSA: {
                    barra.keyReleased(e);
                    break;
                }
            }
        }
        
        @Override
        public void keyTyped(final KeyEvent e) {
        }
    }
}
