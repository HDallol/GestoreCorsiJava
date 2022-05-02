import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Per raccogliere le informazioni di ogni corso. Boh, non so se vada bene così o
 * se ho dimenticato qualcosa
 * @author Marco
 *
 */
public class CorsoDiFormazione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6647037689245289891L;

	public final static int VALIDO = 0;
	public final static int IN_SCADENZA = 1;
	public final static int SCADUTO = 2;
	public final static int DA_COMPLETARE = 3;

	public final static int GENERALE=0;
	public final static int SPECIFICA=1;
	public final static int PREPOSTO=2;
	public final static int QUINQUIENNALE=3;

	private String nomeCorso;
	/**
	 * Formattazione: GG/MM/AAAA
	 */
	private ArrayList<String> data;
	private ArrayList<Integer> nOre;

	private int scadenzaAnni;
	private int stato;
	/**
	 * GENERALE, SPECIFICA, PREPOSTO o QUINQUIENNALE
	 */
	private int tipologia;

	private boolean completato;


	public CorsoDiFormazione() {
		nomeCorso = null;
		data = new ArrayList<String>();
		nOre = new ArrayList<Integer>();
		stato = VALIDO;
		this.tipologia=0;
		completato = false;
	}

	/**
	 * 
	 * @param nomeCorso il nome del corso di formazione
	 * @param data la data in cui il corso è stato fatto FORMATTAZIONE GG/MM/AA
	 * @param nOre il numero di ore che certifica il corso
	 * @param scadenzaAnni il numero di anni che devono passare prima che il corso scadi
	 */
	public CorsoDiFormazione(String nomeCorso, int scadenzaAnni, int tipologia, boolean completato) {

		this.nomeCorso = nomeCorso;
		this.data = new ArrayList<String>();
		//		if(nOre>1)
		//			this.nOre = nOre;
		//		else
		//			this.nOre = 1;

		this.nOre = new ArrayList<Integer>();

		this.tipologia = tipologia;
		this.scadenzaAnni = scadenzaAnni;

		if(this.tipologia>3 || this.tipologia<0)
			this.tipologia = 0;

		this.completato = completato;

		aggiornaStato();

	}

	public void setTipologia(int tipologia) {
		if(tipologia>=0 && tipologia<=4) {
			this.tipologia = tipologia;
		}
		else
			this.tipologia = 0;
	}

	public int getTipologia() {
		return tipologia;
	}

	/**
	 * @return the nomeCorso
	 */
	public String getNomeCorso() {
		return nomeCorso;
	}

	/**
	 * @return the data
	 */
	public ArrayList<String> getData() {
		return data;
	}

	/**
	 * @return the nOre
	 */
	public ArrayList<Integer> getnOre() {
		return nOre;
	}

	/**
	 * @return the scadenzaAnni
	 */
	public int getScadenzaAnni() {
		return scadenzaAnni;
	}

	/**
	 * @return the stato
	 */
	public int getStato() {
		return stato;
	}

	/**
	 * @param nomeCorso the nomeCorso to set
	 */
	public void setNomeCorso(String nomeCorso) {
		this.nomeCorso = nomeCorso;
	}




	/**
	 * @param scadenzaAnni the scadenzaAnni to set
	 */
	public void setScadenzaAnni(int scadenzaAnni) {
		this.scadenzaAnni = scadenzaAnni;

		aggiornaStato();
	}


	public void aggiungiData(String data, int nOre) {
		this.data.add(data);
		this.nOre.add(nOre);
		aggiornaStato();
	}

	public void rimuoviData(String data) {
		int index = this.data.indexOf(data);
		this.data.remove(index);
		this.nOre.remove(index);
		aggiornaStato();
	}

	public void setCompletato(boolean completato) {
		this.completato = completato;
		aggiornaStato();
	}

	public boolean getCompletato() {
		return completato;
	}

	public int getNOreComplessive() {
		int x=0;

		for(int i=0;i<nOre.size();i++) {
			x+=nOre.get(i);
		}

		return x;
	}

	public void aggiornaStato() {

		if(completato) {
			try {
				String dataSpl[] = data.get(data.size()-1).split("/");

				int giorno = Integer.parseInt(dataSpl[0]);
				int mese = Integer.parseInt(dataSpl[1]);
				int anno = Integer.parseInt(dataSpl[2]);

				int giornoAttuale = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				int meseAttuale = Calendar.getInstance().get(Calendar.MONTH);
				int annoAttuale = Calendar.getInstance().get(Calendar.YEAR);

				GregorianCalendar g1 = new GregorianCalendar(anno,mese,giorno);
				GregorianCalendar g2 = new GregorianCalendar(annoAttuale,meseAttuale,giornoAttuale);

				long tempo = g2.getTimeInMillis()-g1.getTimeInMillis();

				long giorniDiff = ((((tempo/1000)/60)/60)/24);	//differenza in giorni

				if(giorniDiff > scadenzaAnni*365) {
					stato=SCADUTO;
				}
				else if(scadenzaAnni*365-giorniDiff < 100) {	//SE LA DIFF IN GIORNI è <100 allora passa a IN SCADENZA
					stato=IN_SCADENZA;
				}
				else {
					stato=VALIDO;
				}


			} catch(ArrayIndexOutOfBoundsException e) {
				stato = SCADUTO;
			}
			catch(NumberFormatException e) {
				stato = SCADUTO;
			}
			catch(Exception e) {
				stato=SCADUTO;
			}

		}
		else {
			stato = DA_COMPLETARE;
		}


	}

	public void ordinaArrayDateEOre() {

		for(int i=data.size()-1;i>0;i--)
			for(int j=0;j<i;j++) {

				try {
					String dataSpl[] = data.get(j).split("/");

					int giorno1 = Integer.parseInt(dataSpl[0]);
					int mese1 = Integer.parseInt(dataSpl[1]);
					int anno1 = Integer.parseInt(dataSpl[2]);

					dataSpl = data.get(j+1).split("/");

					int giorno2 = Integer.parseInt(dataSpl[0]);
					int mese2 = Integer.parseInt(dataSpl[1]);
					int anno2 = Integer.parseInt(dataSpl[2]);

					GregorianCalendar g1 = new GregorianCalendar(anno1,mese1,giorno1);
					GregorianCalendar g2 = new GregorianCalendar(anno2,mese2,giorno2);

					if(g1.getTimeInMillis()>g2.getTimeInMillis()) {
						String temp1 = data.get(j);
						data.set(j, data.get(j+1));
						data.set(j+1, temp1);

						int temp2 = nOre.get(j);
						nOre.set(j, nOre.get(j+1));
						nOre.set(j+1, temp2);
					}

				}catch(Exception e) {

				}


			}


	}




}
