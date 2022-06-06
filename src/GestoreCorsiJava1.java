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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class GestoreCorsiJava1 extends JFrame implements WindowListener{
	
	private Dimension dimensioniSchermo = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension dimensioniMinime = new Dimension(200,200);
	private Dimension dimensioniFinestra = new Dimension(dimensioniSchermo.width/2, dimensioniSchermo.height/2);
	
	public static final Font fontDefault = new Font("Calibri", Font.PLAIN, 15);
	
	private File fileSalvataggio;
	
	/**
	 * Panel di default su cui aggiungere tutti gli altri panel
	 */
	private JPanel pnlDefault;
	
	private MenuInizialeJPanel pnlMenuIniziale;
	private MenuAggiungiLavoratoreJPanel pnlMenuAggiungiLavoratore;
	
	private ImageIcon logo = new ImageIcon("logo.png");
	
	/**
	 * E qui verranno salvati tutti i lavoratori ecc
	 */
	private ArrayList<Lavoratore> arrayLavoratori;
	
	public GestoreCorsiJava1(JWindow splashScreen) {
		super("Gestore Corsi di Formazione - Alpha 0.2");
		
//		SplashScreen splash = SplashScreen.getSplashScreen();
//		System.out.println(splash);
//		Graphics2D g2 = splash.createGraphics();
//		
//		g2.drawImage(logo.getImage(), (dimensioniSchermo.width-logo.getIconWidth())/2, (dimensioniSchermo.height-logo.getIconHeight())/2, logo.getIconWidth(), logo.getIconHeight(), logo.getImageObserver());
//		
	
		
		
		this.setIconImage(logo.getImage());
		
		fileSalvataggio = new File("gestoreCorsiJava.gcj");
		
		arrayLavoratori = new ArrayList<Lavoratore>();
		
		leggi();
		
		
		pnlDefault = new JPanel(new GridLayout(1,1));
		pnlMenuIniziale = new MenuInizialeJPanel(this);
		pnlMenuAggiungiLavoratore = new MenuAggiungiLavoratoreJPanel(this);
		
		pnlDefault.add(pnlMenuIniziale);
		this.getContentPane().add(pnlDefault);
		
		this.addWindowListener(this);
		this.setSize(dimensioniFinestra);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		this.setLocation((dimensioniSchermo.width-dimensioniFinestra.width)/2, (dimensioniSchermo.height-dimensioniFinestra.height)/2);
		this.setMinimumSize(dimensioniMinime);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		splashScreen.setVisible(false);
		splashScreen.dispose();
		this.setVisible(true);
		
		this.toFront();
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


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
		int x = JOptionPane.showConfirmDialog(null, "Vuoi davvero uscire?", "Uscita in corso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		
		if(x==0)
			System.exit(0);
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
