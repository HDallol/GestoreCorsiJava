import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


/**
 * Quello che esce quando clicchi su un corso di un lavoratore.
 * Mostra le info del corso.
 * 
 * @author Marco
 *
 */
public class MenuInfoCorsoSpecificoJPanel extends JPanel {

	private Font fontDefault = new Font("Calibri", Font.PLAIN, 15);
	private Color temaBackground = new Color(210,210,210);

	private GestoreCorsiJava1 gestoreCorsi;
	private CorsoDiFormazione corso;
	private Lavoratore lavoratore;

	JLabel lblNomeCorso;
	JLabel lblTipologia;
	JLabel lblData;
	JLabel lblNOre;
	JLabel lblDurata;
	JLabel lblStato;

	/**
	 * 
	 * @param gcj1 il gestoreCorsi
	 * @param corso il corso specifico che vogliamo su schermo
	 * @param lav il riferimento del lavoratore che ha il corso
	 */
	public MenuInfoCorsoSpecificoJPanel(GestoreCorsiJava1 gcj1, CorsoDiFormazione corso, Lavoratore lav) {

		gestoreCorsi = gcj1;
		this.corso = corso;
		this.lavoratore = lav;

		this.setLayout(new BorderLayout());

		JPanel pnlMain = new JPanel(new GridLayout(1,2));
		JPanel pnlInfo = new JPanel(new GridLayout(6,1));
		JPanel pnlPulsanti = new JPanel(new GridLayout(1,2,10,10));
		JPanel pnlDate = new JPanel(new BorderLayout());
		ScrollPaneJPanel spPanel = new ScrollPaneJPanel();

		lblNomeCorso = new JLabel();
		lblTipologia = new JLabel();
		lblData = new JLabel();
		lblNOre = new JLabel();
		lblDurata = new JLabel();
		lblStato = new JLabel();

		CustomJButton btnIndietro = new CustomJButton("Indietro");
		CustomJButton btnModifica = new CustomJButton("Modifica");
		CustomJButton btnAggiungiData = new CustomJButton("Aggiungi data");

		btnIndietro.addActionListener(new GestioneIndietro());
		btnModifica.addActionListener(new GestioneModifica());
		btnAggiungiData.addActionListener(new GestioneAggiungiData());

		reset();


		pnlInfo.add(lblNomeCorso);
		pnlInfo.add(lblTipologia);
		pnlInfo.add(lblData);
		pnlInfo.add(lblNOre);
		pnlInfo.add(lblDurata);
		pnlInfo.add(lblStato);

		pnlPulsanti.add(btnIndietro);
		pnlPulsanti.add(btnModifica);

		spPanel.aggiornaPanel();

		pnlDate.add(spPanel, BorderLayout.CENTER);
		pnlDate.add(btnAggiungiData, BorderLayout.SOUTH);

		pnlMain.add(pnlInfo);
		pnlMain.add(pnlDate);

		this.add(pnlMain, BorderLayout.CENTER);
		this.add(pnlPulsanti, BorderLayout.SOUTH);
	}

	public void reset() {

		lblDurata.setText("Durata (in anni): "+corso.getScadenzaAnni());
		lblNomeCorso.setText("Corso: "+corso.getNomeCorso());
		lblNOre.setText("Numero di ore complessivo: "+corso.getNOreComplessive());
		lblStato.setText("");
		lblTipologia.setText("");
		lblData.setText("Data di riferimento: "+corso.getData().get(corso.getData().size()-1));

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
		case CorsoDiFormazione.DA_COMPLETARE:
			lblStato.setText("Stato: DA COMPLETARE");
		}

		this.revalidate();
		this.repaint();

	}

	public class GestioneAggiungiData implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuAggiungiDataJPanel(gestoreCorsi, lavoratore, corso));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

	}

	public class GestioneModifica implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuAggiungiCorsoJPanel(gestoreCorsi,corso.getTipologia(),lavoratore,corso));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

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



	public class ScrollPaneJPanel extends JPanel {

		JScrollPane spListaDate;

		public ScrollPaneJPanel() {
			super();
			spListaDate = new JScrollPane();
			this.setLayout(new GridLayout(1,1));
			this.setOpaque(false);
		}

		public void aggiornaPanel() {

			ArrayList<String> arr = corso.getData();

			JLabel lblTextoIniziale = new JLabel("Nessuna data disponibile");

			this.removeAll();

			if(arr.size()==0) {
				this.setLayout(new GridLayout(1,1));
				this.add(lblTextoIniziale);
			}
			else {

				int righe = 0;
				if(arr.size()<5) {
					righe=5;
				}
				else {
					righe=arr.size();
				}

				JPanel pnlLavoratore = new JPanel(new GridLayout(righe,1,3,3));
				pnlLavoratore.setBackground(temaBackground);

				for(int i=0;i<arr.size();i++) {

					DataPnl pnl = new DataPnl(i);
					if(arr.size()==1)
						pnl.cancellaAttivato(false);
					//DA MODIFICARE NEL CASO
					pnlLavoratore.add(pnl);

				}

				spListaDate = new JScrollPane(pnlLavoratore);
				spListaDate.setBorder(null);

				//spListaLavoratori.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

				this.add(spListaDate);

			}

			this.revalidate();
			this.repaint();
		}



		/**
		 * Panel per ogni lavoratore nel JScrollPane
		 */
		public class DataPnl extends JPanel implements MouseListener, ComponentListener{

			int index;

			Color temaSfondo;
			Color temaMouseEntered;
			CustomJButton btnCancella;
			JLabel lbl;
			String data;

			public DataPnl(int index) {

				ArrayList<String> arr = corso.getData();


				this.setLayout(new BorderLayout());
				this.index = index;

				data = arr.get(index);
				int nOre = corso.getnOre().get(index);

				lbl = new JLabel((index+1)+"- Data: "+data+"     Ore: "+nOre);
				btnCancella = new CustomJButton("X",2);
				lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				lbl.addComponentListener(new FontAdj(fontDefault,3));

				temaSfondo = Color.WHITE;
				temaMouseEntered = new Color(190,190,190);

				btnCancella.setPreferredSize(new Dimension((int) (this.getWidth()*0.1), this.getHeight()));
				btnCancella.addActionListener(new GestioneCancella());
				cambiaColore(temaSfondo, temaMouseEntered);


				this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				this.setPreferredSize(new Dimension(100,80));
				this.addMouseListener(this);
				this.addComponentListener(this);
				this.add(lbl, BorderLayout.CENTER);
				this.add(btnCancella, BorderLayout.EAST);
			}
			
			public void cancellaAttivato(boolean attivato) {
				btnCancella.setEnabled(attivato);
			}
			
			public void cambiaColore(Color temaSfondo, Color temaMouseEntered) {
				this.temaSfondo = temaSfondo;
				this.temaMouseEntered = temaMouseEntered;
				this.setBackground(temaSfondo);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub



			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				this.setBackground(temaMouseEntered);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				this.setBackground(temaSfondo);
			}


			/**
			 * Piccola funzione per mantenere fixata la grandezza del bottone: FUNZIONA MEGLIO DEL PREVISTO QUINDI 
			 * SI PUO' RIUTILIZZARE NEL CASO
			 */
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				btnCancella.setPreferredSize(new Dimension((int) (this.getWidth()*0.1), this.getHeight()));
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}


			public class GestioneCancella implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					if(corso.getData().size()>1) {

						corso.rimuoviData(data);
						aggiornaPanel();
						reset();
						
						gestoreCorsi.salva();
					}
				}

			}

		}

	}



}
