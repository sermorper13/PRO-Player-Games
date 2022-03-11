/*
Classe de gestió d'errors
 */
package activitat_avaluable;

/**
 *
 * @author Sergi Moreno
 */
public class Errors extends Exception {
    //DECLARACIÓ atribut de la clase
    //declaració enter per identificar l'error comès
    private int codiError;
    
    //Mètode constructor
    public Errors(int e) {
        super();
        this.codiError = e;
    }
    
    //Mètode funcional
    //retornarà el missatge adient a l'error comès
    @Override
    public String getMessage() {
        //String per representar el missatge a retornar
        String message = "";
        
        switch(codiError) {
            //cas on l'opció inserida no sigui vàlida
            case 0:
                message = "Opció inserida no vàlida. Elegir una altra opció";
                break;
            //cas on no s'ha generat partida abans d'actualitzar les dades
            case 1:
                message = "Partida no generada. Generi partida abans d'actualitzar dades";
                break;
        }
        return message;
    }
}
