package model;

/**
 * Classe modélisant l'objet Page utilisé pour l'affichage
 * @author sonia
 *
 */

public class Page {
	
	private int number; //Numero de la page actuelle
	
	private int maxlines; //Nombre de lignes par pages 
	
	public Page() {
        this.number = 1; // TO DO Modifier pour que le client puisse modifier ces valeurs
        this.maxlines = 10;
    }

    public int getCurrentPage() {
        return number;
    }

    public void setCurrentPage(int current) {
        this.number = current;
    }

    public int getMaxLines() {
        return maxlines;
    }

    public void setMaxLines(int maxLines) {
        this.maxlines = maxLines;
    }

	public int getFirstLine() {
		
		return number * maxlines;
	}

    
    
    
}
