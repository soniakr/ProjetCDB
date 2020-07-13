package com.excilys.formation.cbd.main;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.cbd.config.SpringConfig;
import com.excilys.formation.cbd.ui.CLI;

/**
 * Classe avec la méthode main pour lancer le programme
 * @author sonia
 *
 */
public class Main {
	
	public static void main(String []args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		CLI user= context.getBean(CLI.class);;
		user.start();
		
	}

}
