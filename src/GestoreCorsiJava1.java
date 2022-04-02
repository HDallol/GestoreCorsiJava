/**
 * Autori: Marco Dall'O' Polveni, Stefano Pacchin
 * 
 * Versione: Pre-Alpha 0.0.1
 *  
 *  Un programma che permette la gestione dei corsi di formazione
 *  di tutti i dipendenti di un'azienda
 *  
 *  
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GestoreCorsiJava1 extends JFrame{
	
	Dimension dimensioniSchermo = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dimensioniMinime = new Dimension(200,200);
	Dimension dimensioniFinestra = new Dimension(dimensioniSchermo.width/2, dimensioniSchermo.height/2);
	
	/**
	 * Panel di default su cui aggiungere tutti gli altri panel
	 */
	JPanel pnlDefault;
	
	MenuInizialeJPanel pnlMenuIniziale;
	
	public GestoreCorsiJava1() {
		super("Gestore Corsi di Formazione - Pre-Alpha 0.0.1");
		
		pnlDefault = new JPanel(new GridLayout(1,1));
		pnlMenuIniziale = new MenuInizialeJPanel();
		
		pnlDefault.add(pnlMenuIniziale);
		this.getContentPane().add(pnlDefault);
		
		this.setSize(dimensioniFinestra);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);		
		this.setLocation((dimensioniSchermo.width-dimensioniFinestra.width)/2, (dimensioniSchermo.height-dimensioniFinestra.height)/2);
		this.setMinimumSize(dimensioniMinime);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
	

}
