import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Un "aggiustatore" di font. Da aggiungere con un addComponentListener.
 * Aggiusta la dimensione del font in base alla dimensione della finestra.
 * O almeno ci prova.
 * 
 * @author Marco
 *
 */

public class FontAdj implements ComponentListener{

	private Font f;
	private double div;
	private float minimo;
	private float massimo;

	/**
	 * 
	 * @param f il font che stai usando
	 * @param div il divisore che vuoi usare: più è alto il numero, più piccolo sarà il font
	 */
	public FontAdj(Font f, double div) {
		this.f = f;
		this.div = div;
		minimo = 10;
		massimo = 60;
	}

	/**
	 * 
	 * @param f il font che stai usando
	 * @param div il divisore che vuoi usare: più è alto il numero, più piccolo sarà il font
	 * @param max grandezza massima del font
	 * @param min grandezza minima del font
	 */
	public FontAdj(Font f, double div, int max, int min) {
		this.f = f;
		this.div = div;
		minimo = min;
		massimo = max;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

		//e.getComponent().setFont(new Font(f.getFamily(), f.getStyle(), (int) (e.getComponent().getHeight()/div)));

		float dimensione = (float) (e.getComponent().getHeight()/div);

		if(dimensione>massimo)
			dimensione=massimo;
		else if(dimensione<minimo)
			dimensione=minimo;

		e.getComponent().setFont(f.deriveFont(dimensione));

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

}