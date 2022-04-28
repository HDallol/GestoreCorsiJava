import java.io.Serializable;
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
	
	public final static int GENERALE=0;
	public final static int SPECIFICA=1;
	public final static int PREPOSTO=2;
	public final static int QUINQUIENNALE=3;

	private String nomeCorso;
	/**
	 * Formattazione: GG/MM/AAAA
	 */
	private String data;
	private int nOre;
	private int scadenzaAnni;
	private int stato;
	/**
	 * GENERALE, SPECIFICA, PREPOSTO o QUINQUIENNALE
	 */
	private int tipologia;

	
	public CorsoDiFormazione() {
		nomeCorso = null;
		data = null;
		nOre = 0;
		stato = VALIDO;
		this.tipologia=0;
	}

	/**
	 * 
	 * @param nomeCorso il nome del corso di formazione
	 * @param data la data in cui il corso è stato fatto FORMATTAZIONE GG/MM/AA
	 * @param nOre il numero di ore che certifica il corso
	 * @param scadenzaAnni il numero di anni che devono passare prima che il corso scadi
	 */
	public CorsoDiFormazione(String nomeCorso, String data, int nOre, int scadenzaAnni, int tipologia) {

		this.nomeCorso = nomeCorso;
		this.data = data;
		if(nOre>1)
			this.nOre = nOre;
		else
			this.nOre = 1;
		
		this.tipologia = tipologia;
		this.scadenzaAnni = scadenzaAnni;
		
		if(this.tipologia>3 || this.tipologia<0)
			this.tipologia = 0;
		
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
	public String getData() {
		return data;
	}

	/**
	 * @return the nOre
	 */
	public int getnOre() {
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
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
		
		aggiornaStato();
	}

	/**
	 * @param nOre the nOre to set
	 */
	public void setnOre(int nOre) {
		this.nOre = nOre;
	}

	/**
	 * @param scadenzaAnni the scadenzaAnni to set
	 */
	public void setScadenzaAnni(int scadenzaAnni) {
		this.scadenzaAnni = scadenzaAnni;
		
		aggiornaStato();
	}
	

	public void aggiornaStato() {


		try {
			String dataSpl[] = data.split("/");

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

	

	
	

}
