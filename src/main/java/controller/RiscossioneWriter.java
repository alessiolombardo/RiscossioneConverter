package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.StyleStyle;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.style.data.DataStyle;
import org.jopendocument.dom.style.data.NumberStyle;
import org.jopendocument.dom.style.data.DataStyle.DataStyleDesc;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.RiscossioneModel;

/**
 * Serializzatore per oggetti RiscossioneModel
 * 
 * @author Alessio Lombardo
 *
 */
public class RiscossioneWriter {

	/**
	 * File di output per la serializzazione
	 */
	private File fileOutput;

	/**
	 * Costruttore della classe
	 * 
	 * @param fileOutput File di output per la serializzazione
	 */
	public RiscossioneWriter(File fileOutput) {
		this.fileOutput = fileOutput;
	}

	/**
	 * Serializza sul file prestabilito la lista di oggetti RiscossioneModel fornito
	 * 
	 * @param cartelle Lista di oggetti RiscossioneModel
	 * @throws Exception Errore nel salvataggio su file
	 */
	public void save(ArrayList<RiscossioneModel> cartelle) throws Exception {

		String fileOutputName = fileOutput.getAbsolutePath();

		if (fileOutputName.endsWith(".xls"))
			saveExcel(cartelle, true);
		else if (fileOutputName.endsWith(".xlsx"))
			saveExcel(cartelle, false);
		else if (fileOutputName.endsWith(".ods"))
			saveOds(cartelle);
		else if (fileOutputName.endsWith(".xml"))
			saveXml(cartelle);
		else if (fileOutputName.endsWith(".json"))
			saveJson(cartelle);
		else {
			for (RiscossioneModel cartella : cartelle)
				System.out.println(cartella);
			throw new Exception("Formato di output non riconosciuto.");
		}

	}

