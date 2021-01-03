package model;

/**
 * 
 * Classe di Modello del DECRETO 15 GIUGNO 2015 DEL MINISTERO DELL'ECONOMIA E
 * DELLE FINANZE - ALLEGATI 1/2
 * 
 * @see <a href="https://www.gazzettaufficiale.it/do/atto/serie_generale/caricaPdf?cdimg=15A0467500100010110001&dgu=2015-06-22&art.dataPubblicazioneGazzetta=2015-06-22&art.codiceRedazionale=15A04675&art.num=1&art.tiposerie=SG" target="_blank">Tracciato record</a>
 * @see <a href="https://www.gazzettaufficiale.it/atto/serie_generale/caricaDettaglioAtto/originario?atto.dataPubblicazioneGazzetta=2015-06-22&atto.codiceRedazionale=15A04675" target="_blank">Normativa</a>
 * 
 * @author Alessio Lombardo
 *
 */

public class RiscossioneModel {

	/**
	 * 
	 * Elenco campi del file di output (legati ai campi del tracciato record)
	 * 
	 * @author Alessio Lombardo
	 *
	 */
	public enum Campi {
	Agente_Riscossione, Ente_Impositore, Tipo_Ufficio, Codice_Ufficio, Anno_Ruolo, Numero_Ruolo, Specie_Ruolo,
	Codice_Contribuente, Codice_Cartella, Progressivo_Tributo, Codice_Tributo, Anno_Tributo, Tipo_Tributo,
	Carico_Iscritto, Carico_Residuo
	}

	/**
	 * Codice identificativo dell'agente della riscossione (Tipo: N, Lunghezza: 3)
	 */
	private int agenteRiscossione;

	/**
	 * Codice identificativo dell'ente impositore (ENTE) (Tipo: AN, Lunghezza: 5)
	 */
	private String enteImpositore;

	/**
	 * Codice identificativo dell'ente impositore (TIPO UFFICIO) (Tipo: AN,
	 * Lunghezza: 1)
	 */
	private String tipoUfficio;

	/**
	 * Codice identificativo dell'ente impositore (CODICE UFFICIO) (Tipo: AN,
	 * Lunghezza: 6)
	 */
	private String codiceUfficio;

	/**
	 * Estremi identificativi del ruolo (ANNO) (Tipo: N, Lunghezza: 4)
	 */
	private int annoRuolo;

	/**
	 * Estremi identificativi del ruolo (NUMERO) (Tipo: N, Lunghezza: 6)
	 */
	private int numeroRuolo;

	/**
	 * Specie del ruolo (Tipo: AN, Lunghezza: 1)
	 */
	private String specieRuolo;

	/**
	 * Identificativo del contribuente (Tipo: AN, Lunghezza: 16)
	 */
	private String codiceContribuente;

	/**
	 * Identificativo della cartella (Tipo: AN, Lunghezza: 20)
	 */
	private String codiceCartella;

	/**
	 * Progressivo tributo in cartella (Tipo: N, Lunghezza: 3)
	 */
	private int progressivoTributo;

	/**
	 * Codice del tributo (Tipo: AN, Lunghezza: 4)
	 */
	private String codiceTributo;

	/**
	 * Anno d'imposta del tributo (Tipo: N, Lunghezza: 4)
	 */
	private int annoTributo;

	/**
	 * Tipologia del tributo (campo facoltativo, se valorizzato vale I=Imposta,
	 * T=Interessi, S=Sanzioni, A=Altro) (Tipo: AN, Lunghezza: 1)
	 */
	private String tipoTributo;

	/**
	 * Carico iscritto a ruolo del tributo (Tipo: N, Lunghezza: 17, FLOAT Formato
	 * Cobol "9(15)V99")
	 */
	private double caricoIscritto;

	/**
	 * Carico residuo del tributo (Tipo: N, Lunghezza: 17, FLOAT Formato Cobol
	 * "9(15)V99" )
	 */
	private double caricoResiduo;

	public int getAgenteRiscossione() {
		return agenteRiscossione;
	}

	public void setAgenteRiscossione(int agenteRiscossione) {
		this.agenteRiscossione = agenteRiscossione;
	}

	public String getEnteImpositore() {
		return enteImpositore;
	}

	public void setEnteImpositore(String enteImpositore) {
		this.enteImpositore = enteImpositore;
	}

	public String getTipoUfficio() {
		return tipoUfficio;
	}

	public void setTipoUfficio(String tipoUfficio) {
		this.tipoUfficio = tipoUfficio;
	}

	public String getCodiceUfficio() {
		return codiceUfficio;
	}

	public void setCodiceUfficio(String codiceUfficio) {
		this.codiceUfficio = codiceUfficio;
	}

	public int getAnnoRuolo() {
		return annoRuolo;
	}

	public void setAnnoRuolo(int annoRuolo) {
		this.annoRuolo = annoRuolo;
	}

	public int getNumeroRuolo() {
		return numeroRuolo;
	}

	public void setNumeroRuolo(int numeroRuolo) {
		this.numeroRuolo = numeroRuolo;
	}

	public String getSpecieRuolo() {
		return specieRuolo;
	}

	public void setSpecieRuolo(String specieRuolo) {
		this.specieRuolo = specieRuolo;
	}

	public String getCodiceContribuente() {
		return codiceContribuente;
	}

	public void setCodiceContribuente(String codiceContribuente) {
		this.codiceContribuente = codiceContribuente;
	}

	public String getCodiceCartella() {
		return codiceCartella;
	}

	public void setCodiceCartella(String codiceCartella) {
		this.codiceCartella = codiceCartella;
	}

	public int getProgressivoTributo() {
		return progressivoTributo;
	}

	public void setProgressivoTributo(int progressivoTributo) {
		this.progressivoTributo = progressivoTributo;
	}

	public String getCodiceTributo() {
		return codiceTributo;
	}

	public void setCodiceTributo(String codiceTributo) {
		this.codiceTributo = codiceTributo;
	}

	public int getAnnoTributo() {
		return annoTributo;
	}

	public void setAnnoTributo(int annoTributo) {
		this.annoTributo = annoTributo;
	}

	public String getTipoTributo() {
		return tipoTributo;
	}

	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}

	public double getCaricoIscritto() {
		return caricoIscritto;
	}

	public void setCaricoIscritto(double caricoIscritto) {
		this.caricoIscritto = caricoIscritto;
	}

	public double getCaricoResiduo() {
		return caricoResiduo;
	}

	public void setCaricoResiduo(double caricoResiduo) {
		this.caricoResiduo = caricoResiduo;
	}

	@Override
	public String toString() {
		return "RiscossioneModel [agenteRiscossione=" + agenteRiscossione + ", enteImpositore=" + enteImpositore
				+ ", tipoUfficio=" + tipoUfficio + ", codiceUfficio=" + codiceUfficio + ", annoRuolo=" + annoRuolo
				+ ", numeroRuolo=" + numeroRuolo + ", specieRuolo=" + specieRuolo + ", codiceContribuente="
				+ codiceContribuente + ", codiceCartella=" + codiceCartella + ", progressivoTributo="
				+ progressivoTributo + ", codiceTributo=" + codiceTributo + ", annoTributo=" + annoTributo
				+ ", tipoTributo=" + tipoTributo + ", caricoIscritto=" + caricoIscritto + ", caricoResiduo="
				+ caricoResiduo + "]";
	}

}
