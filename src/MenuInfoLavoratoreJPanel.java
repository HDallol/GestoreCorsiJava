import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Il men� che vien fuori quando premi un lavoratore dal men� principale
 * Qui ci sono tutte le info del lavoratore, la possibilit� di aggiungere corsi ecc
 * 
 * --------------------------------------> DA FARE <-------------------------------------
 * 
 * 
 * @author Marco
 *
 */
public class MenuInfoLavoratoreJPanel extends JPanel{

	/**
	 * Indice del lavoratore dell'array dei lavoratori
	 */
	int index;
	GestoreCorsiJava1 gestoreCorsi;
	
	public MenuInfoLavoratoreJPanel(GestoreCorsiJava1 gcj1, int index) {
		
		this.index = index;
		gestoreCorsi = gcj1;
		
		this.setLayout(new GridLayout(2,1));
		
		JLabel lbl = new JLabel("Qesto � il lavoratore n "+index+" e si chiama "+gestoreCorsi.getArrayLavoratori().get(index).getNome());
		CustomJButton btnIndietro = new CustomJButton("Indietro");
		
		btnIndietro.addActionListener(new GestioneIndietro());
		this.add(lbl);
		this.add(btnIndietro);
		
	}
	
	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlMenuIniziale().reset();
			gestoreCorsi.getPnlMenuIniziale().getScrollPanePnl().aggiornaPanel();
			gestoreCorsi.getPnlDefault().add(gestoreCorsi.getPnlMenuIniziale());
			gestoreCorsi.revalidate();
			gestoreCorsi.repaint();
		}
		
	}
	
}
