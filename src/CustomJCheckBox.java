import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JCheckBox;
/**
 * Questo checkbox custom va a disegnarsi sopra al checkbox originale, cambiando di stato a sua volta
 * 
 * 
 * @author Stefano
 *
 */
public class CustomJCheckBox extends JCheckBox {

	/**
	 * Arrotondamento degli angoli del checkbox
	 * */
	private int rounding = 4;

	
	/**
	 * Colore del bordo del checkbox
	 * */
	private Color borderColor = Color.GRAY;
	
	
	/** 
	 * Colore del checkbox quando e' selezionato
	 * */
	private Color selectedCheckColor = new Color(105, 105, 105);
	
	
	/**
	 * unselectedCheckColor Colore del checkbox quando NON e' selezionato
	 * */
	private Color unselectedCheckColor = Color.WHITE;

	
	public CustomJCheckBox() {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setOpaque(false);
		this.setBackground(selectedCheckColor);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // fa l'anti-aliasing
		int alignment = (this.getHeight() - 16) / 2;

		
		if (isSelected()) {
			if (isEnabled()) {
				g2.setColor(getBackground());
			} else {
				g2.setColor(borderColor);
			}

			g2.fillRoundRect(1, alignment, 16, 16, rounding, rounding);
			//			da scommentare per avere il check come pallina
			//			g2.setColor(unselectedCheckColor);
			//			g2.fillOval(4, 15, 10, 10);

		} else {
            g2.setColor(borderColor);
            g2.fillRoundRect(1, alignment, 16, 16, rounding, rounding);
            g2.setColor(unselectedCheckColor);
            g2.fillRoundRect(2, alignment + 1, 14, 14, rounding, rounding);
		}

		g2.dispose(); // serve a lasciare le risorse che il pc sta utilizzando per il graphic (al termine del calcolo iniziale all'avvio del programma)
	}
}