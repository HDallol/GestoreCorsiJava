/**
 * Autori: Marco Dall'O' Polveni, Stefano Pacchin
 * 
 * Versione: Pre-Alpha 0.0.1
 *  
 *  Un programma che permette la gestione dei corsi di formazione
 *  di tutti i dipendenti di un'azienda
 *  
 *  
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class GestoreCorsiJava1 extends JFrame implements WindowListener{

	private Dimension dimensioniSchermo = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension dimensioniMinime = new Dimension(200,200);
	private Dimension dimensioniFinestra = new Dimension(dimensioniSchermo.width/2, dimensioniSchermo.height/2);

	public static final Font fontDefault = new Font("Calibri", Font.PLAIN, 15);

	private File fileSalvataggio;

	/**
	 * Panel di default su cui aggiungere tutti gli altri panel
	 */
	private JPanel pnlDefault;

	private MenuInizialeJPanel pnlMenuIniziale;
	private MenuAggiungiLavoratoreJPanel pnlMenuAggiungiLavoratore;

	private ImageIcon logo = new ImageIcon("logo.png");

	/**
	 * E qui verranno salvati tutti i lavoratori ecc
	 */
	private ArrayList<Lavoratore> arrayLavoratori;

	public GestoreCorsiJava1(JWindow splashScreen) {
		super("Gestore Corsi di Formazione - Alpha 0.2");

		this.setIconImage(logo.getImage());

		fileSalvataggio = new File("gestoreCorsiJava.gcj");

		arrayLavoratori = new ArrayList<Lavoratore>();

		leggi();


		pnlDefault = new JPanel(new GridLayout(1,1));
		pnlMenuIniziale = new MenuInizialeJPanel(this);
		pnlMenuAggiungiLavoratore = new MenuAggiungiLavoratoreJPanel(this);

		pnlDefault.add(pnlMenuIniziale);
		this.getContentPane().add(pnlDefault);

		this.addWindowListener(this);
		this.setSize(dimensioniFinestra);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		this.setLocation((dimensioniSchermo.width-dimensioniFinestra.width)/2, (dimensioniSchermo.height-dimensioniFinestra.height)/2);
		this.setMinimumSize(dimensioniMinime);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		splashScreen.setVisible(false);
		splashScreen.dispose();
		this.setVisible(true);



		this.toFront();
		this.requestFocus();
	}


	public ArrayList<Lavoratore> getArrayLavoratori() {
		return arrayLavoratori;
	}

	public JPanel getPnlDefault() {
		return pnlDefault;
	}

	public MenuInizialeJPanel getPnlMenuIniziale() {
		return pnlMenuIniziale;
	}

	public MenuAggiungiLavoratoreJPanel getMenuAggiungiLavoratore() {
		return pnlMenuAggiungiLavoratore;
	}

	/**
	 * Salvataggio su file
	 */
	public void salva() {

		try {
			FileOutputStream fos = new FileOutputStream(fileSalvataggio,false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(arrayLavoratori);

			oos.flush();
			oos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}

	/**
	 * Lettura da file
	 */
	public void leggi() {

		try {
			FileInputStream fis = new FileInputStream(fileSalvataggio);
			ObjectInputStream ois = new ObjectInputStream(fis);

			arrayLavoratori = (ArrayList<Lavoratore>) ois.readObject();

			ois.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch(ClassCastException e) {

		}

		System.out.println("Dimensione attuale array: "+arrayLavoratori.size());

	}


	protected synchronized void salvaPdf() {

		Lavoratore lavoratore;
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);

		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int val = fc.showSaveDialog(null);

		if(val==JFileChooser.APPROVE_OPTION) {

			String path = fc.getSelectedFile().getAbsolutePath();

			for(int z=0;z<arrayLavoratori.size();z++) {

				File filePdf = fc.getSelectedFile();
				String name = filePdf.getAbsolutePath();;

				lavoratore = arrayLavoratori.get(z);
				filePdf = new File(name+'\\'+lavoratore.getCognome()+" - "+lavoratore.getCodiceFiscale());

				name = filePdf.getAbsolutePath();
				
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

	}

	protected synchronized void salvaTxt() {

		Lavoratore lavoratore;
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);

		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int val = fc.showSaveDialog(null);

		if(val==JFileChooser.APPROVE_OPTION) {

			String path = fc.getSelectedFile().getAbsolutePath();

			for(int z=0;z<arrayLavoratori.size();z++) {

				File fileTxt = fc.getSelectedFile();
				String name = fileTxt.getAbsolutePath();;

				lavoratore = arrayLavoratori.get(z);
				fileTxt = new File(name+'\\'+lavoratore.getCognome()+" - "+lavoratore.getCodiceFiscale());

				name = fileTxt.getAbsolutePath();
				
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
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

		int x = JOptionPane.showConfirmDialog(null, "Vuoi davvero uscire?", "Uscita in corso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if(x==0)
			System.exit(0);

	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	public class SalvaPdfThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			salvaPdf();
		}

	}
	
	public class SalvaTxtThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			salvaTxt();
		}
		
	}



}
