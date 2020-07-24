package com.excilys.java.cdb.model;

/**
 * Classe modélisant l'objet Page utilisé pour l'affichage
 * @author sonia
 *
 */

public class Page {
	
	private int number; 
	private int firstLine;
	private int maxlines; 
	private int nbTotalPages; 	
	
	
	public Page() {
        this.number = 1;
        this.maxlines = 10;
        this.firstLine=0;
    }

    public int getNumberPage() {
        return this.number;
    }

    public void setfirstLine(int line) {
        this.firstLine = line;
    }

    public int getFirstLine() {
        return this.firstLine;
    }

    public void setNumberPage(int num) {
        this.number = num;
    }
    
    public int getMaxLines() {
        return maxlines;
    }

    public void setMaxLines(int maxLines) {
        this.maxlines = maxLines;
    }
    
    public int getNbTotalPages() {
        return this.nbTotalPages;
    }

    public void setNbTotalPages(int nb) {
        this.nbTotalPages = nb;
    }
    
    /**
     * Retourne le numero de la 1ere ligne de la page courante
     * @return
     */
	public int calculFirstLine() {
		
		this.firstLine = (number-1) * maxlines;
		return this.firstLine;
	}
	
	/**
	 * Teste si la page courrante posséde une page précédente ou pas
	 * @return
	 */
	public boolean hasPrevious() {
		if(getNumberPage()==1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Si elle existe, on passe à la page précédente
	 */
	public int getPreviousPage() {
		if(this.hasPrevious()) {
			this.number--;
			this.firstLine-=maxlines;
		}
		return this.number;
	}
	
	/**
	 * Passe de la page courante à la suivante
	 * @param total
	 */
	public int getNextPage() {
		if(this.hasNext()) {
			this.number++;
			this.firstLine+=maxlines;
		}
		return this.number;

	}

	/**
	 * Teste si la page courante est la dernière ou pas
	 * @param total
	 * @return
	 */
	public boolean hasNext() {
		if(getNumberPage() < getNbTotalPages()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calcule le nombre total de pages nécéssaires pour afficher toutes les entrées 
	 * @param nb le nombre total d'entrées dans la base de données
	 * @return le nombre de pages nécéssaires pour toute les afficher
	 */
	public int getTotalPages(int nbTotal) {
		if(nbTotal%maxlines==0) {
			this.nbTotalPages=nbTotal/maxlines;
			return this.nbTotalPages;
		} 
		this.nbTotalPages=nbTotal/maxlines+1;
		return this.nbTotalPages;	
	}

}
