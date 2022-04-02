import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 * Menù iniziale all'apertura del programma.
 * 
 * @author Marco
 *
 */
public class MenuInizialeJPanel extends JPanel{


	private JPanel pnlRicerca;
	private JPanel pnlFiltri;
	private JPanel pnlAggiungi;

	/**
	 * Una raccolta dei filtri
	 */
	private String[] arrayFiltri = {"Prova", "Prova1" , "Banana", "!!!"};
	
	/**
	 * Una raccolta di label, in modo da poter poi cambiarne il colore facilmente
	 */
	ArrayList<JLabel> arrayJLabel;
	
	/**
	 * Temi di default
	 */
	private Color temaBackground = new Color(210,210,210);
	private Color temaFont = Color.BLACK;
	
	public MenuInizialeJPanel() {
		super(new BorderLayout());

		pnlRicerca = new JPanel(new BorderLayout());
		pnlFiltri = new JPanel();
		pnlAggiungi = new JPanel(new GridLayout(1,1));

		pnlFiltri.setLayout(new BoxLayout(pnlFiltri, BoxLayout.Y_AXIS));

		arrayJLabel = new ArrayList<JLabel>();
		
		//pnlFiltri.setBackground(Color.RED);

		filtri();
		
		cambiaColore(temaBackground, temaFont);
		
		this.add(pnlRicerca, BorderLayout.NORTH);
		this.add(pnlFiltri, BorderLayout.WEST);
		this.add(pnlAggiungi, BorderLayout.CENTER);
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
	 * Aggiunge tutti i filtri dell'arrayFiltri nel pannello
	 */
	private void filtri() {
		
	
		for(int i=0;i<arrayFiltri.length;i++) {
			aggiungiFiltro(i, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JCheckBox cb = (JCheckBox) e.getSource();
					
					if(cb.isSelected())
						System.out.println("Hello "+cb.getName());
				}
				
			});
		}
	}

	/**
	 * Crea e aggiunge un filtro nel pannello 'pnlFiltri'
	 * 
	 * @param indiceFiltro l'indice dell'array arrayFiltri
	 * @param azioneFiltro l'ActionListener da aggiungere al filtro
	 */
	private void aggiungiFiltro(int indiceFiltro, ActionListener azioneFiltro) {

		//for(int i=0; i<arrayFiltri.length;i++) {
		JCheckBox cbFiltro1 = new JCheckBox();
		cbFiltro1.setName(""+indiceFiltro);

		JPanel pnl1 = new JPanel(new BorderLayout());
		JLabel lbl1 = new JLabel(arrayFiltri[indiceFiltro]);
		
		arrayJLabel.add(lbl1);
		
		cbFiltro1.addActionListener(azioneFiltro);

		cbFiltro1.setOpaque(false);

		pnl1.setMaximumSize(new Dimension(200,40));
		pnl1.setOpaque(false);
		pnl1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		pnl1.add(cbFiltro1, BorderLayout.WEST);
		pnl1.add(lbl1, BorderLayout.CENTER);

		pnlFiltri.add(pnl1);

	}
	

	

}
