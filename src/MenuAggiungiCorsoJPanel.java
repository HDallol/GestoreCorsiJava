import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
	private ButtonGroup rbtnGroup;
	private Color temaBackground = new Color(210,210,210);

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

		this.setLayout(new BorderLayout());
		JPanel pnlMain = new JPanel(new GridLayout(5,2,10,10));
		JPanel pnlData = new JPanel(new GridLayout(1,3));
		JPanel pnlPulsanti = new JPanel(new GridLayout(1,3,10,10));
		
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

		rbtnGroup = new ButtonGroup();

		rbtnAffermativo.setFocusable(false);
		rbtnNegativo.setFocusable(false);
		
		rbtnGroup.add(rbtnAffermativo);
		rbtnGroup.add(rbtnNegativo);

		cbAnno.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		cbGiorno.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		cbMese.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		
		cbAnno.setBackground(Color.WHITE);
		cbMese.setBackground(Color.WHITE);
		cbGiorno.setBackground(Color.WHITE);
		
		txtNomeCorso.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,2.5));
		txtNOre.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,2.5));
		txtScadenza.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,2.5));
		
		lblCompletato.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		lblData.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		lblNomeCorso.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		lblNOre.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		lblScadenza.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		
		rbtnAffermativo.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		rbtnNegativo.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		
		btnSalva.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault.deriveFont(Font.BOLD),2));
		btnIndietro.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault.deriveFont(Font.BOLD),2));
		
		pnlData.add(cbGiorno);
		pnlData.add(cbMese);
		pnlData.add(cbAnno);

		pnlCompletato.add(rbtnAffermativo);
		pnlCompletato.add(rbtnNegativo);

		pnlData.setOpaque(false);
		pnlCompletato.setOpaque(false);
		
		pnlPulsanti.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pnlPulsanti.add(btnIndietro);
		pnlPulsanti.add(Box.createGlue());
		pnlPulsanti.add(btnSalva);
		
		txtNomeCorso.setBorder(BorderFactory.createLineBorder(Color.black,1));
		txtNOre.setBorder(BorderFactory.createLineBorder(Color.black,1));
		txtScadenza.setBorder(BorderFactory.createLineBorder(Color.black,1));
		
		this.rbtnAffermativo.setOpaque(false);
		this.rbtnNegativo.setOpaque(false);
		
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.setBackground(temaBackground);
		
		pnlMain.setOpaque(false);
		pnlPulsanti.setOpaque(false);
		
		pnlMain.add(lblNomeCorso);
		pnlMain.add(txtNomeCorso);
		pnlMain.add(lblData);
		pnlMain.add(pnlData);
		pnlMain.add(lblNOre);
		pnlMain.add(txtNOre);
		pnlMain.add(lblScadenza);
		pnlMain.add(txtScadenza);
		pnlMain.add(lblCompletato);
		pnlMain.add(pnlCompletato);
		this.add(pnlMain, BorderLayout.CENTER);
		this.add(pnlPulsanti, BorderLayout.SOUTH);

	}


	public MenuAggiungiCorsoJPanel(GestoreCorsiJava1 gcj1, int tipologia, Lavoratore lavoratore, CorsoDiFormazione corso) {

		this.tipologia = tipologia;
		gestoreCorsi = gcj1;
		this.lavoratore = lavoratore;
		pannelloModifica = true;
		this.corso = corso;
		
		JPanel pnlPulsanti = new JPanel(new GridLayout(1,3,10,10));

		this.setLayout(new BorderLayout());
		JPanel pnlMain = new JPanel(new GridLayout(3,2,20,20));
		JPanel pnlData = new JPanel(new GridLayout(1,3));
		JPanel pnlCompletato = new JPanel(new GridLayout(1,2,10,10));

		JLabel lblNomeCorso = new JLabel("Nome corso: ");
		JLabel lblCompletato = new JLabel("Completato: ");
		JLabel lblScadenza = new JLabel("Durata (in anni): ");

		CustomJButton btnIndietro = new CustomJButton("Indietro");
		CustomJButton btnSalva = new CustomJButton("Salva");

		btnIndietro.addActionListener(new GestioneIndietro());
		btnSalva.addActionListener(new GestioneSalva());
		rbtnGroup = new ButtonGroup();


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

		pnlData.setOpaque(false);
		pnlCompletato.setOpaque(false);
		
		this.rbtnAffermativo.setOpaque(false);
		this.rbtnNegativo.setOpaque(false);
		
		txtNomeCorso.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		txtScadenza.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		
		btnIndietro.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault.deriveFont(Font.BOLD),2));
		btnSalva.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault.deriveFont(Font.BOLD),2));
		
		lblCompletato.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		lblNomeCorso.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		lblScadenza.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		
		rbtnAffermativo.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		rbtnNegativo.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,3));
		
		cbAnno.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,4));
		cbGiorno.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,4));
		cbMese.addComponentListener(new FontAdj(GestoreCorsiJava1.fontDefault,4));
		
		this.setBackground(temaBackground);
		
		pnlMain.setOpaque(false);
		pnlPulsanti.setOpaque(false);
		
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlPulsanti.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.setBackground(temaBackground);
		
		pnlMain.add(lblNomeCorso);
		pnlMain.add(txtNomeCorso);
		pnlMain.add(lblScadenza);
		pnlMain.add(txtScadenza);
		pnlMain.add(lblCompletato);
		pnlMain.add(pnlCompletato);
		pnlPulsanti.add(btnIndietro);
		pnlPulsanti.add(Box.createGlue());
		pnlPulsanti.add(btnSalva);
		this.add(pnlMain, BorderLayout.CENTER);
		this.add(pnlPulsanti, BorderLayout.SOUTH);

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
					
					
					gestoreCorsi.salva();
					reset();
					
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
			
					
					gestoreCorsi.salva();
					reset();
					
					gestoreCorsi.getPnlDefault().removeAll();
					gestoreCorsi.getPnlDefault().add(new MenuInfoCorsoSpecificoJPanel(gestoreCorsi,corso,lavoratore));
					gestoreCorsi.getPnlDefault().revalidate();
					gestoreCorsi.getPnlDefault().repaint();
					
					
				}

			}


			

		}

	}

	public void reset() {
		txtNomeCorso.setText("");
		txtNOre.setText("");
		txtScadenza.setText("");
		rbtnGroup.clearSelection();
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
