/*
Classe JugadorPartida, classe serialitzable 
 */
package activitat_avaluable;

import java.io.Serializable;

public class JugadorPartida implements Serializable {
    //DECLARACIONS atributs de la clase
    //d'objecte
    private int codi;
    private boolean victoria;
    private Rol rol;
    
    //de clase
    //CENTINELA, ens servirà per marcar el final de fitxer de partida
    public static final JugadorPartida CENTINELA = new JugadorPartida(999,false,Rol.ATACANT);
    
    //Mètodes constructors
    public JugadorPartida() {
        
    }
    
    public JugadorPartida(int c,boolean v,Rol r) {
        this.codi = c;
        this.victoria = v;
        this.rol = r;
    }
    
    //Mètodes funcionals
    //mètode per permetre la visualització en string d'un objecte JugadorPartida
    @Override
    public String toString() {
        return "JugadorPartida(codi="+codi+", victòria="+victoria+", rol="+rol+")\n";
    }
    
    //Mètodes set
    public void setCodi(int c) {
        codi = c;
    }
    
    public void setVictoria(boolean v) {
        victoria = v;
    }
    
    public void setRol(Rol r) {
        rol = r;
    }
    
    //Mètodes get
    public int getCodi() {
        return codi;
    }
    
    public boolean getVictoria() {
        return victoria;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    //verifica que un jugador iterat no sigui el centinela
    public boolean noCentinela() {
        return codi != CENTINELA.codi;
    }
}
