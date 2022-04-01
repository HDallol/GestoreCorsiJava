/*
 * Autori: Marco Dall'O' Polveni, Stefano Pacchin
 * 
 * Versione: 1.0
 *  
 *  Un programma che permette la gestione dei corsi di formazione
 *  di tutti i dipendenti di un'azienda
 *  
 *  
 */

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GestoreCorsiJava1 extends JFrame{
	
	Dimension dimensioniSchermo = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dimensioniMinime = new Dimension(200,200);
	Dimension dimensioniFinestra = new Dimension(dimensioniSchermo.width/2, dimensioniSchermo.height/2);
	
	public GestoreCorsiJava1() {
		super("Gestore Corsi di Formazione - Pre-Alpha 0.001");
		
		
		this.setSize(dimensioniFinestra);
		this.setLocation((dimensioniSchermo.width-dimensioniFinestra.width)/2, (dimensioniSchermo.height-dimensioniFinestra.height)/2);
		this.setMinimumSize(dimensioniMinime);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
	

}