	/**
	 * Serializza sul file Excel (.xls/.xlsx) prestabilito la lista di oggetti
	 * RiscossioneModel fornito
	 * 
	 * @param cartelle       Lista di oggetti RiscossioneModel
	 * @param isExcel97_2003 Tipologia Excel. true per Excel 97-2003 (.xls), false
	 *                       per Excel (.xlsx)
	 * @throws Exception Errore nel salvataggio su file Excel
	 */
	private void saveExcel(ArrayList<RiscossioneModel> cartelle, boolean isExcel97_2003) throws Exception {

		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		String sheetName = "Riscossione";
		Object[] headerValues = RiscossioneModel.Campi.values();

		int newRowIndex = 0;

		if (isExcel97_2003)
			ZipSecureFile.setMinInflateRatio(0.0001);

		if (fileOutput.exists() && !fileOutput.isDirectory()) { // File esistente (append)

			FileInputStream inputStream = new FileInputStream(fileOutput);
			workbook = WorkbookFactory.create(inputStream);
			sheet = workbook.getSheet(sheetName);
			inputStream.close();
			newRowIndex = sheet.getLastRowNum() + 1;

		} else { // Nuovo file

			if (isExcel97_2003)
				workbook = new HSSFWorkbook();
			else
				workbook = new XSSFWorkbook();

			sheet = workbook.createSheet(sheetName);
			Row headerRow = sheet.createRow(newRowIndex);
			for (int i = 0; i < headerValues.length; i++) {
				cell = headerRow.createCell(i, CellType.STRING);
				cell.setCellValue(headerValues[i].toString().replace("_", " "));
			}
			newRowIndex++;
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

		FileOutputStream outputStream = new FileOutputStream(fileOutput);
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

	/**
	 * Serializza sul file ODS (.ods) prestabilito la lista di oggetti
	 * RiscossioneModel fornito
	 * 
	 * @param cartelle Lista di oggetti RiscossioneModel
	 * @throws Exception Errore nel salvataggio su file ODS
	 */
	private void saveOds(ArrayList<RiscossioneModel> cartelle) throws Exception {

		org.jopendocument.dom.spreadsheet.Sheet sheet = null;

		String sheetName = "Riscossione";
		Object[] headerValues = RiscossioneModel.Campi.values();

		int newRowIndex = 0;

		if (fileOutput.exists() && !fileOutput.isDirectory()) { // File esistente (append)

			sheet = SpreadSheet.createFromFile(fileOutput).getSheet(0);
			newRowIndex = sheet.getRowCount();
			sheet.ensureRowCount(sheet.getRowCount() + cartelle.size());

		} else { // Nuovo file
			sheet = SpreadSheet.create(1, RiscossioneModel.Campi.values().length, cartelle.size() + 1).getSheet(0);
			sheet.setName(sheetName);

			for (int i = 0; i < headerValues.length; i++) {
				sheet.getCellAt(i, newRowIndex).setValue(headerValues[i].toString().replace("_", " "),
						ODValueType.STRING, false, false);
			}

			newRowIndex++;
		}

		ODPackage pkg = sheet.getSpreadSheet().getPackage();

		DataStyleDesc<NumberStyle> dataStyleDesc = DataStyle.getDesc(NumberStyle.class,
				sheet.getODDocument().getVersion());
		Namespace numberNamespace = dataStyleDesc.getElementNS();
		NumberStyle numberStyle2decimal = dataStyleDesc.createAutoStyle(pkg, "numberStyle2decimal");
		Element numberElement = new Element("number", numberNamespace);
		numberElement.setAttribute("decimal-places", "2", numberNamespace);
		numberStyle2decimal.getElement().addContent(numberElement);

		StyleStyleDesc<org.jopendocument.dom.spreadsheet.CellStyle> styleStyleDesc = StyleStyle.getStyleStyleDesc(
				org.jopendocument.dom.spreadsheet.CellStyle.class, sheet.getODDocument().getVersion());
		org.jopendocument.dom.spreadsheet.CellStyle cellStyle2decimal = styleStyleDesc.createAutoStyle(pkg,
				"cellStyle2decimal");
		cellStyle2decimal.getElement().setAttribute("data-style-name", numberStyle2decimal.getName(),
				pkg.getVersion().getSTYLE());

		for (RiscossioneModel cartella : cartelle) {

			sheet.getCellAt(RiscossioneModel.Campi.Agente_Riscossione.ordinal(), newRowIndex)
					.setValue(cartella.getAgenteRiscossione(), ODValueType.FLOAT, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Ente_Impositore.ordinal(), newRowIndex)
					.setValue(cartella.getEnteImpositore(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Tipo_Ufficio.ordinal(), newRowIndex)
					.setValue(cartella.getTipoUfficio(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Codice_Ufficio.ordinal(), newRowIndex)
					.setValue(cartella.getCodiceUfficio(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Anno_Ruolo.ordinal(), newRowIndex).setValue(cartella.getAnnoRuolo(),
					ODValueType.FLOAT, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Numero_Ruolo.ordinal(), newRowIndex)
					.setValue(cartella.getNumeroRuolo(), ODValueType.FLOAT, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Specie_Ruolo.ordinal(), newRowIndex)
					.setValue(cartella.getSpecieRuolo(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Codice_Contribuente.ordinal(), newRowIndex)
					.setValue(cartella.getCodiceContribuente(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Codice_Cartella.ordinal(), newRowIndex)
					.setValue(cartella.getCodiceCartella(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Progressivo_Tributo.ordinal(), newRowIndex)
					.setValue(cartella.getProgressivoTributo(), ODValueType.FLOAT, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Codice_Tributo.ordinal(), newRowIndex)
					.setValue(cartella.getCodiceTributo(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Anno_Tributo.ordinal(), newRowIndex)
					.setValue(cartella.getAnnoTributo(), ODValueType.FLOAT, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Tipo_Tributo.ordinal(), newRowIndex)
					.setValue(cartella.getTipoTributo(), ODValueType.STRING, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Carico_Iscritto.ordinal(), newRowIndex)
					.setStyleName(cellStyle2decimal.getName());
			sheet.getCellAt(RiscossioneModel.Campi.Carico_Iscritto.ordinal(), newRowIndex)
					.setValue(cartella.getCaricoIscritto(), ODValueType.CURRENCY, false, false);

			sheet.getCellAt(RiscossioneModel.Campi.Carico_Residuo.ordinal(), newRowIndex)
					.setStyleName(cellStyle2decimal.getName());
			sheet.getCellAt(RiscossioneModel.Campi.Carico_Residuo.ordinal(), newRowIndex)
					.setValue(cartella.getCaricoResiduo(), ODValueType.CURRENCY, false, false);

			newRowIndex++;

		}

		sheet.getSpreadSheet().saveAs(fileOutput);

	}

	/**
	 * Serializza sul file XML (.xml) prestabilito la lista di oggetti
	 * RiscossioneModel fornito
	 * 
	 * @param cartelle Lista di oggetti RiscossioneModel
	 * @throws Exception Errore nel salvataggio su file ODS
	 */
	private void saveXml(ArrayList<RiscossioneModel> cartelle) throws Exception {

		Document document = null;
		org.w3c.dom.Element root = null;

		String rootElementName = "Riscossione";

		int newRowIndex = 0;

		if (fileOutput.exists() && !fileOutput.isDirectory()) { // File esistente (append)

			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileOutput);
			root = (org.w3c.dom.Element) document.getElementsByTagName(rootElementName).item(0);
			NodeList recordList = root.getElementsByTagName("Record");
			Node lastRecord = recordList.item(recordList.getLength() - 1);
			newRowIndex = Integer.parseInt(((org.w3c.dom.Element) lastRecord).getAttribute("ID")) + 1;

		} else { // Nuovo file

			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			root = document.createElement(rootElementName);
			document.appendChild(root);

		}

		for (RiscossioneModel cartella : cartelle) {

			org.w3c.dom.Element record = document.createElement("Record");
			Attr attr = document.createAttribute("ID");
			attr.setValue(String.valueOf(newRowIndex));
			record.setAttributeNode(attr);
			root.appendChild(record);

			org.w3c.dom.Element field;

			field = document.createElement(RiscossioneModel.Campi.Agente_Riscossione.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getAgenteRiscossione()));

			field = document.createElement(RiscossioneModel.Campi.Ente_Impositore.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getEnteImpositore());

			field = document.createElement(RiscossioneModel.Campi.Tipo_Ufficio.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getTipoUfficio());

			field = document.createElement(RiscossioneModel.Campi.Codice_Ufficio.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getCodiceUfficio());

			field = document.createElement(RiscossioneModel.Campi.Anno_Ruolo.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getAnnoRuolo()));

			field = document.createElement(RiscossioneModel.Campi.Numero_Ruolo.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getNumeroRuolo()));

			field = document.createElement(RiscossioneModel.Campi.Specie_Ruolo.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getSpecieRuolo());

			field = document.createElement(RiscossioneModel.Campi.Codice_Contribuente.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getCodiceContribuente());

			field = document.createElement(RiscossioneModel.Campi.Codice_Cartella.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getCodiceCartella());

			field = document.createElement(RiscossioneModel.Campi.Progressivo_Tributo.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getProgressivoTributo()));

			field = document.createElement(RiscossioneModel.Campi.Codice_Tributo.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getCodiceTributo());

			field = document.createElement(RiscossioneModel.Campi.Anno_Tributo.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getAnnoTributo()));

			field = document.createElement(RiscossioneModel.Campi.Tipo_Tributo.name());
			field.setAttribute("data_type", "STRING");
			record.appendChild(field).setTextContent(cartella.getTipoTributo());

			field = document.createElement(RiscossioneModel.Campi.Carico_Iscritto.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getCaricoIscritto()));

			field = document.createElement(RiscossioneModel.Campi.Carico_Residuo.name());
			field.setAttribute("data_type", "NUMERIC");
			record.appendChild(field).setTextContent(String.valueOf(cartella.getCaricoResiduo()));

			newRowIndex++;

		}

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(document), new StreamResult(fileOutput));

	}

	/**
	 * Serializza sul file JSON (.json) prestabilito la lista di oggetti
	 * RiscossioneModel fornito
	 * 
	 * @param cartelle Lista di oggetti RiscossioneModel
	 * @throws Exception Errore nel salvataggio su file JSON
	 */
	private void saveJson(ArrayList<RiscossioneModel> cartelle) throws Exception {

		JsonArray records = null;

		if (fileOutput.exists() && !fileOutput.isDirectory()) { // File esistente (append)
			records = (JsonArray) JsonParser.parseString(
					new Gson().fromJson(new BufferedReader(new FileReader(fileOutput)), Object.class).toString());
		} else { // Nuovo file
			records = new JsonArray();
		}

		for (RiscossioneModel cartella : cartelle) {

			JsonObject record = new JsonObject();

			record.addProperty(RiscossioneModel.Campi.Agente_Riscossione.name(), cartella.getAgenteRiscossione());
			record.addProperty(RiscossioneModel.Campi.Ente_Impositore.name(), cartella.getEnteImpositore());
			record.addProperty(RiscossioneModel.Campi.Tipo_Ufficio.name(), cartella.getTipoUfficio());
			record.addProperty(RiscossioneModel.Campi.Codice_Ufficio.name(), cartella.getCodiceUfficio());
			record.addProperty(RiscossioneModel.Campi.Anno_Ruolo.name(), cartella.getAnnoRuolo());
			record.addProperty(RiscossioneModel.Campi.Numero_Ruolo.name(), cartella.getNumeroRuolo());
			record.addProperty(RiscossioneModel.Campi.Specie_Ruolo.name(), cartella.getSpecieRuolo());
			record.addProperty(RiscossioneModel.Campi.Codice_Contribuente.name(), cartella.getCodiceContribuente());
			record.addProperty(RiscossioneModel.Campi.Codice_Cartella.name(), cartella.getCodiceCartella());
			record.addProperty(RiscossioneModel.Campi.Progressivo_Tributo.name(), cartella.getProgressivoTributo());
			record.addProperty(RiscossioneModel.Campi.Codice_Tributo.name(), cartella.getCodiceTributo());
			record.addProperty(RiscossioneModel.Campi.Anno_Tributo.name(), cartella.getAnnoTributo());
			record.addProperty(RiscossioneModel.Campi.Tipo_Tributo.name(), cartella.getTipoTributo());
			record.addProperty(RiscossioneModel.Campi.Carico_Iscritto.name(), cartella.getCaricoIscritto());
			record.addProperty(RiscossioneModel.Campi.Carico_Residuo.name(), cartella.getCaricoResiduo());

			records.add(record);
		}

		FileWriter file = new FileWriter(fileOutput);
		file.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(records.toString())));
		file.flush();
		file.close();

	}

}
