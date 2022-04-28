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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GestoreCorsiJava1 extends JFrame {
	
	private Dimension dimensioniSchermo = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension dimensioniMinime = new Dimension(200,200);
	private Dimension dimensioniFinestra = new Dimension(dimensioniSchermo.width/2, dimensioniSchermo.height/2);
	
	private File fileSalvataggio;
	
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
		super("Gestore Corsi di Formazione - Alpha 0.2");
		
		fileSalvataggio = new File("gestoreCorsiJava.gcj");
		
		arrayLavoratori = new ArrayList<Lavoratore>();
		
		leggi();
		
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
	
	/**
	 * Salvataggio su file
	 */
	public void salva() {
		
		try {
			FileOutputStream fos = new FileOutputStream(fileSalvataggio,false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(arrayLavoratori);
			
			oos.flush();
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	/**
	 * Lettura da file
	 */
	public void leggi() {
		
		try {
			FileInputStream fis = new FileInputStream(fileSalvataggio);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			arrayLavoratori = (ArrayList<Lavoratore>) ois.readObject();
			
			ois.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch(ClassCastException e) {
			
		}
		
		System.out.println("Dimensione attuale array: "+arrayLavoratori.size());
		
	}
	
}
