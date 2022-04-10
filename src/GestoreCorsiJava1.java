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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GestoreCorsiJava1 extends JFrame {
	
	private Dimension dimensioniSchermo = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension dimensioniMinime = new Dimension(200,200);
	private Dimension dimensioniFinestra = new Dimension(dimensioniSchermo.width/2, dimensioniSchermo.height/2);
	
	/**
	 * Panel di default su cui aggiungere tutti gli altri panel
	 */
	private JPanel pnlDefault;
	
	private MenuInizialeJPanel pnlMenuIniziale;
	private MenuAggiungiLavoratoreJPanel pnlMenuAggiungiLavoratore;
	
	/**
	 * E qui verranno salvati tutti i lavoratori ecc
	 */
	private ArrayList<Lavoratore> arrayLavoratori;
	
	public GestoreCorsiJava1() {
		super("Gestore Corsi di Formazione - Pre-Alpha 0.0.1");
		
		arrayLavoratori = new ArrayList<Lavoratore>();
		
		pnlDefault = new JPanel(new GridLayout(1,1));
		pnlMenuIniziale = new MenuInizialeJPanel(this);
		pnlMenuAggiungiLavoratore = new MenuAggiungiLavoratoreJPanel(this);
		
		pnlDefault.add(pnlMenuIniziale);
		this.getContentPane().add(pnlDefault);
		
		
		this.setSize(dimensioniFinestra);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		this.setLocation((dimensioniSchermo.width-dimensioniFinestra.width)/2, (dimensioniSchermo.height-dimensioniFinestra.height)/2);
		this.setMinimumSize(dimensioniMinime);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.requestFocus();
	}
	
	
	public ArrayList<Lavoratore> getArrayLavoratori() {
		return arrayLavoratori;
	}

	public JPanel getPnlDefault() {
		return pnlDefault;
	}
	
	public MenuInizialeJPanel getPnlMenuIniziale() {
		return pnlMenuIniziale;
	}
	
	public MenuAggiungiLavoratoreJPanel getMenuAggiungiLavoratore() {
		return pnlMenuAggiungiLavoratore;
	}
	
}
