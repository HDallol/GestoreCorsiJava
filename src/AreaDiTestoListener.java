import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Questo mostra quel bel "ghost text" tipico delle barre di ricerca.
 * Da aggiungere ai JTextField tramite un getDocument()
 * @author Marco
 *
 */
public class AreaDiTestoListener implements DocumentListener {

		JLabel lblTestoFantasma;
		JTextField txtF;

		
		public AreaDiTestoListener(JTextField txtF, String testo, Font font) {
			this.txtF = txtF;
			this.lblTestoFantasma = new JLabel(testo);
			this.lblTestoFantasma.addComponentListener(new FontAdj(font,1.5));
			this.lblTestoFantasma.setForeground(Color.gray);
			txtF.setLayout(new BorderLayout());
			txtF.add(lblTestoFantasma);
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			lblTestoFantasma.setVisible(false);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
			if(txtF.getText().equals("")) 
				lblTestoFantasma.setVisible(true);
			else
				lblTestoFantasma.setVisible(false);
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
