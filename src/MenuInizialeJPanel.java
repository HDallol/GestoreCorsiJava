import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.ArrayList;

/**
 * Menù iniziale all'apertura del programma.
 * 
 * @author Marco
 *
 */
public class MenuInizialeJPanel extends JPanel{

	private Font fontDefault = new Font("Calibri", Font.PLAIN, 15);

	private JPanel pnlRicerca;
	private JPanel pnlFiltri;
	private JPanel pnlAggiungi;

	private JTextField txtBarraRicerca;
	private CustomJButton btnAggiungi;
	private JLabel lblTextoIniziale;
	
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
	
	private GestoreCorsiJava1 gestoreCorsi;
	
	public MenuInizialeJPanel(GestoreCorsiJava1 gcj1) {
		super(new BorderLayout());

		gestoreCorsi = gcj1;		//link al GestoreCorsiJava1: da qui posso recuperare tutti i jpanel custom e l'array di lavoratore
		
		pnlRicerca = new JPanel(new BorderLayout());
		pnlFiltri = new JPanel();
		pnlAggiungi = new JPanel(new GridLayout(1,1));

		txtBarraRicerca = new JTextField();
		btnAggiungi = new CustomJButton("+",1.5,fontDefault);
		lblTextoIniziale = new JLabel("Premi + per aggiungere");
		
		pnlFiltri.setLayout(new BoxLayout(pnlFiltri, BoxLayout.Y_AXIS));

		arrayJLabel = new ArrayList<JLabel>();
	
		txtBarraRicerca.setBorder(BorderFactory.createLineBorder(temaFont, 1));
		txtBarraRicerca.setFont(fontDefault);
		lblTextoIniziale.setFont(fontDefault.deriveFont(50.0f));
		lblTextoIniziale.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextoIniziale.setVerticalAlignment(SwingConstants.CENTER);
		btnAggiungi.setPreferredSize(new Dimension(40,40));		//La preferred Size del bottone +, da cambiare nel caso
		
		txtBarraRicerca.getDocument().addDocumentListener(new AreaDiTestoListener(txtBarraRicerca, "Cerca...", fontDefault));
		txtBarraRicerca.addComponentListener(new FontAdj(fontDefault, 1.5));
		lblTextoIniziale.addComponentListener(new FontAdj(fontDefault, 8, 60,30));
		
		filtri();
		
		pnlRicerca.add(txtBarraRicerca, BorderLayout.CENTER);
		pnlRicerca.add(btnAggiungi, BorderLayout.EAST);
		pnlRicerca.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlFiltri.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, temaBackground.darker().darker()));
		
		pnlAggiungi.add(lblTextoIniziale);
		
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
					CustomJCheckBox cb = (CustomJCheckBox) e.getSource();
					
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

	}
	

	
	

}
