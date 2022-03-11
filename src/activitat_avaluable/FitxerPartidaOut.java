/*
Classe FitxerPartidaOut, escriptura d'objectes a fitxer
*/
package activitat_avaluable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class FitxerPartidaOut {
    //DECLARACIONS atributs de la clase
    private ObjectOutputStream fw = null;
    
    //Mètode constructor
    public FitxerPartidaOut(String arxiu) {
        try {
            //establiment enllaç lògic d'escriptura Object de fitxer
            fw = new ObjectOutputStream(new FileOutputStream(arxiu));
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    //Mètodes funcionals
    //selecciona un cert nombre de jugadors aleatoris per jugar la partida i 
    //escriu les dades aleatòries generades en haver jugat la partida
    public void generaPartida() {
        //DECLARACIONS 
        //declaració objecte de la clase Random que ens permetrà el càlcul de nombres
        //aleatoris
        Random numero = new Random();
        //càlcul d'un nombre de jugadors aleatori, entre 2 i 5, per jugar la partida
        int nombreJugadors = numero.nextInt(4) + 2;
        //declaració array d'enters per emmagatzemar les posicions dels jugadors
        //que ja han estat seleccionats
        int [] posicions = new int[nombreJugadors];
        
        //ACCIONS
        //BUCLE ITERATIU a nivell de JugadorPartida
        for (int i = 0;i < nombreJugadors;i++) {
            try {
                //inicialització d'un objecte JugadorPartida
                JugadorPartida jugador = new JugadorPartida();
                //càlcul d'un nombre aleatori per seleccionar el jugador 
                //segons la seva posició dins fitxer
                int pos = numero.nextInt(10) + 1;
                
                //comprovar que el codi no havia estat elegit prèviament
                int m = 0;
                while (m < i) {
                    if (pos == posicions[m]) {
                        pos = (numero.nextInt(10) + 1);
                        m = 0;
                    }
                    else {
                        m++;
                    }
                }
                //emmagatzemam el codi del jugador seleccionat
                posicions[i] = pos;
                //set codi al nou objecte jugador
                jugador.setCodi((pos - 1));
                
                //càlcul d'un victòria o derrota aleatòria
                pos = numero.nextInt(2);
                if (pos == 0) {
                    //set victòria
                    jugador.setVictoria(true);
                } else {
                    //set derrota
                    jugador.setVictoria(false);
                }
                
                //càlcul d'un rol aleatori, entre ATACANT i DEFENSOR
                pos = numero.nextInt(2);
                //set rol elegit
                jugador.setRol(Rol.values()[pos]);
                //escriptura de l'objecte JugadorPartida dins fitxer
                fw.writeObject(jugador);
            } catch (IOException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }
    
    //escriptura objecte centinela al final de fitxer i tancament enllaç lògic
    public void close() {
        try {
            if (fw != null) {               
                fw.writeObject(JugadorPartida.CENTINELA);
                fw.close();
            }
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
