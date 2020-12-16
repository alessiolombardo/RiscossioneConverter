package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import model.RiscossioneModel;

/**
 * Controller dell'applicazione: consente di convertire un ruolo da un formato a
 * blocchi (si veda classe RiscossioneModel per ulteriori dettagli) ad un
 * formato Excel
 * 
 * @see RiscossioneModel
 * @author Alessio
 *
 */
public class RiscossioneConverter {

	/**
	 * File dati (input)
	 */
	private File fileDati;

	/**
	 * File Excel (output)
	 */
	private File fileExcel;

	/**
	 * Ultima directory selezionata per il file dati
	 */
	private String lastPathDati = ".";

	/**
	 * Ultima directory selezionata per il file excel
	 */
	private String lastPathExcel = ".";

	public File getFileDati() {
		return fileDati;
	}

	public void setFileDati(File fileDati) {
		this.fileDati = fileDati;
	}

	public File getFileExcel() {
		return fileExcel;
	}

	public void setFileExcel(File fileExcel) {
		this.fileExcel = fileExcel;
	}

	public String getLastPathDati() {
		return lastPathDati;
	}

	public void setLastPathDati(String lastPathDati) {
		this.lastPathDati = lastPathDati;
	}

	public String getLastPathExcel() {
		return lastPathExcel;
	}

	public void setLastPathExcel(String lastPathExcel) {
		this.lastPathExcel = lastPathExcel;
	}

	/**
	 * Avvia conversione (legge dati, converte, salva in formato Excel)
	 * 
	 * @throws Exception Errore catturato durante la conversione
	 */
	public void avviaConversione() throws Exception {
		if (!fileDati.isFile() || !fileDati.exists()) {
			throw new Exception("File Dati inesistente.");
		} else {
			salvaExcel(converter(leggiDati()));
		}
	}

	/**
	 * Legge il file dati e ritorna l'elenco di record (stringhe)
	 * 
	 * @return Lista di record
	 * @throws FileNotFoundException File dati non trovato
	 */
	private ArrayList<String> leggiDati() throws FileNotFoundException {
		ArrayList<String> records = new ArrayList<String>();
		Scanner scanner = new Scanner(fileDati);
		while (scanner.hasNextLine()) {
			records.add(scanner.nextLine());
		}
		scanner.close();
		return records;
	}

	/**
	 * Serializza sul file Excel prestabilito l'elenco di oggetti RiscossioneModel
	 * fornito
	 * 
	 * @param cartelle Lista di oggetti RiscossioneModel
	 * @throws Exception Errore nel salvataggio su file Excel
	 */
	private void salvaExcel(ArrayList<RiscossioneModel> cartelle) throws Exception {

		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		String sheetName = "Riscossione";

		Object[] headerValues = RiscossioneModel.Campi.values();

		int newRowIndex = 0;

		try {

			if (fileExcel.exists() && !fileExcel.isDirectory()) { // File esistente (append)
				FileInputStream inputStream = new FileInputStream(fileExcel);
				workbook = WorkbookFactory.create(inputStream);
				sheet = workbook.getSheet(sheetName);
				inputStream.close();
				newRowIndex = sheet.getLastRowNum() + 1;
			} else { // Nuovo file
				newRowIndex = 0;
				workbook = new HSSFWorkbook();
				sheet = workbook.createSheet(sheetName);
				Row headerRow = sheet.createRow(newRowIndex);
				for (int i = 0; i < headerValues.length; i++) {
					cell = headerRow.createCell(i, CellType.STRING);
					cell.setCellValue(headerValues[i].toString().replace("_", " "));
				}
				newRowIndex++;
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new Exception("File Excel invalido o danneggiato.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Errore generico di I/O.");
		}

		CellStyle cellStyle2decimal = workbook.createCellStyle();
		cellStyle2decimal.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

		for (RiscossioneModel cartella : cartelle) {

			row = sheet.createRow(newRowIndex);

			cell = row.createCell(RiscossioneModel.Campi.Agente_Riscossione.ordinal(), CellType.NUMERIC);
			cell.setCellValue(cartella.getAgenteRiscossione());

			cell = row.createCell(RiscossioneModel.Campi.Ente_Impositore.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getEnteImpositore());

			cell = row.createCell(RiscossioneModel.Campi.Tipo_Ufficio.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getTipoUfficio());

			cell = row.createCell(RiscossioneModel.Campi.Codice_Ufficio.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getCodiceUfficio());

			cell = row.createCell(RiscossioneModel.Campi.Anno_Ruolo.ordinal(), CellType.NUMERIC);
			cell.setCellValue(cartella.getAnnoRuolo());

			cell = row.createCell(RiscossioneModel.Campi.Numero_Ruolo.ordinal(), CellType.NUMERIC);
			cell.setCellValue(cartella.getNumeroRuolo());

			cell = row.createCell(RiscossioneModel.Campi.Specie_Ruolo.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getSpecieRuolo());

			cell = row.createCell(RiscossioneModel.Campi.Codice_Contribuente.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getCodiceContribuente());

			cell = row.createCell(RiscossioneModel.Campi.Codice_Cartella.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getCodiceCartella());

			cell = row.createCell(RiscossioneModel.Campi.Progressivo_Tributo.ordinal(), CellType.NUMERIC);
			cell.setCellValue(cartella.getProgressivoTributo());

			cell = row.createCell(RiscossioneModel.Campi.Codice_Tributo.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getCodiceTributo());

			cell = row.createCell(RiscossioneModel.Campi.Anno_Tributo.ordinal(), CellType.NUMERIC);
			cell.setCellValue(cartella.getAnnoTributo());

			cell = row.createCell(RiscossioneModel.Campi.Tipo_Tributo.ordinal(), CellType.STRING);
			cell.setCellValue(cartella.getTipoTributo());

			cell = row.createCell(RiscossioneModel.Campi.Carico_Iscritto.ordinal(), CellType.NUMERIC);
			cell.setCellStyle(cellStyle2decimal);
			cell.setCellValue(cartella.getCaricoIscritto());

			cell = row.createCell(RiscossioneModel.Campi.Carico_Residuo.ordinal(), CellType.NUMERIC);
			cell.setCellStyle(cellStyle2decimal);
			cell.setCellValue(cartella.getCaricoResiduo());

			newRowIndex++;

		}

		try {
			FileOutputStream outputStream = new FileOutputStream(fileExcel);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Impossibile creare o aggiornare il file Excel. Verificare che non sia aperto.");
		}

	}

	/**
	 * Converte un elenco di record in un elenco di oggetti RiscossioneModel, pronti
	 * per essere serializzati in formato Excel
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
		return cartelle;
	}

	/**
	 * Converte un singolo record in un oggetto RiscossioneModel, pronto per essere
	 * serializzato in formato Excel
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
			cartella.setCaricoIscritto(Float.parseFloat(record.substring(74, 89) + "." + record.substring(89, 91)));
			cartella.setCaricoResiduo(Float.parseFloat(record.substring(91, 106) + "." + record.substring(106, 108)));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Record " + index + " non valido. Correggere il record e riprovare.");
		}

		return cartella;

	}

}
