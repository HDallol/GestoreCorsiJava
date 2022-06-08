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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.ArrayList;

/**
 * Men� iniziale all'apertura del programma.
 * 
 * @author Marco
 *
 */
public class MenuInizialeJPanel extends JPanel {

	private Font fontDefault = new Font("Calibri", Font.PLAIN, 15);

	private JPanel pnlRicerca;
	private JPanel pnlFiltri;
	private ScrollPaneJPanel pnlAggiungi;
	private JPanel pnlOpzioni;

	private JTextField txtBarraRicerca;
	private CustomJButton btnAggiungi;
	private JLabel lblTextoIniziale;
	private JScrollPane spListaLavoratori;
	private boolean filtriAttivi;
	private ArrayList<Lavoratore> arrayLavoratoreConFiltri;
	/**
	 * Una raccolta dei filtri, e non so quali altri mettere
	 */
	private String[] arrayFiltri = {"Corsi scaduti","Corsi in scadenza" , "Da Aggiornare" };
	private ArrayList<CustomJCheckBox> arrayListFiltri;

	/**
	 * Una raccolta di label, in modo da poter poi cambiarne il colore facilmente
	 */
	ArrayList<JLabel> arrayJLabel;

	/**
	 * Temi di default
	 */
	private Color temaBackground = new Color(210,210,210);
	private Color temaFont = Color.BLACK;

	private GestoreCorsiJava1 gestoreCorsi;

