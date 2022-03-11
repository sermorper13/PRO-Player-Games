/*
Classe FitxerPartidaIn, lectura objectes de fitxer
 */
package activitat_avaluable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FitxerPartidaIn {
    //DECLARACIÓ atribut de la clase
    private ObjectInputStream fr = null;
    
    //Mètode constructor
    public FitxerPartidaIn(String arxiu) {
        try {
            //establiment enllaç lògic de lectura Object de fitxer
            fr = new ObjectInputStream(new FileInputStream(arxiu));
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    //Mètodes funcionals
    //lectura i visualització en pantalla dels objectes jugadors que han jugat la partida generada
    public void lecturaPartida() {
        try {
            ///inicialitza objecte JugadorPartida per emmagatzemar els jugadors
            JugadorPartida jugador = new JugadorPartida();
            //lectura objecte JugadorPartida de fitxer
            jugador = (JugadorPartida) fr.readObject();
            
            //BUCLE ITERATIVO a nivell de JugadorPartida
            while (jugador.noCentinela()) {
                //visualització jugador iterat
                System.out.print(jugador);
                //lectura següent jugador
                jugador = (JugadorPartida) fr.readObject();
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    //retorna un jugador que ha jugat la partida generada
    public JugadorPartida lecturaJugador() {
        JugadorPartida jugador = new JugadorPartida();
        try {
            //lectura d'un objecte de fitxer i conversió a objecte JugadorPartida
            jugador = (JugadorPartida) fr.readObject();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return jugador;
    }
    
    //tancament enllaç lògic
    public void close() {
        try {
            if (fr != null) {
                fr.close();
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
