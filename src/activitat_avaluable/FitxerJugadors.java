/*
Classe FitxerJugadors, tractament fitxers d’accés aleatori 
 */
package activitat_avaluable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class FitxerJugadors {
    //DECLARACIONS atributs de la clase
    //d'objecte
    private File arxiu = null;
    private RandomAccessFile f = null;
    
    //atributs de clase
    //mida nom del jugador
    private static final int MIDANOM = 20;
    //mida registre jugador (mida chars) + (mida ints)
    private static final int MIDAREG = (MIDANOM * 2) + (3 * 4);
    
    //Mètode constructor
    public FitxerJugadors(String nom) {
        try {
            //establiment enllaç lògic d'accés aleatori
            arxiu = new File(nom);
            f = new RandomAccessFile(arxiu,"rw");
            f.close();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    //Mètodes funcionals
    //registra els noms de 10 jugadors seleccionats aleatòriament i retorn
    //el contingut del fitxer creat
    public String inserirJugadors() {
        try {
            //DECLARACIONS
            //declaració objecte de la clase Random per calcular nombres aleatoris
            Random random = new Random();
            //declaració variable entera per emmagatzemar la posició trobada aleatòriament
            int pos;
            //declaració String per emmagatzemar el nom del jugador seleccionat
            String nom = "";
            //declaració array d'enters on emmagatzarem les posicions ocupades pels jugadors
            //que ja han estat seleccionats
            int [] num = new int[10];
            
            //establiment d'enllaç lógic de lectura/escriptura amb fitxer físic
            f = new RandomAccessFile(arxiu,"rw");
            
            //BUCLE ITERATIU a nivell de jugador de fitxer
            for (int i = 0;i < 10;i++) {
                try {
                    //càlcul d'una posició aleatòria
                    pos = random.nextInt(510);
                    
                    //comprovar que la posició elegida no ho havia estat prèviament
                    for (int m = 0;m < i;m++) {
                        if (pos == num[m]) {
                            pos = random.nextInt(510);
                            m = 0;
                        }
                    }
                    //emmagatzemam la posició en fitxer del jugador seleccionat
                    num[i] = pos;

                    //inicialitzam un objecte BufferedReader per lletgir les línies del
                    //fitxer de noms
                    BufferedReader fr = new BufferedReader(new FileReader("LlistaNoms.txt"));

                    //lectura de línies successivament fins a arribar a la línia elegida aleatòriament
                    for (int j = 0;j < pos;j++) {   fr.readLine();  }

                    //assignament de la línia a la String nom, on trobarem el nom del jugador
                    nom = fr.readLine();

                    //escriptura del nom del jugador i inicialització de les seves dades a fitxer
                    inicialitzarJugador(nom);
                    //tancam enllaç lógic
                    fr.close();
                } catch (IOException ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        //lectura del contingut del fitxer de jugadors
        return lecturaFitxerJugadors();
    }

    //actualitza les dades dels jugadors que han jugat la partida amb la informació 
    //del fitxer de partida, prèviament llegint les dades que ja havien aconseguit 
    //els jugadors fins el moment
    public void actualitza(JugadorPartida jugador) {
        try {
            //establiment d'enllaç lógic de lectura/escriptura amb fitxer físic
            f = new RandomAccessFile(arxiu,"rw");
            
            //ens situam dins el fitxer a la posició del jugador que passam per paràmetre
            //saltam també la posició del nom del jugador
            f.seek(jugador.getCodi() * MIDAREG + 2 * MIDANOM);

            //lectura de les estadístiques del jugador
            //temps jugat
            int temps = f.readInt();
            //victòries en atac
            int atac = f.readInt();
            //victòries en defensa
            int defensa = f.readInt();
            
            //ens situam dins el fitxer a la posició del jugador que passam per paràmetre
            //saltam també la posició del nom del jugador
            f.seek(jugador.getCodi() * MIDAREG + 2 * MIDANOM);
            //actualització del temps jugat
            f.writeInt(temps + 10);
            //comprovar si el jugador ha jugat com ATACANT o com DEFENSOR
            //ATACANT
            if (jugador.getRol() == Rol.values()[0]) {
                //si ha guanyat incrementam les victòries com ATACANT
                if (jugador.getVictoria()) {
                    f.writeInt(atac + 1);
                }
              //DEFENSOR
            } else {
                //botam les dades d'ATACANT
                f.readInt();
                //si ha guanyat incrementam les victòries com DEFENSOR
                if (jugador.getVictoria()) {
                    f.writeInt(defensa + 1);
                }
            }
            f.close();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    //lectura del contingut del fitxer de jugadors
    public String lecturaFitxerJugadors() {
        //DECLARACIONS
        //declaració String que representarà el contingut del fitxer
        String contingut = "";
        try {
            //establiment d'enllaç lógic de lectura/escriptura amb fitxer físic
            f = new RandomAccessFile(arxiu,"r");
            //declaracions d'enters per emmagatzemar les dades dels jugadors dels fitxers
            int atac,temps,defensa;
            
            //càlcul del nombre de registres dins fitxer
            long numReg = f.length()/MIDAREG;
            
            //BUCLE ITERATIU a nivell de registre
            for (int i = 0;i < numReg;i++) {
                
                //concatenam String amb les dades de fitxer
                contingut += "\nJugador: ";
                //llegim el nom del jugador
                for (int j = 0;j < MIDANOM;j++) {
                    contingut += f.readChar();  
                }
                //llegim les seves estadístiques
                temps = f.readInt();
                atac = f.readInt();
                defensa= f.readInt();
                
                //concatenam String amb les dades
                contingut += "/ Temps jugat: " +  temps + " / Victòries atacant:" 
                             + atac + " / Victòries defensor: " + defensa;
            }
            //tancam enllaç lògic
            f.close();  
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return contingut;
    }
    
    //retorna una String amb el nom del jugador amb un nombre màxim de caràcters,
    //afegint " " en cas de fer falta
    private String toMidaMaxim(String s) {
        if (s.length() < MIDANOM) {
            for (int i = s.length();i < MIDANOM;i++) {
                s += " ";
            }
        } else {
            s = s.substring(0, MIDAREG - 1);
        }
        return s;
    }
    
    //escriptura en fitxer d'accés aleatori del nou jugador seleccionat amb dades a 0
    private void inicialitzarJugador(String nom) {
        try {
            f.writeChars(toMidaMaxim(nom));
            f.writeInt(0);
            f.writeInt(0);
            f.writeInt(0);
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
