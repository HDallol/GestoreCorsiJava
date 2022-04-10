import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Menù per aggiungere un lavoratore: LAVORI IN CORSO
 * 
 * 
 * Ho messo un bordo ROSSO quando sbagli i parametri, solo perché altrimenti non capivo un tubo se sbagliava o no
 * ma vabbe si capisce meglio quando è in esecuzione
 * 
 * @author Marco
 *
 */
public class MenuAggiungiLavoratoreJPanel extends JPanel{

	private GestoreCorsiJava1 gestoreCorsi;

	private JTextField txtCognome;
	private JTextField txtNome;
	private JTextField txtCodiceFiscale;
	private JTextField txtIndirizzo;
	private CustomJComboBox cbQualifica;

	private String arrayQualifiche[] = {"Maggiordomo","Il ritardato","L'aiutante","Altro"};
	private ArrayList<CorsoDiFormazione> corsiDiFormazione;

	public MenuAggiungiLavoratoreJPanel(GestoreCorsiJava1 gcj1) {

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
		cbQualifica = new CustomJComboBox(arrayQualifiche);

		txtCodiceFiscale.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		txtCognome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		txtIndirizzo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		txtNome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		txtCodiceFiscale.addKeyListener(new GestioneTextField());
		txtNome.addKeyListener(new GestioneTextField());
		txtCognome.addKeyListener(new GestioneTextField());
		txtIndirizzo.addKeyListener(new GestioneTextField());
		
		btnIndietro.addActionListener(new GestioneIndietro());
		btnSalva.addActionListener(new GestioneSalva());
		
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

	/**
	 * 
	 * @return controllo false se va tutto bene, true se c'è qualcosa di sballato
	 */
	private boolean controlloParametri() {
		boolean controllo=false;

		if(txtNome.getText().length()==0 || txtNome.getText().length()>20) {
			txtNome.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			controllo = true;
		}

		if(txtCognome.getText().length()==0 || txtCognome.getText().length()>20) {
			txtCognome.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			controllo = true;
		}

		if(txtCodiceFiscale.getText().length()==0) {									//ATTENZIONE: Modificare la lunghezza effettiva del codice fiscale
			txtCodiceFiscale.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			controllo = true;
		}

		if(txtIndirizzo.getText().length()==0 || txtIndirizzo.getText().length()>40) {
			txtIndirizzo.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			controllo = true;
		}

		return controllo;
	}

	private boolean controlloArrayLavoratori() {
		boolean controllo=false;
		
		ArrayList<Lavoratore> arr = gestoreCorsi.getArrayLavoratori();
		
		//Se c'è un altro che ha lo stesso codice fiscale, allora blocco l'inserimento
		// CODICE FISCALE = UNIVOCO
		for(int i=0;i<arr.size();i++) {
			if(arr.get(i).getCodiceFiscale().equalsIgnoreCase(txtCodiceFiscale.getText())) {	
				controllo=true;
				txtCodiceFiscale.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				break;
			}
		}
		 
		return controllo;
	}
	

	public void reset() {
		txtCognome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		txtCodiceFiscale.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		txtIndirizzo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		txtNome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		txtCognome.setText("");
		txtCodiceFiscale.setText("");
		txtIndirizzo.setText("");
		txtNome.setText("");
		cbQualifica.setSelectedIndex(0);
	}
	
	public class GestioneSalva implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			boolean controllo=controlloParametri();

			if(controllo==false) {
				controllo = controlloArrayLavoratori();
				
				if(controllo==false) {
					String nome = txtNome.getText();
					String cognome = txtCognome.getText();
					String codiceFiscale = txtCodiceFiscale.getText();
					String indirizzo = txtIndirizzo.getText();
					String qualifica = (String) cbQualifica.getSelectedItem();
					System.out.println("Nel dubbio, la qualifica: "+qualifica);
					
					gestoreCorsi.getArrayLavoratori().add(new Lavoratore(cognome,nome,codiceFiscale,indirizzo,qualifica));
					//gestoreCorsi.getPnlMenuIniziale().getScrollPanePnl().aggiornaPanel();
					
					txtNome.setText("");
					txtCognome.setText("");
					txtIndirizzo.setText("");
					txtCodiceFiscale.setText("");
					cbQualifica.setSelectedIndex(0);
				}
				
			}

		}

	}

	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlMenuIniziale().reset();
			gestoreCorsi.getPnlDefault().add(gestoreCorsi.getPnlMenuIniziale());
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

	}

	
	/**
	 * Questo resetta il bordo quando scrivi qualcosa, per riportarlo a NERO 
	 * @author Marco
	 *
	 */
	public class GestioneTextField implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			JTextField t = (JTextField) e.getSource();
			t.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}


		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
