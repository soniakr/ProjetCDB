package com.excilys.formation.cbd.exceptions;

public class ValidatorException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ValidatorException() {
		super();
	}
	
	/**
	   * Constructeur avec  argument et l'erreur 
	   */
	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	/**
	   * Constructeur avec un message en argument
	   * @param message qui est le message à envoyer en cas d'exception liée à la création de model ne respectant les règles de création
	   */
	public ValidatorException(String message) {
		super(message);
	}
	
	
	/**
	   * Constructeur avec une erreur en argument
	   * @param cause: erreur levéee
	   */
	public ValidatorException(Throwable cause) {
		super(cause);
}

}
