/*
Gestió de les dades d’un joc que hi participen 10 jugadors i per cada jugador 
es guarda el nom del jugador, els minuts jugats i les victòries aconseguides, 
com que el jugador podrà jugar amb dos rols diferents ATACANT o DEFENSOR, 
les victòries es guardaran per a cada un dels rols. Aquestes dades es guarden 
dins un fitxer de jugadors. S’haurà d’actualitzar el fitxer de jugadors amb 
les dades d'una nova partida, que contenen el nombre de jugadors de la partida (2-5) 
i per cada jugador és té el codi,posició que ocupa dins el fitxer de jugadors; si el jugador 
ha aconseguit la victòria o no en aquesta partida, i el rol amb el que ha jugat 
(atacant o defensor). Cada partida dura 10 minuts.
 */
package activitat_avaluable;

/**
 *
 * @author Sergi Moreno
 */
public class Activitat_Avaluable {
    public static void main(String[] args) {
        new Activitat_Avaluable().programaPrincipal();
    }
    
    //programa principal
    private void programaPrincipal() {
        //DECLARACIONS
        //declaració variable booleana per representar la sortida de fitxer
        boolean sortir = false;
        //declaració variable booleana per representar si se poden actualitzar 
        //les dades o no
        boolean noActualitzar = true;
        
        //ACCIONS
        //BUCLE ITERATIU
        while (!sortir) {
            try {
                //visualitza el menú
                menu();
                
                //lectura enter que representa l'opció elegida per l'usuari
                int opcio = readInt("\tInserir opció: ");

                switch (opcio) {
                    case 1:
                        nousJugadors();
                        break;
                    case 2:
                        novaPartida();
                        //se pot fer l'actualització
                        noActualitzar = false;
                        break;
                    case 3:
                        //verifica si se pot actualitzar
                        if (!noActualitzar) {
                            actualitzaFitxerJugadors();
                            //ara no se pot actualitzar
                            noActualitzar = true;
                        } else {
                            //s'ha comès un error
                            throw new Errors(1);
                        }
                        break;
                    case 0:
                        //sortida del programa
                        sortir = true;
                        break;
                    default:
                        //s'ha comès un error
                        throw new Errors(0);
                }
            } catch (Errors ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    //registra els noms de 10 jugadors seleccionats aleatòriament i visualitza
    //el contingut del fitxer creat 
    private void nousJugadors() {
        //establiment d'enllaç lógic amb fitxer físic
        FitxerJugadors fj = new FitxerJugadors("jugadors.dat");
        //visualitza les dades del fitxer de jugadors creat
        System.out.print("\nContingut del fitxer de jugadors: ");
        System.out.println(fj.inserirJugadors());
    }
    
    //crea una partida amb un nom aleatori de jugadors (2-5) i les dades
    //de la partida
    private void novaPartida() {
        //ACCIONS ESCRIPTURA FITXER
        //establiment d'enllaç lógic d'escriptura amb fitxer físic
        FitxerPartidaOut fw = new FitxerPartidaOut("partida.dat");
        //genera una partida amb un nom aleatori de jugadors (2-5)
        fw.generaPartida();
        //tanca enllaç lògic d'escriptura
        fw.close();
        
        //ACCIONS LECTURA FITXER
        //establiment d'enllaç lógic de lectura amb fitxer físic
        FitxerPartidaIn fr = new FitxerPartidaIn("partida.dat");
        //visualitza les dades de la partida
        System.out.println("\nContingut del fitxer de partida: ");
        fr.lecturaPartida();
        //tanca enllaç lògic de lectura
        fr.close();
    }
    
    //actualitza les dades dels jugadors que han jugat la partida 
    private void actualitzaFitxerJugadors() {
        //DECLARACIONS
        //inicialitza objecte JugadorPartida per emmagatzemar els jugadors
        JugadorPartida jugador = new JugadorPartida();
        //establiment d'enllaç lógic de lectura amb fitxer físic
        FitxerPartidaIn fr = new FitxerPartidaIn("partida.dat");
        //establiment d'enllaç lógic de lectura/escriptura amb fitxer físic
        FitxerJugadors f = new FitxerJugadors("jugadors.dat");
            
        //ACCIONS
        //lectura objecte JugadorPartida de fitxer
        jugador = fr.lecturaJugador();
        //BUCLE ITERATIU a nivell de JugadorPartida
        while (jugador.noCentinela()) {
            //actualitza les dades dels jugadors
            f.actualitza(jugador);
            //lectura del següent jugador
            jugador = fr.lecturaJugador();
        }
        
        //tanca enllaç lògic de lectura
        fr.close();
        
        //visualització del fitxer de jugadors actualitzat
        System.out.print("\nContingut del fitxer de jugadors actualitzat: ");
        System.out.println(f.lecturaFitxerJugadors());
    }
    
    //visualitza en pantalla el menú
    private void menu() {
        System.out.println("\nGESTIÓ DE JOC\n");
        System.out.println("\t1. Inicialitza fitxer jugadors");
        System.out.println("\t2. Genera partida");
        System.out.println("\t3. Actualitza jugadors amb la partida actual");
        System.out.println("\t0. Sortida\n\n");
    }
    
    //visualitza un missatge en pantalla y retorna un enter llegit de teclat
    private int readInt(String s) {
        System.out.print(s);
        return LT.lecturaEntero();
    }
}
