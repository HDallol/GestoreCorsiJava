import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Menù per aggiungere un lavoratore: LAVORI IN CORSO
 * 
 * 
 * 
 * @author Marco
 *
 */
public class MenuAggiungiLavoratore extends JPanel{

	private GestoreCorsiJava1 gestoreCorsi;
	
	private JTextField txtCognome;
	private JTextField txtNome;
	private JTextField txtCodiceFiscale;
	private JTextField txtIndirizzo;
	private CustomJComboBox cbQualifica;
	
	private ArrayList<CorsoDiFormazione> corsiDiFormazione;
	
	public MenuAggiungiLavoratore(GestoreCorsiJava1 gcj1) {
		
		gestoreCorsi = gcj1;
		this.setLayout(new GridLayout(6,2,10,10));
		
		JLabel lblCognome = new JLabel("Cognome:");
		JLabel lblNome = new JLabel("Nome:");
		JLabel lblCodiceFis = new JLabel("Codice Fiscale:");
		JLabel lblIndirizzo = new JLabel("Indirizzo:");
		JLabel lblQualifica = new JLabel("Qualifica:");
		
		CustomJButton btnIndietro = new CustomJButton("Indietro",2);
		CustomJButton btnSalva = new CustomJButton("Salva",2);
		
		txtCognome = new JTextField();
		txtNome = new JTextField();
		txtCodiceFiscale = new JTextField();
		txtIndirizzo = new JTextField();
		cbQualifica = new CustomJComboBox();
		
		btnIndietro.addActionListener(new GestioneIndietro());
		
		this.add(lblCognome);
		this.add(txtCognome);
		this.add(lblNome);
		this.add(txtNome);
		this.add(lblCodiceFis);
		this.add(txtCodiceFiscale);
		this.add(lblIndirizzo);
		this.add(txtIndirizzo);
		this.add(lblQualifica);
		this.add(cbQualifica);
		this.add(btnIndietro);
		this.add(btnSalva);
		
	}
	
	
	
	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(gestoreCorsi.getPnlMenuIniziale());
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}
		
	}
	
}
