import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Il menù che vien fuori quando premi un lavoratore dal menù principale
 * Qui ci sono tutte le info del lavoratore, la possibilità di aggiungere corsi ecc
 * 
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
	private Color temaBackground = new Color(210,210,210);
	private Color temaFont = Color.BLACK;
	private Font fontDefault = new Font("Calibri", Font.BOLD, 15);

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
		JPanel pnlIndietro = new JPanel(new GridLayout(1,3,10,10));
		JPanel pnlOpzioni = new JPanel(new GridLayout(1,0,10,10));

		JLabel lblCognome = new JLabel("Cognome: "+lavoratore.getCognome());
		JLabel lblNome = new JLabel("Nome: "+lavoratore.getNome());
		JLabel lblCodiceFis = new JLabel("Codice fiscale: "+lavoratore.getCodiceFiscale());
		JLabel lblIndirizzo = new JLabel("Indirizzo: "+lavoratore.getIndirizzo());
		JLabel lblQualifica = new JLabel("Qualifica: "+lavoratore.getQualifica());
		//JLabel lblDaAggiornare = new JLabel("<html><i>Da Aggiornare: </i></html>");

		CustomJButton btnIndietro = new CustomJButton("Indietro",2);
		CustomJButton btnAggiornamento = new CustomJButton("",2.5);
		CustomJButton btnModifica = new CustomJButton("Modifica",2.5);
		CustomJButton btnSalvaPdf = new CustomJButton("Salva (PDF)", 2.5);
		CustomJButton btnSalvaTxt = new CustomJButton("Salva (txt)", 2.5);


		if(lavoratore.getDaAggiornare()==true) {
			btnAggiornamento.setText("Aggiornamento: Sì");
			btnAggiornamento.setName("1");
		}
		else {
			btnAggiornamento.setText("Aggiornamento: No");
			btnAggiornamento.setName("0");
		}

		CorsiFormazionePnl pnlGenerale = new CorsiFormazionePnl(lavoratore,CorsiFormazionePnl.GENERALE);
		CorsiFormazionePnl pnlSpecifica = new CorsiFormazionePnl(lavoratore, CorsiFormazionePnl.SPECIFICA);
		CorsiFormazionePnl pnlPreposto = new CorsiFormazionePnl(lavoratore, CorsiFormazionePnl.PREPOSTO);
		CorsiFormazionePnl pnlQuinquiennale = new CorsiFormazionePnl(lavoratore, CorsiFormazionePnl.QUINQUIENNALE);

		btnIndietro.addActionListener(new GestioneIndietro());
		btnAggiornamento.addActionListener(new GestioneDaAggiornare());
		btnModifica.addActionListener(new GestioneModifica());
		btnSalvaPdf.addActionListener(new GestioneSalvaPdf());
		btnSalvaTxt.addActionListener(new GestioneSalvaTxt());

		lblCodiceFis.addComponentListener(new FontAdj(fontDefault,2.5));
		lblCognome.addComponentListener(new FontAdj(fontDefault,2.5));
		lblIndirizzo.addComponentListener(new FontAdj(fontDefault,2.5));
		lblNome.addComponentListener(new FontAdj(fontDefault,2.5));
		lblQualifica.addComponentListener(new FontAdj(fontDefault,2.5));

		pnlInfo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		pnlIndietro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlOpzioni.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

		pnlCorsi.setOpaque(false);
		pnlInfo.setOpaque(false);
		pnlOpzioni.setOpaque(false);
		pnlIndietro.setOpaque(false);
		pnlMain.setOpaque(false);

		this.setBackground(temaBackground);

		pnlOpzioni.add(btnAggiornamento);
		pnlOpzioni.add(btnModifica);

		pnlInfo.add(lblCognome);
		pnlInfo.add(lblNome);
		pnlInfo.add(lblCodiceFis);
		pnlInfo.add(lblIndirizzo);
		pnlInfo.add(lblQualifica);
		pnlInfo.add(pnlOpzioni);

		pnlCorsi.add(pnlGenerale);
		pnlCorsi.add(pnlSpecifica);
		pnlCorsi.add(pnlPreposto);
		pnlCorsi.add(pnlQuinquiennale);

		pnlIndietro.add(btnIndietro);
		pnlIndietro.add(btnSalvaPdf);
		pnlIndietro.add(btnSalvaTxt);

		pnlMain.add(pnlInfo);
		pnlMain.add(pnlCorsi);

		this.add(pnlMain, BorderLayout.CENTER);
		this.add(pnlIndietro, BorderLayout.SOUTH);

	}

	protected void salvaPdf() {

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter fnFilter = new FileNameExtensionFilter(".pdf files", "pdf");
		fc.setFileFilter(fnFilter);
		fc.setAcceptAllFileFilterUsed(false);


		int val = fc.showSaveDialog(null);

		if(val==JFileChooser.APPROVE_OPTION) {

			File filePdf = fc.getSelectedFile();
			String path = filePdf.getAbsolutePath();
			String name = filePdf.getAbsolutePath();;


			if(filePdf.getAbsolutePath().contains(".pdf")) {
				name = filePdf.getAbsolutePath().substring(0, filePdf.getAbsolutePath().length()-4);
			}

			if(!name.contains(".pdf")) {
				filePdf = new File(name+".pdf");
			}


			int x = 0;
			System.out.println("Exists: "+filePdf.exists());
			while(filePdf.exists()) {
				x++;
				filePdf = new File(name +" ("+x+").pdf");
				System.out.println("x: "+x);
			}

			if(x>0) {
				name += " ("+x+")";
			}

			System.out.println("Percorso: "+name);


			int numeroPagina = 1;
			try {

				PDDocument doc = new PDDocument();

				doc.getDocumentInformation().setAuthor("Gestore Corsi Java");
				doc.getDocumentInformation().setCreationDate(Calendar.getInstance());

				doc.addPage(new PDPage());

				PDPageContentStream pcs = new PDPageContentStream(doc, doc.getPage(0));
				PDImageXObject pdImage = PDImageXObject.createFromFile("SplashScreen.png",doc);

				pcs.drawImage(pdImage, 30, 690, pdImage.getWidth()/2, pdImage.getHeight()/2);

				pcs.moveTo(8, 670);
				pcs.lineTo(605, 670);
				pcs.stroke();

				pcs.beginText();

				pcs.newLineAtOffset(570, 50);
				pcs.setFont(PDType1Font.HELVETICA, 15);
				pcs.showText(""+numeroPagina);
				numeroPagina++;
				pcs.endText();

				pcs.beginText();
				pcs.setFont(PDType1Font.HELVETICA_BOLD, 30);
				pcs.newLineAtOffset(30, 600);

				pcs.setLeading(80);
				pcs.showText("INFORMAZIONI PRINCIPALI");
				pcs.newLine();
				pcs.setLeading(60);
				pcs.setFont(PDType1Font.HELVETICA, 28);

				pcs.showText("Cognome: "+lavoratore.getCognome());
				pcs.newLine();
				pcs.showText("Nome: "+lavoratore.getNome());
				pcs.newLine();
				pcs.showText("Indirizzo: "+lavoratore.getIndirizzo());
				pcs.newLine();
				pcs.showText("Qualifica: "+lavoratore.getQualifica());

				pcs.endText();
				pcs.close();


				for(int k=0;k<4;k++) {

					int nCorsi = lavoratore.getNumeroCorsi(k);

					System.out.println("la k: "+k);

					if(nCorsi>0) {

						ArrayList<CorsoDiFormazione> arrCorsi = lavoratore.getCorsi(k);

						doc.addPage(new PDPage());

						try {
							pcs.endText();
						} catch(IllegalStateException e) {
							System.out.println("Non puoi chiudere una stream prima di aprirla, lo so");
						}

						pcs.close();

						pcs = new PDPageContentStream(doc, doc.getPage(doc.getNumberOfPages()-1));				
						pcs.drawImage(pdImage, 30, 690, pdImage.getWidth()/2, pdImage.getHeight()/2);

						pcs.moveTo(8, 670);
						pcs.lineTo(605, 670);
						pcs.stroke();

						pcs.beginText();

						pcs.newLineAtOffset(570, 50);
						pcs.setFont(PDType1Font.HELVETICA, 15);
						pcs.showText(""+numeroPagina);
						numeroPagina++;
						pcs.endText();

						pcs.beginText();
						pcs.setFont(PDType1Font.HELVETICA_BOLD, 25);
						pcs.newLineAtOffset(30, 600);

						pcs.setLeading(30);

						if(k==0)
							pcs.showText("CORSI DI FORMAZIONE GENERALE");
						else if(k==1)
							pcs.showText("CORSI DI FORMAZIONE SPECIFICA");
						else if(k==2)
							pcs.showText("CORSI DI FORMAZIONE PREPOSTO");
						else if(k==3)
							pcs.showText("AGGIORNAMENTI QUINQUIENNALE");

						pcs.setFont(PDType1Font.HELVETICA, 20);

						pcs.setLeading(50);

						int puntatore=550;


						for(int i=0;i<nCorsi;i++) {

							pcs.newLine();
							puntatore -= 50;
							pcs.showText("Nome corso: "+arrCorsi.get(i).getNomeCorso());
							pcs.newLine();
							puntatore -= 50;
							pcs.showText("Numero ore: "+arrCorsi.get(i).getNOreComplessive());
							pcs.newLine();
							puntatore -= 50;
							pcs.showText("Scadenza in anni: "+arrCorsi.get(i).getScadenzaAnni());
							pcs.newLine();
							puntatore -= 50;
							pcs.showText("Stato: "+arrCorsi.get(i).getStringaStato());
							pcs.newLine();
							puntatore -= 50;
							pcs.showText("Date: ");

							for(int j=0;j<arrCorsi.get(i).getData().size();j++) {
								pcs.newLine();
								puntatore -= 50;
								pcs.showText("- "+ arrCorsi.get(i).getData().get(j)+" Ore: "+arrCorsi.get(i).getnOre().get(j));

								if(puntatore<=100 && j!=(arrCorsi.get(i).getData().size()-1)) {

									pcs.endText();
									pcs.close();

									doc.addPage(new PDPage());

									pcs = new PDPageContentStream(doc, doc.getPage(doc.getNumberOfPages()-1));

									pcs.drawImage(pdImage, 30, 690, pdImage.getWidth()/2, pdImage.getHeight()/2);

									pcs.moveTo(8, 670);
									pcs.lineTo(605, 670);
									pcs.stroke();

									pcs.beginText();

									pcs.newLineAtOffset(570, 50);
									pcs.setFont(PDType1Font.HELVETICA, 15);
									pcs.showText(""+numeroPagina);
									numeroPagina++;
									pcs.endText();

									pcs.beginText();

									pcs.setFont(PDType1Font.HELVETICA, 20);
									pcs.newLineAtOffset(30, 600);
									pcs.setLeading(50);
									puntatore = 600;
								}
							}

							if(puntatore<=250 && i!= (nCorsi-1)) {

								pcs.endText();
								pcs.close();

								doc.addPage(new PDPage());

								pcs = new PDPageContentStream(doc, doc.getPage(doc.getNumberOfPages()-1));

								pcs.drawImage(pdImage, 30, 690, pdImage.getWidth()/2, pdImage.getHeight()/2);

								pcs.moveTo(8, 670);
								pcs.lineTo(605, 670);
								pcs.stroke();

								pcs.beginText();

								pcs.newLineAtOffset(570, 50);
								pcs.setFont(PDType1Font.HELVETICA, 15);
								pcs.showText(""+numeroPagina);
								numeroPagina++;
								pcs.endText();

								pcs.beginText();

								pcs.setFont(PDType1Font.HELVETICA, 20);
								pcs.newLineAtOffset(30, 600);
								pcs.setLeading(50);
								puntatore = 600;
							}

						}



					}
				}

				try {
					pcs.endText();
				} catch(IllegalStateException e) {
					System.out.println("Ancora una volta non puoi chiudere prima di aprire ma vabbe dai");
				}


				pcs.close();

				doc.save(filePdf);
				doc.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block

				System.out.println("Errore "+e.getMessage());
			} 

		}

	}


	protected void salvaTxt() {
		
		
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter fnFilter = new FileNameExtensionFilter(".txt files", "txt");
		fc.setFileFilter(fnFilter);
		fc.setAcceptAllFileFilterUsed(false);


		int val = fc.showSaveDialog(null);

		if(val==JFileChooser.APPROVE_OPTION) {

			File fileTxt = fc.getSelectedFile();
			String path = fileTxt.getAbsolutePath();
			String name = fileTxt.getAbsolutePath();;


			if(fileTxt.getAbsolutePath().contains(".txt")) {
				name = fileTxt.getAbsolutePath().substring(0, fileTxt.getAbsolutePath().length()-4);
			}

			if(!name.contains(".txt")) {
				fileTxt = new File(name+".txt");
			}


			int x = 0;
			System.out.println("Exists: "+fileTxt.exists());
			while(fileTxt.exists()) {
				x++;
				fileTxt = new File(name +" ("+x+").txt");
				System.out.println("x: "+x);
			}

			if(x>0) {
				name += " ("+x+")";
			}

			System.out.println("Percorso: "+name);
			
			try {
				FileOutputStream fos = new FileOutputStream(fileTxt);
				PrintWriter pw = new PrintWriter(fos);
				
				pw.println("Cognome: "+lavoratore.getCognome());
				pw.println("Nome: "+lavoratore.getNome());
				pw.println("Codice fiscale: "+lavoratore.getCodiceFiscale());
				pw.println("Indirizzo: "+lavoratore.getIndirizzo());
				pw.println("Qualifica: "+lavoratore.getQualifica());
				pw.println();
				
				for(int i=0;i<4;i++) {
					
					int nCorsi = lavoratore.getNumeroCorsi(i);

					if(nCorsi>0) {
						
						if(i==0)
							pw.println("CORSI DI FORMAZIONE GENERALE");
						else if(i==1)
							pw.println("CORSI DI FORMAZIONE SPECIFICA");
						else if(i==2)
							pw.println("CORSI DI FORMAZIONE PREPOSTO");
						else if(i==3)
							pw.println("AGGIORNAMENTI QUINQUIENNALE");
						
						pw.println();
						
						for(int j=0;j<lavoratore.getCorsi(i).size();j++) {
							
							pw.println("Nome corso: "+lavoratore.getCorsi(i).get(j).getNomeCorso());
							pw.println("Numero complessivo di ore: "+lavoratore.getCorsi(i).get(j).getNOreComplessive());
							pw.println("Durata (in anni): "+lavoratore.getCorsi(i).get(j).getScadenzaAnni());
							pw.println("Stato: "+lavoratore.getCorsi(i).get(j).getStringaStato());
							pw.println("Date:");
							
							for(int k=0;k<lavoratore.getCorsi(i).get(j).getData().size();k++) {
								pw.println(" - "+lavoratore.getCorsi(i).get(j).getData().get(k)+" Ore: "+lavoratore.getCorsi(i).get(j).getnOre().get(k));
							}
							
							pw.println();
						}
						
					}
					
				}
				
				pw.flush();
				pw.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public class GestioneSalvaTxt implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			salvaTxt();
		}

	}

	public class GestioneSalvaPdf implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			salvaPdf();

		}

	}

	public class GestioneModifica implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gestoreCorsi.getPnlDefault().removeAll();
			gestoreCorsi.getPnlDefault().add(new MenuAggiungiLavoratoreJPanel(gestoreCorsi,lavoratore));
			gestoreCorsi.getPnlDefault().revalidate();
			gestoreCorsi.getPnlDefault().repaint();
		}

	}

	public class GestioneDaAggiornare implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			CustomJButton btn = (CustomJButton) e.getSource();

			if(btn.getName().equals("1")) {
				btn.setName("0");
				btn.setText("Aggiornamento: No");
				lavoratore.setDaAggiornare(false);
			}
			else {
				btn.setName("1");
				btn.setText("Aggiornamento: Sì");
				lavoratore.setDaAggiornare(true);
			}

			gestoreCorsi.salva();

		}

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
				s="<html>Formazione Generale - <i>"+lav.getNumeroCorsi(tipologia1)+" Corsi</html>";
				break;
			case SPECIFICA:
				s="<html>Formazione Specifica - <i>"+lav.getNumeroCorsi(tipologia1)+" Corsi</html>";
				break;
			case PREPOSTO:
				s="<html>Formazione Preposto - <i>"+lav.getNumeroCorsi(tipologia1)+" Corsi</html>";
				break;
			case QUINQUIENNALE:
				s="<html>Aggiornamenti Quinquiennale - <i>"+lav.getNumeroCorsi(tipologia1)+" Corsi</html>";
				break;
			default:
				s="<html>Formazione Generale - <i>"+lav.getNumeroCorsi(tipologia1)+" Corsi</html>";
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
