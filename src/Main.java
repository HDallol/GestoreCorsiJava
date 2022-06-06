import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 * Autori: Marco Dall'O' Polveni, Stefano Pacchin
 * 
 * Versione: 1.0
 *  
 *  Un programma che permette la gestione dei corsi di formazione
 *  di tutti i dipendenti di un'azienda
 *  
 *  
 */

public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon image = new ImageIcon("SplashScreen.png");
		JWindow splash = new JWindow();

		if(image!=null) {

			splash.setBounds( (screen.width-image.getIconWidth())/2	, (screen.height-image.getIconHeight())/2, image.getIconWidth(), image.getIconHeight());
			splash.getContentPane().add(new JLabel(image));
			splash.setAlwaysOnTop(true);
			splash.setVisible(true);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		new GestoreCorsiJava1(splash);
	}

}
