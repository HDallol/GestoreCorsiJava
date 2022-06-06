import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



/**
 * Questo è il menu che compare quando scegli una delle tipologie di corso nel MenuInfoLavoratore.
 * Vi sono tutti i corsi di un determinato tipo.
 * 
 * Preso spunto dal menu iniziale.
 * 
 * @author Marco
 *
 */

public class MenuInfoCorsiJPanel extends JPanel {

	public final static int GENERALE=0;
	public final static int SPECIFICA=1;
	public final static int PREPOSTO=2;
	public final static int QUINQUIENNALE=3;
	
	private int tipologia;

	private Lavoratore lavoratore;
	
	private Font fontDefault = new Font("Calibri", Font.PLAIN, 15);

	private JPanel pnlRicerca;
	private ScrollPaneJPanel pnlAggiungi;

	private JTextField txtBarraRicerca;
	private CustomJButton btnAggiungi;
	private JLabel lblTextoIniziale;
	private JScrollPane spListaCorsi;

	/**
	 * Array che contiene i corsi specifici di una tipologia da rappresentare a schermo
	 */
	private ArrayList<CorsoDiFormazione> arrCorsi;
	

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

	/**
	 * 
	 * @param gcj1 il GestoreCorsi
	 * @param lavoratore il lavoratore su cui stiamo lavorando
	 * @param tipologia tipologia tra GENERALE, SPECIFICA, PREPOSTO o QUINQUIENNALE
	 */
	public MenuInfoCorsiJPanel(GestoreCorsiJava1 gcj1, Lavoratore lavoratore, int tipologia) {
		super(new BorderLayout());

		gestoreCorsi = gcj1;		

		this.lavoratore = lavoratore;
		this.tipologia = tipologia;
		
		
		arrCorsi = new ArrayList<CorsoDiFormazione>();
		
		for(int i=0;i<lavoratore.getCorsiDiFormazioni().size();i++) {
			if(lavoratore.getCorsiDiFormazioni().get(i).getTipologia()==tipologia) {
				arrCorsi.add(lavoratore.getCorsiDiFormazioni().get(i));
				System.out.println("Array indice del corso: "+i);
			}
		}
		
		String s = "";
		
		switch(tipologia) {
		case GENERALE:
			s= "Formazione Generale";
			break;
		case SPECIFICA:
			s = "Formazione Specifica";
			break;
		case PREPOSTO:
			s = "Formazione Preposto";
			break;
		case QUINQUIENNALE:
			s = "Aggiornamenti Quinquiennale";
			break;
		}
		
		JLabel lblTipoCorso = new JLabel(s);
		lblTipoCorso.addComponentListener(new FontAdj(fontDefault.deriveFont(Font.ITALIC),2));
		lblTipoCorso.setPreferredSize(new Dimension(300,45));
		
		pnlRicerca = new JPanel(new BorderLayout());
		JPanel pnlTipoCorso = new JPanel(new GridLayout(1,1));
		JPanel pnlIndietro = new JPanel(new GridLayout(1,3,10,10));
		pnlAggiungi = new ScrollPaneJPanel();

		txtBarraRicerca = new JTextField();
		btnAggiungi = new CustomJButton("+",1.5,fontDefault);
		lblTextoIniziale = new JLabel("Premi + per aggiungere");
		
		
		CustomJButton btnIndietro = new CustomJButton("Indietro");
		btnIndietro.addActionListener(new GestioneIndietro());
		btnIndietro.addComponentListener(new FontAdj(fontDefault.deriveFont(Font.BOLD), 2));
		
		arrayJLabel = new ArrayList<JLabel>();

		spListaCorsi = new JScrollPane();
		txtBarraRicerca.setBorder(BorderFactory.createLineBorder(temaFont, 1));
		txtBarraRicerca.setFont(fontDefault);
		lblTextoIniziale.setFont(fontDefault.deriveFont(50.0f));
		lblTextoIniziale.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextoIniziale.setVerticalAlignment(SwingConstants.CENTER);
		btnAggiungi.setPreferredSize(new Dimension(40,40));		//La preferred Size del bottone +, da cambiare nel caso


		txtBarraRicerca.getDocument().addDocumentListener(new AreaDiTestoListener(txtBarraRicerca, "Cerca...", fontDefault));
		txtBarraRicerca.getDocument().addDocumentListener(new GestioneBarraRicerca(txtBarraRicerca));
		txtBarraRicerca.addComponentListener(new FontAdj(fontDefault, 1.5));
		lblTextoIniziale.addComponentListener(new FontAdj(fontDefault, 8, 60,30));
		btnAggiungi.addActionListener(new GestioneAggiungi());
		pnlAggiungi.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));		
	
		pnlTipoCorso.add(lblTipoCorso);
		
		pnlRicerca.add(pnlTipoCorso, BorderLayout.NORTH);
		pnlRicerca.add(txtBarraRicerca, BorderLayout.CENTER);
		pnlRicerca.add(btnAggiungi, BorderLayout.EAST);
		pnlRicerca.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		//pnlAggiungi.add(lblTextoIniziale);
		pnlAggiungi.aggiornaPanel();

		cambiaColore(temaBackground, temaFont);

		pnlIndietro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		pnlTipoCorso.setOpaque(false);
		pnlIndietro.setOpaque(false);
		pnlIndietro.add(btnIndietro);
		pnlIndietro.add(Box.createGlue());
		pnlIndietro.add(Box.createGlue());
	
		this.add(pnlRicerca, BorderLayout.NORTH);
		this.add(pnlAggiungi, BorderLayout.CENTER);
		this.add(pnlIndietro, BorderLayout.SOUTH);
		
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

		spListaCorsi = new JScrollPane(pnlLavoratore);
		spListaCorsi.setBorder(null);
		pnlAggiungi.add(spListaCorsi);

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
		pnlAggiungi.setBackground(temaBackground);

		for(int i=0;i<arrayJLabel.size();i++) {
			arrayJLabel.get(i).setForeground(temaFont);
		}

	}

	/**
	 * Riporta il panel alla condizione iniziale
	 */
	public void reset() {
		txtBarraRicerca.setText("");
		pnlAggiungi.aggiornaPanel();
	}



	public class GestioneAggiungi implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuAggiungiCorsoJPanel(gestoreCorsi,tipologia, lavoratore));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

	}

	public class ScrollPaneJPanel extends JPanel {

		public ScrollPaneJPanel() {
			super(new GridLayout(1,1));
			
		}

		public void aggiornaPanel() {

			this.removeAll();

			if(arrCorsi.size()==0) {
				this.setLayout(new GridLayout(1,1));
				this.add(lblTextoIniziale);
			}
			else {

				int righe = 0;
				if(arrCorsi.size()<5) {
					righe=5;
				}
				else {
					righe=arrCorsi.size();
				}

				JPanel pnlListaCorsi = new JPanel(new GridLayout(righe,1,3,3));
				pnlListaCorsi.setBackground(temaBackground);

				for(int i=0;i<arrCorsi.size();i++) {

					CorsoPnl pnl = new CorsoPnl(i);
					//DA MODIFICARE NEL CASO
					pnlListaCorsi.add(pnl);

				}

				spListaCorsi = new JScrollPane(pnlListaCorsi);
				spListaCorsi.setBorder(null);

				//spListaCorsi.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

				this.add(spListaCorsi);

			}

			this.revalidate();
			this.repaint();
		}

		public void aggiornaPanel(String parolaChiave) {

			this.removeAll();

			if(arrCorsi.size()==0) {
				this.setLayout(new GridLayout(1,1));
				this.add(lblTextoIniziale);
			}
			else {

				int cont=0;

				String sRicerca[] = parolaChiave.split(" ");
				ArrayList<Integer> paroleUsate = new ArrayList<Integer>(5);

				for(int i=0;i<arrCorsi.size();i++) {
					String nomeCorso = arrCorsi.get(i).getNomeCorso();
					String sCorsoSplit[] = nomeCorso.split(" ");
					paroleUsate.clear();

					int temp=0;

					for(int j=0;j<sCorsoSplit.length;j++) {

						for(int k=0;k<sRicerca.length;k++) {

							try {
								if(!paroleUsate.contains(k)) {
									if( (sCorsoSplit[j].substring(0, sRicerca[k].length()).equalsIgnoreCase(sRicerca[k])) ) {
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

					for(int i=0;i<arrCorsi.size();i++) {
						String nomeCorso = arrCorsi.get(i).getNomeCorso();
						String sCorsoSplit[] = nomeCorso.split(" ");
						paroleUsate.clear();

						int temp=0;

						for(int j=0;j<sCorsoSplit.length;j++) {

							for(int k=0;k<sRicerca.length;k++) {

								try {
									if(!paroleUsate.contains(k)) {
										if( (sCorsoSplit[j].substring(0, sRicerca[k].length()).equalsIgnoreCase(sRicerca[k])) ) {
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
							CorsoPnl pnl = new CorsoPnl(i);
							pnlLavoratore.add(pnl);
						}
					}




					spListaCorsi = new JScrollPane(pnlLavoratore);
					spListaCorsi.setBorder(null);
					this.add(spListaCorsi);
				}


			}

			this.revalidate();
			this.repaint();

		}	

		/**
		 * Panel per ogni corso nel JScrollPane
		 */
		public class CorsoPnl extends JPanel implements MouseListener, ComponentListener{

			int index;

			Color temaSfondo;
			Color temaMouseEntered;
			CustomJButton btnCancella;
			JLabel lbl;
			AvvisoScadenzaJPanel avvisoScadenzaPnl;
			
			public CorsoPnl(int index) {

				this.setLayout(new BorderLayout());
				
				JPanel pnlCenter = new JPanel(new BorderLayout());
				avvisoScadenzaPnl = new AvvisoScadenzaJPanel(arrCorsi.get(index).getStato());
				
				this.index = index;
				
				lbl = new JLabel(arrCorsi.get(index).getNomeCorso());
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
				
				pnlCenter.setOpaque(false);
				pnlCenter.add(lbl,BorderLayout.CENTER);
				pnlCenter.add(avvisoScadenzaPnl, BorderLayout.EAST);
				
				this.addMouseListener(this);
				this.addComponentListener(this);
				this.add(pnlCenter, BorderLayout.CENTER);
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

				gestoreCorsi.getPnlDefault().removeAll();
				gestoreCorsi.getPnlDefault().add(new MenuInfoCorsoSpecificoJPanel(gestoreCorsi,arrCorsi.get(index),lavoratore));
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
				avvisoScadenzaPnl.setPreferredSize(new Dimension((int) (this.getWidth()*0.1), this.getHeight()));
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
					
					int x = JOptionPane.showConfirmDialog(null, "Vuoi davvero cancellare il corso "+arrCorsi.get(index).getNomeCorso()+"?", "Attenzione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if(x==0) {
						lavoratore.getCorsiDiFormazioni().remove(arrCorsi.get(index));
						arrCorsi.remove(index);
						pnlAggiungi.aggiornaPanel();
						pnlAggiungi.revalidate();
						pnlAggiungi.repaint();
						
						gestoreCorsi.salva();

					}
					
				}

			}

			
			public class AvvisoScadenzaJPanel extends JPanel {
				
				private double grandezzaCerchio;
				private int stato;
				private Color colore;
				
				public AvvisoScadenzaJPanel(int stato) {
					this.setOpaque(false);
					grandezzaCerchio = 0.6;			//LA GRANDEZZA DEL CERCHIO COLORATO: Da 0 a 1
					
					
					this.stato = stato;
					
					if(stato==CorsoDiFormazione.VALIDO) {
						colore = Color.GREEN;
						this.setToolTipText("Valido");
					}
					else if(stato==CorsoDiFormazione.IN_SCADENZA) {
						colore = Color.ORANGE;
						this.setToolTipText("In Scadenza");
					}
					else if(stato==CorsoDiFormazione.SCADUTO){
						colore = Color.RED;
						this.setToolTipText("Scaduto");
					}
					else {
						colore = Color.BLUE;
						this.setToolTipText("Da completare");
					}
					
				}
				
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.setColor(colore);
					g.fillOval((int) (this.getWidth()-this.getHeight()*grandezzaCerchio)/2, (int) (this.getHeight()-this.getHeight()*grandezzaCerchio)/2,
							(int) (this.getHeight()*grandezzaCerchio),(int) (this.getHeight()*grandezzaCerchio));
					

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
	
	
	
	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			int index=0;
			
			for(int i=0;i<gestoreCorsi.getArrayLavoratori().size();i++) {
				
				if(gestoreCorsi.getArrayLavoratori().get(i)==lavoratore) {
					index = i;
					break;
				}
					
			}
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuInfoLavoratoreJPanel(gestoreCorsi, index));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}
		
	}
	
}
