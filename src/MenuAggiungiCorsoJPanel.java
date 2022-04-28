import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
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

	private JTextField txtNomeCorso = new JTextField();
	private CustomJComboBox cbGiorno = new CustomJComboBox();
	private CustomJComboBox cbMese = new CustomJComboBox();
	private CustomJComboBox cbAnno = new CustomJComboBox();
	private JTextField txtNOre = new JTextField();
	private JTextField txtScadenza = new JTextField();
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

		this.setLayout(new GridLayout(5,2));
		JPanel pnlData = new JPanel(new GridLayout(1,3));

		JLabel lblNomeCorso = new JLabel("Nome corso: ");
		JLabel lblData = new JLabel("Data: ");
		JLabel lblNOre = new JLabel("Numero di ore: ");
		JLabel lblScadenza = new JLabel("Durata (in anni): ");

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


	public MenuAggiungiCorsoJPanel(GestoreCorsiJava1 gcj1, int tipologia, Lavoratore lavoratore, CorsoDiFormazione corso) {

		this.tipologia = tipologia;
		gestoreCorsi = gcj1;
		this.lavoratore = lavoratore;
		pannelloModifica = true;
		this.corso = corso;

		this.setLayout(new GridLayout(5,2));
		JPanel pnlData = new JPanel(new GridLayout(1,3));

		JLabel lblNomeCorso = new JLabel("Nome corso: ");
		JLabel lblData = new JLabel("Data: ");
		JLabel lblNOre = new JLabel("Numero di ore: ");
		JLabel lblScadenza = new JLabel("Durata (in anni): ");

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

		for(int i=1;i<=31;i++) {
			cbGiorno.addItem(""+i);
		}

		for(int i=0;i<MESI.length;i++) {
			cbMese.addItem(MESI[i]);
		}

		int annoAttuale = Calendar.getInstance().get(Calendar.YEAR);

		for(int i=annoAttuale-60;i<=annoAttuale;i++) {
			cbAnno.addItem(""+i);
			if(i==Integer.parseInt(corso.getData().split("/")[2]))
				cbAnno.setSelectedIndex(cbAnno.getItemCount()-1);
		}

		txtNomeCorso.setText(corso.getNomeCorso());
		txtNOre.setText(""+corso.getnOre());
		txtScadenza.setText(""+corso.getScadenzaAnni());

		cbGiorno.setSelectedIndex(Integer.parseInt(corso.getData().split("/")[0])-1);
		cbMese.setSelectedIndex(Integer.parseInt(corso.getData().split("/")[1])-1);

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

	/**
	 * Allora, qui controlla tipo tutto per vedere se è apposto. E poi mette dentro l'array di corsi di lavoratore
	 * 
	 * @author Marco
	 *
	 */
	public class GestioneSalva implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

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


			if(controllo==false) {
				String data = ""+giorno+"/"+mese+"/"+anno;

				if(pannelloModifica) {
					corso.setNomeCorso(nomeCorso);
					corso.setData(data);
					corso.setnOre(numeroOre);
					corso.setScadenzaAnni(scadenzaInt);
					corso.setTipologia(tipologia);

					gestoreCorsi.getPnlDefault().removeAll();
					gestoreCorsi.getPnlDefault().add(new MenuInfoCorsoSpecificoJPanel(gestoreCorsi,corso,lavoratore));
					gestoreCorsi.getPnlDefault().revalidate();
					gestoreCorsi.getPnlDefault().repaint();
					

				}
				else {
					lavoratore.aggiungiCorso(new CorsoDiFormazione(nomeCorso, data, numeroOre, scadenzaInt, tipologia));
				}
				
				gestoreCorsi.salva();

				reset();
			}


		}

	}

	public void reset() {
		txtNomeCorso.setText("");
		txtNOre.setText("");
		txtScadenza.setText("");

		Calendar c = Calendar.getInstance();

		cbGiorno.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
		cbMese.setSelectedIndex(c.get(Calendar.MONTH));
		cbAnno.setSelectedIndex(cbAnno.getItemCount()-1);
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
