import java.io.Serializable;
import java.util.ArrayList;

public class Lavoratore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5472092391274441816L;
	
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String indirizzo;
	private String qualifica;
	private ArrayList<CorsoDiFormazione> corsiDiFormazione;
	private boolean daAggiornare;
	
	public Lavoratore() {
		
		cognome = null;
		nome = null;
		codiceFiscale = null;
		indirizzo = null;
		qualifica = null;
		this.corsiDiFormazione = new ArrayList<CorsoDiFormazione>();
		daAggiornare = false;
	}
	
	/**
	 * @param cognome il cognome del lavoratore
	 * @param nome il nome del lavoratore 
	 * @param codiceFiscale il codice fiscale del lavoratore
	 * @param indirizzo l'indirizzo del lavoratore
	 * @param qualifica la qualifica del lavoratore
	 */
	public Lavoratore(String cognome, String nome, String codiceFiscale, String indirizzo, String qualifica) {
		this.cognome = cognome;
		this.nome = nome;
		this.codiceFiscale = codiceFiscale;
		this.indirizzo = indirizzo;
		this.qualifica = qualifica;
		this.corsiDiFormazione = new ArrayList<CorsoDiFormazione>();
		this.daAggiornare=false;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @return the qualifica
	 */
	public String getQualifica() {
		return qualifica;
	}

	/**
	 * 
	 * @return daAggiornare
	 */
	public boolean getDaAggiornare() {
		return daAggiornare;
	}
	
	/**
	 * @return the corsiDiFormazioni
	 */
	public ArrayList<CorsoDiFormazione> getCorsiDiFormazioni() {
		return corsiDiFormazione;
	}

	/**
	 * 
	 * @param b
	 */
	public void setDaAggiornare(boolean b) {
		daAggiornare = b;
	}
	 
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * @param indirizzo the indirizzo to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @param qualifica the qualifica to set
	 */
	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	/**
	 * 
	 * @param c il corso di formazione da aggiungere
	 */
	public void aggiungiCorso(CorsoDiFormazione c) {
		corsiDiFormazione.add(c);
	}
	
	/**
	 * 
	 * @param c il corso di formazione da rimuovere
	 */
	public void togliCorso(CorsoDiFormazione c) {
		corsiDiFormazione.remove(c);
	}
	
	/**
	 * 
	 * @return il numero di corsi scaduti
	 */
	public int getCorsiScaduti() {
		
		int n = 0;
		for(int i=0;i<corsiDiFormazione.size();i++) {
			if(corsiDiFormazione.get(i).getStato()==CorsoDiFormazione.SCADUTO)
				n++;
		}
		
		return n;
	}
	
	public int getCorsiInScadenza() {
		int n=0;
		
		for(int i=0;i<corsiDiFormazione.size();i++) {
			if(corsiDiFormazione.get(i).getStato()==CorsoDiFormazione.IN_SCADENZA) {
				n++;
			}
		}
		
		return n;
	}
	
	public int getNumeroCorsi(int tipologia) {
		
		int n=0;
		
		for(int i=0;i<corsiDiFormazione.size();i++) 
			if(corsiDiFormazione.get(i).getTipologia()==tipologia)
				n++;
		
		return n;
	}
	
	public ArrayList<CorsoDiFormazione> getCorsi(int tipologia) {
		
		ArrayList<CorsoDiFormazione> arr = new ArrayList<CorsoDiFormazione>();
		
		for(int i=0;i<corsiDiFormazione.size();i++) 
			if(corsiDiFormazione.get(i).getTipologia()==tipologia)
				arr.add(corsiDiFormazione.get(i));
		
		return arr;
	}
	
	
}