	public MenuInizialeJPanel(GestoreCorsiJava1 gcj1) {
		super(new BorderLayout());

		gestoreCorsi = gcj1;		//link al GestoreCorsiJava1: da qui posso recuperare tutti i jpanel custom e l'array di lavoratore

		arrayListFiltri = new ArrayList<CustomJCheckBox>();
		pnlRicerca = new JPanel(new BorderLayout());
		pnlFiltri = new JPanel();
		pnlAggiungi = new ScrollPaneJPanel();
		pnlOpzioni = new JPanel(new GridLayout(1,2,10,10));

		filtriAttivi = false;
		arrayLavoratoreConFiltri = new ArrayList<Lavoratore>();
		txtBarraRicerca = new JTextField();
		btnAggiungi = new CustomJButton("+ Aggiungi",1.5,fontDefault);
		lblTextoIniziale = new JLabel("Premi + per aggiungere");

		pnlFiltri.setLayout(new BoxLayout(pnlFiltri, BoxLayout.Y_AXIS));

		arrayJLabel = new ArrayList<JLabel>();

		CustomJButton btnSalvaPdf = new CustomJButton("Salva tutti i lavoratori (PDF)");
		CustomJButton btnSalvaTxt = new CustomJButton("Salva tutti i lavoratori (txt)");

		spListaLavoratori = new JScrollPane();
		txtBarraRicerca.setBorder(BorderFactory.createLineBorder(temaFont, 1));
		txtBarraRicerca.setFont(fontDefault);
		lblTextoIniziale.setFont(fontDefault.deriveFont(50.0f));
		lblTextoIniziale.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextoIniziale.setVerticalAlignment(SwingConstants.CENTER);
		btnAggiungi.setPreferredSize(new Dimension(250,70));		//La preferred Size del bottone +, da cambiare nel caso

		btnSalvaPdf.addActionListener(new GestioneBtnSalvaPdf());
		btnSalvaTxt.addActionListener(new GestioneBtnSalvaTxt());

		pnlOpzioni.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

		txtBarraRicerca.getDocument().addDocumentListener(new AreaDiTestoListener(txtBarraRicerca, "Cerca...", fontDefault));
		txtBarraRicerca.getDocument().addDocumentListener(new GestioneBarraRicerca(txtBarraRicerca));
		txtBarraRicerca.addComponentListener(new FontAdj(fontDefault, 1.5));
		lblTextoIniziale.addComponentListener(new FontAdj(fontDefault, 8, 60,30));
		btnAggiungi.addActionListener(new GestioneAggiungi());
		btnSalvaPdf.addComponentListener(new FontAdj(fontDefault,2));
		btnSalvaTxt.addComponentListener(new FontAdj(fontDefault,2));
		pnlAggiungi.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));		
		filtri();

		pnlRicerca.add(txtBarraRicerca, BorderLayout.CENTER);
		pnlRicerca.add(btnAggiungi, BorderLayout.WEST);
		pnlRicerca.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlFiltri.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, temaBackground.darker().darker()));

		//pnlAggiungi.add(lblTextoIniziale);
		pnlAggiungi.aggiornaPanel();

		pnlOpzioni.setOpaque(false);

		pnlOpzioni.add(btnSalvaPdf);

		pnlOpzioni.add(btnSalvaTxt);

		cambiaColore(temaBackground, temaFont);


		this.add(pnlRicerca, BorderLayout.NORTH);
		this.add(pnlFiltri, BorderLayout.WEST);
		this.add(pnlAggiungi, BorderLayout.CENTER);
		this.add(pnlOpzioni, BorderLayout.SOUTH);
	}

	public void aggiungiScrollPane() {
		pnlAggiungi.removeAll();

		int righe = 0;
		if(gestoreCorsi.getArrayLavoratori().size()<5)
			righe=5;
		else
			righe=gestoreCorsi.getArrayLavoratori().size();

		JPanel pnlLavoratore = new JPanel(new GridLayout(righe,1,3,3));
		pnlLavoratore.setBackground(temaBackground);

		for(int i=0;i<gestoreCorsi.getArrayLavoratori().size();i++) {

			JPanel pnl = new JPanel(new GridLayout(1,2));
			System.out.println("::: "+gestoreCorsi.getArrayLavoratori().get(i).getNome()+" "+gestoreCorsi.getArrayLavoratori().get(i).getCognome());
			JLabel lbl = new JLabel(gestoreCorsi.getArrayLavoratori().get(i).getNome()+" "+gestoreCorsi.getArrayLavoratori().get(i).getCognome());
			lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			pnl.add(lbl);
			pnl.setBackground(Color.WHITE);
			pnl.setPreferredSize(new Dimension(100,80));		//DA MODIFICARE NEL CASO
			pnlLavoratore.add(pnl);


			System.out.println(i);
		}

		spListaLavoratori = new JScrollPane(pnlLavoratore);
		spListaLavoratori.setBorder(null);
		pnlAggiungi.add(spListaLavoratori);

	}


	public ScrollPaneJPanel getScrollPanePnl() {
		return pnlAggiungi;
	}

	/**
	 * Metodo per cambiare colore a tutti i componenti di questo Panel
	 * in una sola volta
	 * 
	 * @param temaBackground il colore del background
	 * @param temaFont il colore del testo
	 */
	public void cambiaColore(Color temaBackground, Color temaFont) {
		this.temaBackground = temaBackground;
		this.temaFont = temaFont;

		this.setBackground(temaBackground);
		pnlRicerca.setBackground(temaBackground);
		pnlFiltri.setBackground(temaBackground);
		pnlAggiungi.setBackground(temaBackground);

		for(int i=0;i<arrayJLabel.size();i++) {
			arrayJLabel.get(i).setForeground(temaFont);
		}

	}

	/**
	 * Riporta il panel alla condizione iniziale
	 */
	public void reset() {

		arrayLavoratoreConFiltri.clear();
		filtriAttivi = false;

		for(int i=0;i<arrayListFiltri.size();i++) {
			arrayListFiltri.get(i).setSelected(false);
		}

		txtBarraRicerca.setText("");
		pnlAggiungi.aggiornaPanel();
	}

	/**
	 * Aggiunge tutti i filtri dell'arrayFiltri nel pannello
	 */
	private void filtri() {

		arrayListFiltri.add(aggiungiFiltro(arrayListFiltri.size(), new GestioneFiltroCorsiScaduti()));
		arrayListFiltri.add(aggiungiFiltro(arrayListFiltri.size(), new GestioneFiltroCorsiInScadenza()));
		arrayListFiltri.add(aggiungiFiltro(arrayListFiltri.size(), new GestioneFiltroDaAggiornare()));


		//		for(int i=0;i<arrayFiltri.length;i++) {
		//			arrayListFiltri.add(aggiungiFiltro(i, new ActionListener() {
		//
		//				@Override
		//				public void actionPerformed(ActionEvent e) {
		//					// TODO Auto-generated method stub
		//					CustomJCheckBox cb = (CustomJCheckBox) e.getSource();
		//
		//					if(cb.isSelected()) {
		//						System.out.println("Hello "+cb.getName());
		//
		//						for(int i=0;i<arrayListFiltri.size();i++) {
		//							if(cb!=arrayListFiltri.get(i)) {
		//								arrayListFiltri.get(i).setSelected(false);
		//							}
		//						}
		//
		//					}
		//
		//				}
		//
		//			}));
		//		}
	}

	/**
	 * Crea e aggiunge un filtro nel pannello 'pnlFiltri'
	 * 
	 * @param indiceFiltro l'indice dell'array arrayFiltri
	 * @param azioneFiltro l'ActionListener da aggiungere al filtro
	 */
	private CustomJCheckBox aggiungiFiltro(int indiceFiltro, ActionListener azioneFiltro) {

		//for(int i=0; i<arrayFiltri.length;i++) {
		CustomJCheckBox cbFiltro1 = new CustomJCheckBox();
		cbFiltro1.setName(""+indiceFiltro);

		JPanel pnl1 = new JPanel(new BorderLayout());
		JLabel lbl1 = new JLabel(arrayFiltri[indiceFiltro]);

		arrayJLabel.add(lbl1);

		lbl1.addComponentListener(new FontAdj(fontDefault, 2));
		lbl1.setVerticalAlignment(SwingConstants.CENTER);
		cbFiltro1.addActionListener(azioneFiltro);

		cbFiltro1.setOpaque(false);

		pnl1.setMaximumSize(new Dimension(200,40));
		pnl1.setOpaque(false);
		pnl1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		pnl1.add(cbFiltro1, BorderLayout.WEST);
		pnl1.add(lbl1, BorderLayout.CENTER);

		pnlFiltri.add(pnl1);

		return cbFiltro1;
	}


	public class GestioneAggiungi implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getMenuAggiungiLavoratore().reset();
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(gestoreCorsi.getMenuAggiungiLavoratore());
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

	}

	public class ScrollPaneJPanel extends JPanel {

		public ScrollPaneJPanel() {
			super();
			this.setLayout(new GridLayout(1,1));
		}

		public void aggiornaPanel() {

			ArrayList<Lavoratore> arr;

			if(filtriAttivi==false) {
				arr = gestoreCorsi.getArrayLavoratori();
			}
			else {
				arr = arrayLavoratoreConFiltri;
			}

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

					LavoratorePnl pnl = new LavoratorePnl(i);
					//DA MODIFICARE NEL CASO
					pnlLavoratore.add(pnl);

				}

				spListaLavoratori = new JScrollPane(pnlLavoratore);
				spListaLavoratori.setBorder(null);

				//spListaLavoratori.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

				this.add(spListaLavoratori);

			}

			this.revalidate();
			this.repaint();
		}

		public void aggiornaPanel(String parolaChiave) {

			ArrayList<Lavoratore> arr;

			if(filtriAttivi==false) {
				arr = gestoreCorsi.getArrayLavoratori();
			}
			else {
				arr = arrayLavoratoreConFiltri;
			}
			this.removeAll();

			if(arr.size()==0) {
				this.setLayout(new GridLayout(1,1));
				this.add(lblTextoIniziale);
			}
			else {

				int cont=0;

				String sRicerca[] = parolaChiave.split(" ");
				ArrayList<Integer> paroleUsate = new ArrayList<Integer>(5);

				for(int i=0;i<arr.size();i++) {
					String nomeCognome = arr.get(i).getNome() +" "+arr.get(i).getCognome();
					String sLavoratore[] = nomeCognome.split(" ");
					paroleUsate.clear();

					int temp=0;

					for(int j=0;j<sLavoratore.length;j++) {

						for(int k=0;k<sRicerca.length;k++) {

							try {
								if(!paroleUsate.contains(k)) {
									if( (sLavoratore[j].substring(0, sRicerca[k].length()).equalsIgnoreCase(sRicerca[k])) ) {
										temp++;
										//controllo=true;
										paroleUsate.add(Integer.valueOf(k));
										break;
									}
								}
							} catch(StringIndexOutOfBoundsException e) {

							}
						}

						if(temp==sRicerca.length)
							break;
					}

					if(temp==sRicerca.length)
						cont++;

				}

				int righe = cont;

				if(righe==0) {
					JLabel lblTestoRicercaNulla = new JLabel("Nessun risultato", SwingConstants.CENTER);
					lblTestoRicercaNulla.addComponentListener(new FontAdj(fontDefault,2));
					this.setLayout(new GridLayout(1,1));
					this.add(lblTestoRicercaNulla);
				}
				else {

					if(cont<5)
						righe=5;

					JPanel pnlLavoratore = new JPanel(new GridLayout(righe,1,3,3));
					pnlLavoratore.setBackground(temaBackground);

					paroleUsate.clear();

					for(int i=0;i<arr.size();i++) {
						String nomeCognome = arr.get(i).getNome() +" "+arr.get(i).getCognome();
						String sLavoratore[] = nomeCognome.split(" ");
						paroleUsate.clear();

						int temp=0;

						for(int j=0;j<sLavoratore.length;j++) {

							for(int k=0;k<sRicerca.length;k++) {

								try {
									if(!paroleUsate.contains(k)) {
										if( (sLavoratore[j].substring(0, sRicerca[k].length()).equalsIgnoreCase(sRicerca[k])) ) {
											temp++;
											//controllo=true;
											paroleUsate.add(Integer.valueOf(k));
											break;
										}
									}
								} catch(StringIndexOutOfBoundsException e) {

								}
							}

							if(temp==sRicerca.length)
								break;
						}

						if(temp==sRicerca.length) {
							LavoratorePnl pnl = new LavoratorePnl(i);
							pnlLavoratore.add(pnl);
						}
					}

					spListaLavoratori = new JScrollPane(pnlLavoratore);
					spListaLavoratori.setBorder(null);
					this.add(spListaLavoratori);
				}


			}

			this.revalidate();
			this.repaint();

		}	

		/**
		 * Panel per ogni lavoratore nel JScrollPane
		 */
		public class LavoratorePnl extends JPanel implements MouseListener, ComponentListener{

			int index;

			Color temaSfondo;
			Color temaMouseEntered;
			CustomJButton btnCancella;
			JLabel lbl;
			ArrayList<Lavoratore> arr;

			public LavoratorePnl(int index) {



				if(filtriAttivi==false) {
					arr = gestoreCorsi.getArrayLavoratori();
				}
				else {
					arr = arrayLavoratoreConFiltri;
				}

				this.setLayout(new BorderLayout());
				this.index = index;
				String cognome = arr.get(index).getCognome();
				String nome = arr.get(index).getNome();
				int nCorsi = arr.get(index).getCorsiDiFormazioni().size();
				int nCorsiScaduti = arr.get(index).getCorsiScaduti();
				lbl = new JLabel("<html>"+cognome+" "+nome+" - <i>Corsi: "+nCorsi+"; Scaduti: "+nCorsiScaduti+"</i></html>");
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

				if(filtriAttivi) {
					for(int i=0;i<gestoreCorsi.getArrayLavoratori().size();i++) {
						if(gestoreCorsi.getArrayLavoratori().get(i).equals(arrayLavoratoreConFiltri.get(index))) {
							index = i;
							break;
						}
					}

				}

				gestoreCorsi.getPnlDefault().removeAll();
				gestoreCorsi.getPnlDefault().add(new MenuInfoLavoratoreJPanel(gestoreCorsi, index));
				gestoreCorsi.getPnlDefault().revalidate();
				gestoreCorsi.getPnlDefault().repaint();

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

					int x = JOptionPane.showConfirmDialog(null, "Vuoi davvero cancellare " + arr.get(index).getNome() +" "+arr.get(index).getCognome()+"?", "Attenzione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if(x==0) {
						gestoreCorsi.getArrayLavoratori().remove(index);
						pnlAggiungi.aggiornaPanel();
						pnlAggiungi.revalidate();
						pnlAggiungi.repaint();
						gestoreCorsi.salva();
					}



				}

			}

		}

	}



	public class GestioneBarraRicerca implements DocumentListener {

		JTextField txt;

		public GestioneBarraRicerca(JTextField txt) {
			this.txt = txt;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String parolaChiave = txt.getText();

			if(parolaChiave.length()>0) 
				pnlAggiungi.aggiornaPanel(parolaChiave);
			else
				pnlAggiungi.aggiornaPanel();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String parolaChiave = txt.getText();

			if(parolaChiave.length()>0) 
				pnlAggiungi.aggiornaPanel(parolaChiave);
			else
				pnlAggiungi.aggiornaPanel();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	}


	/**
	 * Una gestione per il filtro "Corsi scaduti", che fitra tutti i lavoratori per mostrare solo quelli che
	 * hanno 1 o pi� corsi scaduti
	 * 
	 * @author Marco
	 *
	 */
	public class GestioneFiltroCorsiScaduti implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			CustomJCheckBox cb = (CustomJCheckBox) e.getSource();

			ArrayList<Lavoratore> arr = gestoreCorsi.getArrayLavoratori();
			ArrayList<Lavoratore> arrFiltro = new ArrayList<Lavoratore>();

			arrayLavoratoreConFiltri.clear();

			if(cb.isSelected()) {

				for(int i=0;i<arrayListFiltri.size();i++) {
					if(cb!=arrayListFiltri.get(i)) {
						arrayListFiltri.get(i).setSelected(false);
					}
				}

				filtriAttivi = true;

				for(int i=0;i<arr.size();i++) {
					if(arr.get(i).getCorsiScaduti()>0) {
						arrFiltro.add(arr.get(i));
						System.out.println("Aggiunto al filtro 1: "+i);
					}
				}

				for(int i=0;i<arrFiltro.size();i++) {
					if( !(arrayLavoratoreConFiltri.contains(arrFiltro.get(i))) ) {
						arrayLavoratoreConFiltri.add(arrFiltro.get(i));
					}
				}

				System.out.println("Array Filtri: "+arrFiltro.size());
				System.out.println("ArrayLavoratore con filtri: "+arrayLavoratoreConFiltri.size());

				for(int i=0;i<arrayLavoratoreConFiltri.size();i++) {
					System.out.println("NOME DEL PLEBEO: "+arrayLavoratoreConFiltri.get(i).getNome());
				}

				txtBarraRicerca.setText("");
				pnlAggiungi.aggiornaPanel();
			}
			else {
				arrayLavoratoreConFiltri.clear();
				filtriAttivi = false;
				pnlAggiungi.aggiornaPanel();
			}


		}

	}

	public class GestioneFiltroCorsiInScadenza implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub


			CustomJCheckBox cb = (CustomJCheckBox) e.getSource();

			ArrayList<Lavoratore> arr = gestoreCorsi.getArrayLavoratori();
			ArrayList<Lavoratore> arrFiltro = new ArrayList<Lavoratore>();

			arrayLavoratoreConFiltri.clear();

			if(cb.isSelected()) {

				for(int i=0;i<arrayListFiltri.size();i++) {
					if(cb!=arrayListFiltri.get(i)) {
						arrayListFiltri.get(i).setSelected(false);
					}
				}

				filtriAttivi = true;

				for(int i=0;i<arr.size();i++) {
					if(arr.get(i).getCorsiInScadenza()>0) {
						arrFiltro.add(arr.get(i));
					}
				}

				for(int i=0;i<arrFiltro.size();i++) {
					if( !(arrayLavoratoreConFiltri.contains(arrFiltro.get(i))) ) {
						arrayLavoratoreConFiltri.add(arrFiltro.get(i));
					}
				}

				System.out.println("Array Filtri: "+arrFiltro.size());
				System.out.println("ArrayLavoratore con filtri: "+arrayLavoratoreConFiltri.size());


				txtBarraRicerca.setText("");
				pnlAggiungi.aggiornaPanel();
			}
			else {
				arrayLavoratoreConFiltri.clear();
				filtriAttivi = false;
				pnlAggiungi.aggiornaPanel();
			}


		}

	}

	public class GestioneFiltroDaAggiornare implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub


			CustomJCheckBox cb = (CustomJCheckBox) e.getSource();

			ArrayList<Lavoratore> arr = gestoreCorsi.getArrayLavoratori();
			ArrayList<Lavoratore> arrFiltro = new ArrayList<Lavoratore>();

			arrayLavoratoreConFiltri.clear();

			if(cb.isSelected()) {

				for(int i=0;i<arrayListFiltri.size();i++) {
					if(cb!=arrayListFiltri.get(i)) {
						arrayListFiltri.get(i).setSelected(false);
					}
				}

				filtriAttivi = true;

				for(int i=0;i<arr.size();i++) {
					if(arr.get(i).getDaAggiornare()) {
						arrFiltro.add(arr.get(i));
						System.out.println("Aggiunto al filtro 1: "+i);
					}
				}

				for(int i=0;i<arrFiltro.size();i++) {
					if( !(arrayLavoratoreConFiltri.contains(arrFiltro.get(i))) ) {
						arrayLavoratoreConFiltri.add(arrFiltro.get(i));
					}
				}

				System.out.println("Array Filtri: "+arrFiltro.size());
				System.out.println("ArrayLavoratore con filtri: "+arrayLavoratoreConFiltri.size());

				for(int i=0;i<arrayLavoratoreConFiltri.size();i++) {
					System.out.println("NOME DEL PLEBEO: "+arrayLavoratoreConFiltri.get(i).getNome());
				}

				txtBarraRicerca.setText("");
				pnlAggiungi.aggiornaPanel();
			}
			else {
				arrayLavoratoreConFiltri.clear();
				filtriAttivi = false;
				pnlAggiungi.aggiornaPanel();
			}


		}

	}


	public class GestioneBtnSalvaPdf implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if(gestoreCorsi.getArrayLavoratori().size()>0) {
				Thread t = new Thread(gestoreCorsi.new SalvaPdfThread());
				t.start();
			}
			else {
				JOptionPane.showMessageDialog(null, "Non � presente nessun lavoratore", "Errore", JOptionPane.WARNING_MESSAGE);
			}

		}

	}

	public class GestioneBtnSalvaTxt implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if(gestoreCorsi.getArrayLavoratori().size()>0) {
				Thread t = new Thread(gestoreCorsi.new SalvaTxtThread());
				t.start();

			}
			else {
				JOptionPane.showMessageDialog(null, "Non � presente nessun lavoratore", "Errore", JOptionPane.WARNING_MESSAGE);

			}
		}

	}

}
