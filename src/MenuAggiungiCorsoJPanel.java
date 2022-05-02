import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

	private JTextField txtNomeCorso = new JTextField();
	private CustomJComboBox cbGiorno = new CustomJComboBox();
	private CustomJComboBox cbMese = new CustomJComboBox();
	private CustomJComboBox cbAnno = new CustomJComboBox();
	private JTextField txtNOre = new JTextField();
	private JTextField txtScadenza = new JTextField();
	private JRadioButton rbtnAffermativo = new JRadioButton("Sì");
	private JRadioButton rbtnNegativo = new JRadioButton("No");
	private JPanel pnlCompletato = new JPanel(new GridLayout(1,2,10,10));

	/**
	 * Questo decide se il pannello è un pannello di modifica di un corso
	 * già esistente o se serve a creare un nuovo corso
	 */
	private boolean pannelloModifica;
	private CorsoDiFormazione corso;

	/**
	 * @param gcj1 il gestore corsi
	 * @param tipologia tipologia tra GENERALE, SPECIFICA, PREPOSTO o QUINQUIENNALE
	 * @param lavoratore il lavoratore su cui stiamo lavorando
	 */
	public MenuAggiungiCorsoJPanel(GestoreCorsiJava1 gcj1, int tipologia, Lavoratore lavoratore) {

		this.tipologia = tipologia;
		gestoreCorsi = gcj1;
		this.lavoratore = lavoratore;
		pannelloModifica = false;
		corso = null;



		this.setLayout(new GridLayout(6,2));
		JPanel pnlData = new JPanel(new GridLayout(1,3));

		JLabel lblNomeCorso = new JLabel("Nome corso: ");
		JLabel lblData = new JLabel("Data: ");
		JLabel lblNOre = new JLabel("Numero di ore: ");
		JLabel lblScadenza = new JLabel("Durata (in anni): ");
		JLabel lblCompletato = new JLabel("Completato: ");

		CustomJButton btnIndietro = new CustomJButton("Indietro");
		CustomJButton btnSalva = new CustomJButton("Salva");

		btnIndietro.addActionListener(new GestioneIndietro());
		btnSalva.addActionListener(new GestioneSalva());
		cbGiorno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cbGiorno.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			}

		});

		txtNomeCorso.addKeyListener(new GestioneTesto());
		txtNOre.addKeyListener(new GestioneTesto());
		txtScadenza.addKeyListener(new GestioneTesto());
		rbtnAffermativo.addActionListener(new GestioneRadioButton());
		rbtnNegativo.addActionListener(new GestioneRadioButton());

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

		Calendar c = Calendar.getInstance();

		cbGiorno.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
		cbMese.setSelectedIndex(c.get(Calendar.MONTH));
		cbAnno.setSelectedIndex(cbAnno.getItemCount()-1);

		ButtonGroup rbtnGroup = new ButtonGroup();

		rbtnAffermativo.setFocusable(false);
		rbtnNegativo.setFocusable(false);

		rbtnGroup.add(rbtnAffermativo);
		rbtnGroup.add(rbtnNegativo);

		pnlData.add(cbGiorno);
		pnlData.add(cbMese);
		pnlData.add(cbAnno);

		pnlCompletato.add(rbtnAffermativo);
		pnlCompletato.add(rbtnNegativo);

		this.add(lblNomeCorso);
		this.add(txtNomeCorso);
		this.add(lblData);
		this.add(pnlData);
		this.add(lblNOre);
		this.add(txtNOre);
		this.add(lblScadenza);
		this.add(txtScadenza);
		this.add(lblCompletato);
		this.add(pnlCompletato);
		this.add(btnIndietro);
		this.add(btnSalva);

	}


	public MenuAggiungiCorsoJPanel(GestoreCorsiJava1 gcj1, int tipologia, Lavoratore lavoratore, CorsoDiFormazione corso) {

		this.tipologia = tipologia;
		gestoreCorsi = gcj1;
		this.lavoratore = lavoratore;
		pannelloModifica = true;
		this.corso = corso;

		this.setLayout(new GridLayout(4,2));
		JPanel pnlData = new JPanel(new GridLayout(1,3));
		JPanel pnlCompletato = new JPanel(new GridLayout(1,2,10,10));

		JLabel lblNomeCorso = new JLabel("Nome corso: ");
		JLabel lblCompletato = new JLabel("Completato: ");
		JLabel lblScadenza = new JLabel("Durata (in anni): ");

		CustomJButton btnIndietro = new CustomJButton("Indietro");
		CustomJButton btnSalva = new CustomJButton("Salva");

		btnIndietro.addActionListener(new GestioneIndietro());
		btnSalva.addActionListener(new GestioneSalva());
		
		ButtonGroup rbtnGroup = new ButtonGroup();

		rbtnAffermativo.setFocusable(false);
		rbtnNegativo.setFocusable(false);

		rbtnGroup.add(rbtnAffermativo);
		rbtnGroup.add(rbtnNegativo);

		if(corso.getCompletato())
			rbtnAffermativo.setSelected(true);
		else
			rbtnNegativo.setSelected(true);
		
		txtNomeCorso.addKeyListener(new GestioneTesto());
		txtNOre.addKeyListener(new GestioneTesto());
		txtScadenza.addKeyListener(new GestioneTesto());

		txtNomeCorso.setText(corso.getNomeCorso());
		txtNOre.setText(""+corso.getnOre());
		txtScadenza.setText(""+corso.getScadenzaAnni());

		pnlData.add(cbGiorno);
		pnlData.add(cbMese);
		pnlData.add(cbAnno);
		
		pnlCompletato.add(rbtnAffermativo);
		pnlCompletato.add(rbtnNegativo);

		this.add(lblNomeCorso);
		this.add(txtNomeCorso);
		this.add(lblScadenza);
		this.add(txtScadenza);
		this.add(lblCompletato);
		this.add(pnlCompletato);
		this.add(btnIndietro);
		this.add(btnSalva);

	}

	/**
	 * Allora, qui controlla tipo tutto per vedere se è apposto. E poi mette dentro l'array di corsi di lavoratore
	 * 
	 * @author Marco
	 *
	 */
	public class GestioneSalva implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if(!pannelloModifica) {
				boolean controllo=false;

				String nomeCorso = txtNomeCorso.getText().trim();
				int giorno = cbGiorno.getSelectedIndex()+1;
				int mese = cbMese.getSelectedIndex()+1;
				int anno = Integer.parseInt( (String) cbAnno.getSelectedItem() );
				String nOre = txtNOre.getText().trim();
				String scadenza = txtScadenza.getText().trim();

				if(nomeCorso.length()==0) {
					controllo=true;
					txtNomeCorso.setBorder(BorderFactory.createLineBorder(Color.red, 1));
				}

				if(mese==2) {
					if(anno%400==0 || (anno%4==0 && anno%100!=0)) {
						if(!(giorno<=29)) {
							controllo=true;
							cbGiorno.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
						}
					}
					else if(!(giorno<=28)) {
						controllo=true;
						cbGiorno.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					}
				}
				else if(mese==4 || mese==6 || mese==9 || mese==11) {
					if(!(giorno<=30)) {
						controllo=true;
						cbGiorno.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					}
				}
				int numeroOre = 0;
				try {
					numeroOre = Integer.parseInt(nOre);

					if(numeroOre<1) {
						controllo=true;
						txtNOre.setBorder(BorderFactory.createLineBorder(Color.red, 1));
					}

				}
				catch(NumberFormatException e1) {
					controllo=true;
					txtNOre.setBorder(BorderFactory.createLineBorder(Color.red, 1));
				}

				int scadenzaInt=0;

				try {
					scadenzaInt = Integer.parseInt(scadenza);

					if(scadenzaInt<1) {
						controllo=true;
						txtScadenza.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
					}
				}
				catch(NumberFormatException e1) {
					controllo=true;
					txtScadenza.setBorder(BorderFactory.createLineBorder(Color.red, 1));
				}


				boolean completato=false;
				if(rbtnAffermativo.isSelected()==false && rbtnNegativo.isSelected()==false) {
					controllo=true;
					pnlCompletato.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				}
				else {
					if(rbtnAffermativo.isSelected()==true) 
						completato = true;
					else 
						completato = false;
				}

				if(controllo==false) {
					String data = ""+giorno+"/"+mese+"/"+anno;

					lavoratore.aggiungiCorso(new CorsoDiFormazione(nomeCorso, scadenzaInt, tipologia, completato));
					lavoratore.getCorsiDiFormazioni().get(lavoratore.getCorsiDiFormazioni().size()-1).aggiungiData(data, numeroOre);

				}
			}

			else {
				boolean controllo = false;

				String nomeCorso = txtNomeCorso.getText().trim();
				if(nomeCorso.length()==0) {
					controllo=true;
					txtNomeCorso.setBorder(BorderFactory.createLineBorder(Color.red, 1));
				}
				String scadenza = txtScadenza.getText().trim();
				int scadenzaInt=0;

				try {
					scadenzaInt = Integer.parseInt(scadenza);

					if(scadenzaInt<1) {
						controllo=true;
						txtScadenza.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
					}
				}
				catch(NumberFormatException e1) {
					controllo=true;
					txtScadenza.setBorder(BorderFactory.createLineBorder(Color.red, 1));
				}

				boolean completato=false;
				if(rbtnAffermativo.isSelected()==false && rbtnNegativo.isSelected()==false) {
					controllo=true;
					pnlCompletato.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				}
				else {
					if(rbtnAffermativo.isSelected()==true) 
						completato = true;
					else 
						completato = false;
				}
				
				if(controllo==false) {
					corso.setNomeCorso(nomeCorso);
					corso.setScadenzaAnni(scadenzaInt);
					corso.setCompletato(completato);
					gestoreCorsi.getPnlDefault().removeAll();
					gestoreCorsi.getPnlDefault().add(new MenuInfoCorsoSpecificoJPanel(gestoreCorsi,corso,lavoratore));
					gestoreCorsi.getPnlDefault().revalidate();
					gestoreCorsi.getPnlDefault().repaint();
				}

			}


			gestoreCorsi.salva();
			reset();

		}

	}

	public void reset() {
		txtNomeCorso.setText("");
		txtNOre.setText("");
		txtScadenza.setText("");

		Calendar c = Calendar.getInstance();

		if(!pannelloModifica) {
			cbGiorno.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
			cbMese.setSelectedIndex(c.get(Calendar.MONTH));
			cbAnno.setSelectedIndex(cbAnno.getItemCount()-1);
		}
	}

	public class GestioneRadioButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			pnlCompletato.setBorder(null);
		}

	}

	public class GestioneTesto implements KeyListener {

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

	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {


			gestoreCorsi.getPnlDefault().removeAll();

			if(pannelloModifica)
				gestoreCorsi.getPnlDefault().add(new MenuInfoCorsoSpecificoJPanel(gestoreCorsi,corso,lavoratore));
			else
				gestoreCorsi.getPnlDefault().add(new MenuInfoCorsiJPanel(gestoreCorsi,lavoratore,tipologia));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();

		}

	}


}
