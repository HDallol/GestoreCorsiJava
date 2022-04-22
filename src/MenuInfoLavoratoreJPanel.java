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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Il menù che vien fuori quando premi un lavoratore dal menù principale
 * Qui ci sono tutte le info del lavoratore, la possibilità di aggiungere corsi ecc
 * 
 * --------------------------------------> DA FARE <-------------------------------------
 * 
 * 
 * @author Marco
 *
 */
public class MenuInfoLavoratoreJPanel extends JPanel{

	/**
	 * Indice del lavoratore dell'array dei lavoratori
	 */
	int index;
	GestoreCorsiJava1 gestoreCorsi;
	
	Lavoratore lavoratore;
	
	private Font fontDefault = new Font("Calibri", Font.PLAIN, 15);

	/**
	 * 
	 * @param gcj1 il gestore corsi java
	 * @param index indice del lavoratore
	 */
	public MenuInfoLavoratoreJPanel(GestoreCorsiJava1 gcj1, int index) {
		
		this.index = index;
		gestoreCorsi = gcj1;
		
		this.setLayout(new BorderLayout());
		
		lavoratore = gcj1.getArrayLavoratori().get(index);
		
		JPanel pnlMain = new JPanel(new GridLayout(2,1));
		JPanel pnlInfo = new JPanel(new GridLayout(3,2));
		JPanel pnlCorsi = new JPanel(new GridLayout(4,1));
		JPanel pnlIndietro = new JPanel(new GridLayout(1,2,10,10));
		
		JLabel lblCognome = new JLabel("Cognome: "+lavoratore.getCognome());
		JLabel lblNome = new JLabel("Nome: "+lavoratore.getNome());
		JLabel lblCodiceFis = new JLabel("Codice fiscale: "+lavoratore.getCodiceFiscale());
		JLabel lblIndirizzo = new JLabel("Indirizzo: "+lavoratore.getIndirizzo());
		JLabel lblQualifica = new JLabel("Qualifica: "+lavoratore.getQualifica());
		
		
		CustomJButton btnIndietro = new CustomJButton("Indietro");
		
		CorsiFormazionePnl pnlGenerale = new CorsiFormazionePnl(lavoratore,CorsiFormazionePnl.GENERALE);
		CorsiFormazionePnl pnlSpecifica = new CorsiFormazionePnl(lavoratore, CorsiFormazionePnl.SPECIFICA);
		CorsiFormazionePnl pnlPreposto = new CorsiFormazionePnl(lavoratore, CorsiFormazionePnl.PREPOSTO);
		CorsiFormazionePnl pnlQuinquiennale = new CorsiFormazionePnl(lavoratore, CorsiFormazionePnl.QUINQUIENNALE);
		
		btnIndietro.addActionListener(new GestioneIndietro());

		lblCodiceFis.addComponentListener(new FontAdj(fontDefault,2.5));
		lblCognome.addComponentListener(new FontAdj(fontDefault,2.5));
		lblIndirizzo.addComponentListener(new FontAdj(fontDefault,2.5));
		lblNome.addComponentListener(new FontAdj(fontDefault,2.5));
		lblQualifica.addComponentListener(new FontAdj(fontDefault,2.5));
		
		pnlInfo.add(lblCognome);
		pnlInfo.add(lblNome);
		pnlInfo.add(lblCodiceFis);
		pnlInfo.add(lblIndirizzo);
		pnlInfo.add(lblQualifica);
		
		pnlCorsi.add(pnlGenerale);
		pnlCorsi.add(pnlSpecifica);
		pnlCorsi.add(pnlPreposto);
		pnlCorsi.add(pnlQuinquiennale);
		
		pnlIndietro.add(btnIndietro);
		pnlIndietro.add(Box.createGlue());
		
		pnlMain.add(pnlInfo);
		pnlMain.add(pnlCorsi);
		
		this.add(pnlMain, BorderLayout.CENTER);
		this.add(pnlIndietro, BorderLayout.SOUTH);
		
	}
	
	public class GestioneIndietro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlMenuIniziale().reset();
			gestoreCorsi.getPnlMenuIniziale().getScrollPanePnl().aggiornaPanel();
			gestoreCorsi.getPnlDefault().add(gestoreCorsi.getPnlMenuIniziale());
			gestoreCorsi.revalidate();
			gestoreCorsi.repaint();
		}
		
	}
	
	
	
	/**
	 * Panel per ogni tab del menu dei corsi
	 */
	public class CorsiFormazionePnl extends JPanel implements MouseListener {

		public final static int GENERALE=0;
		public final static int SPECIFICA=1;
		public final static int PREPOSTO=2;
		public final static int QUINQUIENNALE=3;

		Color temaSfondo;
		Color temaMouseEntered;
		JLabel lbl;
		int tipologia;

		public CorsiFormazionePnl(Lavoratore lav, int tipologia1) {

			this.setLayout(new BorderLayout());
			
			String s ="";
			
			switch(tipologia1) {
			case GENERALE:
				s="Formazione Generale";
				break;
			case SPECIFICA:
				s="Formazione Specifica";
				break;
			case PREPOSTO:
				s="Formazione Preposto";
				break;
			case QUINQUIENNALE:
				s="Aggiornamenti Quinquiennale";
				break;
			default:
				s="Formazione Generale";
				tipologia1=0;
				break;
			}
			
			tipologia = tipologia1;
			
			lbl = new JLabel(s);

			lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			lbl.addComponentListener(new FontAdj(fontDefault,2));

			temaSfondo = Color.WHITE;
			temaMouseEntered = new Color(190,190,190);

			cambiaColore(temaSfondo, temaMouseEntered);

			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			this.setPreferredSize(new Dimension(100,80));
			this.addMouseListener(this);
			this.add(lbl, BorderLayout.CENTER);
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
			gestoreCorsi.getPnlDefault().add(new MenuInfoCorsiJPanel(gestoreCorsi, lavoratore, tipologia));
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




		
	}

	
}
