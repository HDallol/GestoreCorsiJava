import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Quello che esce quando clicchi su un corso di un lavoratore.
 * Mostra le info del corso.
 * 
 * @author Marco
 *
 */
public class MenuInfoCorsoSpecificoJPanel extends JPanel {

	private GestoreCorsiJava1 gestoreCorsi;
	private CorsoDiFormazione corso;
	private Lavoratore lavoratore;
	
	public MenuInfoCorsoSpecificoJPanel(GestoreCorsiJava1 gcj1, CorsoDiFormazione corso, Lavoratore lav) {
		
		gestoreCorsi = gcj1;
		this.corso = corso;
		this.lavoratore = lav;
		
		this.setLayout(new BorderLayout());
		
		JPanel pnlInfo = new JPanel(new GridLayout(6,1));
		JPanel pnlPulsanti = new JPanel(new GridLayout(1,2,10,10));
		
		JLabel lblNomeCorso = new JLabel("Corso: "+corso.getNomeCorso());
		JLabel lblTipologia = new JLabel("");
		JLabel lblData = new JLabel("Data: "+corso.getData());
		JLabel lblNOre = new JLabel("Numero di ore: "+corso.getnOre());
		JLabel lblDurata = new JLabel("Durata (in anni): "+corso.getScadenzaAnni());
		JLabel lblStato = new JLabel("");
		
		CustomJButton btnIndietro = new CustomJButton("Indietro");
		CustomJButton btnModifica = new CustomJButton("Modifica");
		
		btnIndietro.addActionListener(new GestioneIndietro());
		
		switch(corso.getTipologia()) {
		case CorsoDiFormazione.GENERALE:
			lblTipologia.setText("Tipologia: Generale");
			break;
		case CorsoDiFormazione.PREPOSTO:
			lblTipologia.setText("Tipologia: Preposto");
			break;
		case CorsoDiFormazione.QUINQUIENNALE:
			lblTipologia.setText("Tipologia: Quinquiennale");
			break;
		case CorsoDiFormazione.SPECIFICA:
			lblTipologia.setText("Tipologia: Specifica");
			break;
		}
	
		switch(corso.getStato()) {
		case CorsoDiFormazione.VALIDO:
			lblStato.setText("Stato: VALIDO");
			break;
		case CorsoDiFormazione.IN_SCADENZA:
			lblStato.setText("Stato: IN SCADENZA");
			break;
		case CorsoDiFormazione.SCADUTO:
			lblStato.setText("Stato: SCADUTO");
			break;
		}
		
		
		pnlInfo.add(lblNomeCorso);
		pnlInfo.add(lblTipologia);
		pnlInfo.add(lblData);
		pnlInfo.add(lblNOre);
		pnlInfo.add(lblDurata);
		pnlInfo.add(lblStato);
		
		pnlPulsanti.add(btnIndietro);
		pnlPulsanti.add(btnModifica);
		
		this.add(pnlInfo, BorderLayout.CENTER);
		this.add(pnlPulsanti, BorderLayout.SOUTH);
	}
	
	
	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuInfoCorsiJPanel(gestoreCorsi,lavoratore, corso.getTipologia()));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}
		
	}
	
}
