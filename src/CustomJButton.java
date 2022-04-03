import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Un JButton custom da utilizzare ovunque. Perché quelli
 * di default sono orribili.
 * 
 * @author Marco
 *
 */

public class CustomJButton extends JButton implements ChangeListener, ComponentListener {

		Color colore = new Color(233,233,233);
		Color rollover = new Color(211,211,211);
		Color pressed = new Color(189,190,189);
		Color disabled = new Color(169,169,169);
		double divisore;

		Font fontDefault;
		
		public CustomJButton(String testo) {

			this.setText(testo);
			fontDefault = new Font("Calibri",Font.BOLD, (int) (this.getHeight()/1.5));

			divisore = 1.5;

			this.setBackground(colore);
			this.setFont(fontDefault);
			this.setFocusable(false);
			this.setBorder(BorderFactory.createLineBorder(pressed, 1));
			this.setContentAreaFilled(false);
			this.setOpaque(true);
			this.setFocusPainted(false);
			this.setEnabled(true);
			this.addChangeListener(this);
			this.addComponentListener(this);
			this.setMinimumSize(new Dimension(80,50));
			this.setPreferredSize(new Dimension(80,70));
		}


		public CustomJButton(String testo, double divisore) {

			this.setText(testo);
			fontDefault = new Font("Calibri",Font.BOLD, (int) (this.getHeight()/1.5));

			this.divisore = divisore;

			this.setBackground(colore);
			this.setFont(fontDefault);
			this.setFocusable(false);
			this.setBorder(BorderFactory.createLineBorder(pressed, 1));
			this.setContentAreaFilled(false);
			this.setOpaque(true);
			this.setFocusPainted(false);
			this.setEnabled(true);
			this.addChangeListener(this);
			this.addComponentListener(this);
			this.setMinimumSize(new Dimension(80,50));
			this.setPreferredSize(new Dimension(80,70));
		}
		
		public CustomJButton(String testo, double divisore, Font f) {

			this.setText(testo);
			fontDefault = f;

			this.divisore = divisore;

			this.setBackground(colore);
			this.setFont(fontDefault);
			this.setFocusable(false);
			this.setBorder(BorderFactory.createLineBorder(pressed, 1));
			this.setContentAreaFilled(false);
			this.setOpaque(true);
			this.setFocusPainted(false);
			this.setEnabled(true);
			this.addChangeListener(this);
			this.addComponentListener(this);
			this.setMinimumSize(new Dimension(80,50));
			this.setPreferredSize(new Dimension(80,70));
		}


		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub

			if(this.getModel().isPressed()) 
				this.setBackground(rollover);
			else if(this.getModel().isRollover())
				this.setBackground(pressed);
			else if(!this.isEnabled()) 
				this.setBackground(disabled);
			else 
				this.setBackground(colore);

		}

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub

			int size = ((int) (this.getHeight()/divisore));
			if(size>65) {
				size=65;
			}
			fontDefault = fontDefault.deriveFont((float) size);
			this.setFont(fontDefault);
			
			
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
