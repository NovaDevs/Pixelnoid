package pixelnoid;

import java.io.*;

public class SistemaPuntuacion
{
    private Puntuacion[] mapa;
    
    public SistemaPuntuacion() {
        this.mapa = new Puntuacion[4];
    }
    
    public Puntuacion getPuntuacion(final int posicion) {
        return this.mapa[posicion];
    }
    
    public boolean entroEnRanking(final int punt) {
        for (int i = mapa.length - 1; i >= 0; --i) {
            if (mapa[i].puntos < punt) {
                return true;
            }
        }
        return false;
    }
    
    public boolean anadirPuntuacion(final String nom, final int puntos) {
        int pos = -1;
        for (int i = mapa.length - 1; i >= 0; --i) {
            if (mapa[i].puntos < puntos) {
                pos = i;
            }
        }
        if (pos == -1) {
            return false;
        }
        for (int i = mapa.length - 1; i > pos; --i) {
             mapa[i] = mapa[i - 1];
        }
        this.mapa[pos] = new Puntuacion(pos, puntos, nom);
        try {
            this.grabaFichTexto("puntuaciones");
        }
        catch (Exception ex) {}
        return true;
    }
    
    public Puntuacion getMejorPuntuacion() {
        return this.mapa[0];
    }
    
    public Puntuacion getPosicion(final int puntuacion) {
        int pos = -1;
        for (int i = this.mapa.length - 1; i >= 0; --i) {
            if (this.mapa[i].puntos < puntuacion) {
                pos = i;
            }
        }
        if (pos == -1) {
            return null;
        }
        return this.getPuntuacion(pos);
    }
    
    private void inicializa(final int pos, final int puntos, final String nom) {
        this.mapa[pos] = new Puntuacion(pos, puntos, nom);
    }
    
    public void grabaFichTexto(final String nomFich) throws IOException, FileNotFoundException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(nomFich));
            Puntuacion[] mapa;
            for (int length = (mapa = this.mapa).length, i = 0; i < length; ++i) {
                final Puntuacion c = mapa[i];
                out.println(c.toString());
            }
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
        if (out != null) {
            out.close();
        }
    }
    
    public static SistemaPuntuacion recuperaFichTexto(final String nomFich) throws IOException {
        final File f = new File(nomFich);
        BufferedReader ent = null;
        final SistemaPuntuacion s = new SistemaPuntuacion();
        try {
            ent = new BufferedReader(new FileReader(f));
            String str;
            do {
                str = ent.readLine();
                if (str != null) {
                    final String[] aux = str.split(",");
                    final int auxPos = Integer.parseInt(aux[0]);
                    final int auxPuntos = Integer.parseInt(aux[1]);
                    final String auxNombre = aux[2];
                    s.inicializa(auxPos, auxPuntos, auxNombre);
                }
            } while (str != null);
            return s;
        }
        finally {
            if (ent != null) {
                ent.close();
            }
        }
    }
    
    public class Puntuacion
    {
        private int puntos;
        private int pos;
        private String nombre;
        
        public Puntuacion(final int pos, final int p, final String n) {
            this.puntos = p;
            this.nombre = n;
            this.pos = pos;
        }
        
        public int getPos() {
            return this.pos + 1;
        }
        
        public int getPuntos() {
            return this.puntos;
        }
        
        public String getNombre() {
            return this.nombre;
        }
        /*
        @Override
        public String toString() {
            return String.valueOf(this.pos) + "," + this.puntos + "," + this.nombre;
        }
        */
        
        @Override
        public String toString() {
            return String.valueOf(getPos()) + "," + getPuntos()+ "," + getNombre();
        }
        
        //static /* synthetic */ void access$1(final Puntuacion puntuacion, final int pos) {
        // puntuacion.pos = pos;
        //}
    }
}
