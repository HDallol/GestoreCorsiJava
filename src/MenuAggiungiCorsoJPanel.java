import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Menu per aggiungere i corsi. Niente di più, niente di meno.
 * 
 * @author Marco
 *
 */
public class MenuAggiungiCorsoJPanel extends JPanel{

	public final static int GENERALE=0;
	public final static int SPECIFICA=1;
	public final static int PREPOSTO=2;
	public final static int QUINQUIENNALE=3;
	public final static String[] MESI = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	
	
	private int tipologia;
	private int index;
	private Lavoratore lavoratore;
	private GestoreCorsiJava1 gestoreCorsi;
	
	/**
	 * @param gcj1 il gestore corsi
	 * @param tipologia tipologia tra GENERALE, SPECIFICA, PREPOSTO o QUINQUIENNALE
	 * @param lavoratore il lavoratore su cui stiamo lavorando
	 */
	public MenuAggiungiCorsoJPanel(GestoreCorsiJava1 gcj1, int tipologia, Lavoratore lavoratore) {
		
		this.tipologia = tipologia;
		gestoreCorsi = gcj1;
		this.lavoratore = lavoratore;
		
		this.setLayout(new GridLayout(5,2));
		JPanel pnlData = new JPanel(new GridLayout(1,3));
		
		JLabel lblNomeCorso = new JLabel("Nome corso: ");
		JLabel lblData = new JLabel("Data: ");
		JLabel lblNOre = new JLabel("Numero di ore: ");
		JLabel lblScadenza = new JLabel("Scadenza: ");
		
		JTextField txtNomeCorso = new JTextField();
		CustomJComboBox cbGiorno = new CustomJComboBox();
		CustomJComboBox cbMese = new CustomJComboBox();
		CustomJComboBox cbAnno = new CustomJComboBox();
		JTextField txtNOre = new JTextField();
		JTextField txtScadenza = new JTextField();
		
		CustomJButton btnIndietro = new CustomJButton("Indietro");
		CustomJButton btnSalva = new CustomJButton("Salva");
		
		btnIndietro.addActionListener(new GestioneIndietro());
		
		for(int i=1;i<=31;i++) {
			cbGiorno.addItem(""+i);
		}
		
		for(int i=0;i<MESI.length;i++) {
			cbMese.addItem(MESI[i]);
		}
		
		int annoAttuale = Calendar.getInstance().get(Calendar.YEAR);
		
		for(int i=annoAttuale-60;i<=annoAttuale;i++) {
			cbAnno.addItem(""+i);
		}
		
		pnlData.add(cbGiorno);
		pnlData.add(cbMese);
		pnlData.add(cbAnno);
		
		this.add(lblNomeCorso);
		this.add(txtNomeCorso);
		this.add(lblData);
		this.add(pnlData);
		this.add(lblNOre);
		this.add(txtNOre);
		this.add(lblScadenza);
		this.add(txtScadenza);
		this.add(btnIndietro);
		this.add(btnSalva);

	}
	
	public class GestioneSalva implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//TODO
			
		}
		
	}
	
	
	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuInfoCorsiJPanel(gestoreCorsi,lavoratore,tipologia));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
			
		}
		
	}
	
	
}
