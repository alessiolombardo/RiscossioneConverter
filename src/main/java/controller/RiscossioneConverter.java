package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import model.RiscossioneModel;

/**
 * Controller dell'applicazione: consente di convertire un ruolo da un formato a
 * blocchi (si veda classe RiscossioneModel per ulteriori dettagli) ad un
 * formato Excel, ODS, XML o JSON
 * 
 * @see RiscossioneModel
 * @author Alessio Lombardo
 *
 */
public class RiscossioneConverter {

	/**
	 * File dati (input)
	 */
	private File fileDati;

	/**
	 * File di Output
	 */
	private File fileOutput;

	/**
	 * Ultima directory selezionata per il file dati
	 */
	private String lastPathDati = ".";

	/**
	 * Ultima directory selezionata per il file di output
	 */
	private String lastPathOutput = ".";

	public File getFileDati() {
		return fileDati;
	}

	public void setFileDati(File fileDati) {
		this.fileDati = fileDati;
	}

	public File getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(File fileOutput) {
		this.fileOutput = fileOutput;
	}

	public String getLastPathDati() {
		return lastPathDati;
	}

	public void setLastPathDati(String lastPathDati) {
		this.lastPathDati = lastPathDati;
	}

	public String getLastPathOutput() {
		return lastPathOutput;
	}

	public void setLastPathOutput(String lastPathOutput) {
		this.lastPathOutput = lastPathOutput;
	}

	/**
	 * Avvia conversione (legge dati, converte, salva nel formato scelto)
	 * 
	 * @throws Exception Errore catturato durante la conversione
	 */
	public void avviaConversione() throws Exception {
		if (!fileDati.isFile() || !fileDati.exists())
			throw new Exception("File Dati inesistente.");

		RiscossioneWriter writer = new RiscossioneWriter(fileOutput);
		writer.save(converter(readBlockData()));

	}

	/**
	 * Legge il file dati e ritorna l'elenco di record (stringhe)
	 * 
	 * @return Lista di record
	 * @throws Exception Errore nella lettura del file dati
	 */
	private ArrayList<String> readBlockData() throws Exception {
		ArrayList<String> records = new ArrayList<String>();
		Scanner scanner = new Scanner(fileDati);
		while (scanner.hasNextLine()) {
			records.add(scanner.nextLine());
		}
		scanner.close();
		return records;
	}

	/**
	 * Converte un elenco di record in un elenco di oggetti RiscossioneModel, pronti
	 * per essere serializzati nel formato desiderato
	 * 
	 * @param records Elenco di record da convertire
	 * @return Elenco di oggetti RiscossioneModel
	 * @throws Exception Errore nella conversione
	 */
	private ArrayList<RiscossioneModel> converter(ArrayList<String> records) throws Exception {
		ArrayList<RiscossioneModel> cartelle = new ArrayList<RiscossioneModel>();
		int index = 1;
		for (String record : records) {
			if (!record.replaceAll("[^(a-zA-Z0-9_ )]", "").trim().equals(""))
				cartelle.add(converter(record, index));
			index++;
		}
		if(cartelle.size()==0)
			throw new Exception("Il file dati non ha record validi.");
		return cartelle;
	}

	/**
	 * Converte un singolo record in un oggetto RiscossioneModel, pronto per essere
	 * serializzato nel formato desiderato
	 * 
	 * @param record record da convertire
	 * @param index  indice del record (utile in caso di errore)
	 * @return Oggetto RiscossioneModel
	 * @throws Exception Errore nella conversione
	 */
	private RiscossioneModel converter(String record, int index) throws Exception {

		RiscossioneModel cartella = new RiscossioneModel();

		try {

			cartella.setAgenteRiscossione(Integer.parseInt(record.substring(0, 3)));
			cartella.setEnteImpositore(record.substring(3, 8).trim());
			cartella.setTipoUfficio(record.substring(8, 9).trim());
			cartella.setCodiceUfficio(record.substring(9, 15).trim());
			cartella.setAnnoRuolo(Integer.parseInt(record.substring(15, 19)));
			cartella.setNumeroRuolo(Integer.parseInt(record.substring(19, 25)));
			cartella.setSpecieRuolo(record.substring(25, 26).trim());
			cartella.setCodiceContribuente(record.substring(26, 42).trim());
			cartella.setCodiceCartella(record.substring(42, 62).trim());
			cartella.setProgressivoTributo(Integer.parseInt(record.substring(62, 65)));
			cartella.setCodiceTributo(record.substring(65, 69).trim());
			cartella.setAnnoTributo(Integer.parseInt(record.substring(69, 73)));
			cartella.setTipoTributo(record.substring(73, 74).trim());
			cartella.setCaricoIscritto(Double.parseDouble(record.substring(74, 89) + "." + record.substring(89, 91)));
			cartella.setCaricoResiduo(Double.parseDouble(record.substring(91, 106) + "." + record.substring(106, 108)));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Record " + index + " non valido. Correggere il record e riprovare.");
		}

		return cartella;

	}

}
