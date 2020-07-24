package com.excilys.java.cdb.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.java.cdb.ui.CLI;
import com.excilys.java.cdb.config.SpringConfiguration;

/**
 * Classe avec la méthode main pour lancer le programme
 * @author sonia
 *
 */
public class Main {
	
	public static void main(String []args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		CLI user= context.getBean(CLI.class);;
		user.start();		
	}

}
