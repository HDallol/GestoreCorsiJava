import java.awt.BorderLayout;
import java.awt.Color;
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



public class MenuAggiungiDataJPanel extends JPanel {

	public final static int GENERALE=0;
	public final static int SPECIFICA=1;
	public final static int PREPOSTO=2;
	public final static int QUINQUIENNALE=3;
	public final static String[] MESI = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

	private CorsoDiFormazione corso;
	private Lavoratore lavoratore;
	private GestoreCorsiJava1 gestoreCorsi;

	private CustomJComboBox cbGiorno = new CustomJComboBox();
	private CustomJComboBox cbMese = new CustomJComboBox();
	private CustomJComboBox cbAnno = new CustomJComboBox();
	private JTextField txtNOre = new JTextField();
	private Color temaBackground = new Color(210,210,210);




	public MenuAggiungiDataJPanel(GestoreCorsiJava1 gcj1, Lavoratore lavoratore, CorsoDiFormazione corso) {
		gestoreCorsi = gcj1;
		this.lavoratore = lavoratore;
		this.corso = corso;



		this.setLayout(new BorderLayout());
		JPanel pnlMain = new JPanel(new GridLayout(2,2,30,30));
		JPanel pnlData = new JPanel(new GridLayout(1,3));
		JPanel pnlPulsanti = new JPanel(new GridLayout(1,3,10,10));
		
		JLabel lblData = new JLabel("Data: ");
		JLabel lblNOre = new JLabel("Numero di ore: ");

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

		txtNOre.addKeyListener(new GestioneTesto());
		
		

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
		
		btnIndietro.addComponentListener(new FontAdj(gestoreCorsi.fontDefault.deriveFont(Font.BOLD),2));
		btnSalva.addComponentListener(new FontAdj(gestoreCorsi.fontDefault.deriveFont(Font.BOLD),2));
		lblData.addComponentListener(new FontAdj(gestoreCorsi.fontDefault,4));
		lblNOre.addComponentListener(new FontAdj(gestoreCorsi.fontDefault,4));
		txtNOre.addComponentListener(new FontAdj(gestoreCorsi.fontDefault,4));
		cbAnno.addComponentListener(new FontAdj(gestoreCorsi.fontDefault,5));
		cbGiorno.addComponentListener(new FontAdj(gestoreCorsi.fontDefault,5));
		cbMese.addComponentListener(new FontAdj(gestoreCorsi.fontDefault,5));
		
		pnlData.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
		pnlPulsanti.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		pnlData.setOpaque(false);
		pnlMain.setOpaque(false);
		pnlPulsanti.setOpaque(false);
		
		this.setBackground(temaBackground);
		
		pnlData.add(cbGiorno);
		pnlData.add(cbMese);
		pnlData.add(cbAnno);
		
		pnlPulsanti.add(btnIndietro);
		pnlPulsanti.add(Box.createGlue());
		pnlPulsanti.add(btnSalva);
	
		pnlMain.add(lblData);
		pnlMain.add(pnlData);
		pnlMain.add(lblNOre);
		pnlMain.add(txtNOre);
	
		this.add(pnlMain, BorderLayout.CENTER);
		this.add(pnlPulsanti, BorderLayout.SOUTH);
	}

	public void reset() {
		Calendar c = Calendar.getInstance();

		cbGiorno.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
		cbMese.setSelectedIndex(c.get(Calendar.MONTH));
		cbAnno.setSelectedIndex(cbAnno.getItemCount()-1);
		txtNOre.setText("");
	}
	

	public class GestioneSalva implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			
			boolean controllo=false;

			int giorno = cbGiorno.getSelectedIndex()+1;
			int mese = cbMese.getSelectedIndex()+1;
			int anno = Integer.parseInt( (String) cbAnno.getSelectedItem() );
			String nOre = txtNOre.getText().trim();

		

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


			if(controllo==false) {
				String data = ""+giorno+"/"+mese+"/"+anno;

				corso.aggiungiData(data, numeroOre);
				corso.ordinaArrayDateEOre();
				reset();

			}
			
			
		}

	}

	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuInfoCorsoSpecificoJPanel(gestoreCorsi, corso, lavoratore));

			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

	}


	/**
	 * Questo resetta il bordo quando scrivi qualcosa, per riportarlo a NERO 
	 * @author Marco
	 *
	 */
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

}
