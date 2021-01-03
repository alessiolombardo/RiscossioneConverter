package view;

import java.io.File;
import java.io.IOException;
import controller.RiscossioneConverter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * GUI JavaFX (compatibile con Java 8 e superiori)
 * 
 * @author Alessio Lombardo
 *
 */
public class RiscossioneConverterGUIJavaFX extends Application {

	/**
	 * Controller di tutte le procedure dell'applicazione
	 */
	private RiscossioneConverter converter = new RiscossioneConverter();

	/**
	 * Text Field che visualizza percorso e nome del file dati selezionato
	 */
	@FXML
	private TextField textFieldFileDati;

	/**
	 * Text Field che visualizza percorso e nome del file di output selezionato
	 */
	@FXML
	private TextField textFieldFileOutput;

	/**
	 * Consente la scelta del file dati (funzione legata alla pressione del tasto
	 * "Apri File Dati")
	 * 
	 * @param event Evento click sul bottone
	 */
	@FXML
	private void apriFileDati(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Apri File Dati");
		fileChooser.setInitialDirectory(new File(converter.getLastPathDati()));
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			if (selectedFile.exists())
				converter.setLastPathDati(selectedFile.getParent());

			textFieldFileDati.setText(selectedFile.getAbsolutePath());

		}

	}

	/**
	 * Consente la scelta del file di output (funzione legata alla pressione del tasto
	 * "Apri File di Output")
	 * 
	 * @param event Evento click sul bottone
	 */
	@FXML
	private void apriFileOutput(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Apri File di Output");
		fileChooser.setInitialDirectory(new File(converter.getLastPathOutput()));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 97-2003 (*.xls)", "*.xls"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Document Spreadsheet (*.ods)", "*.ods"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON (*.json)", "*.json"));
		File selectedFile = fileChooser.showSaveDialog(null);

		if (selectedFile != null) {
			if (selectedFile.exists())
				converter.setLastPathOutput(selectedFile.getParent());
			textFieldFileOutput.setText(selectedFile.getAbsolutePath());
		}
	}

	/**
	 * Avvia la conversione (funzione legata alla pressione del tasto "Converti")
	 * 
	 * @param event Evento click sul bottone
	 */
	@FXML
	private void converti(ActionEvent event) {
		converter.setFileDati(new File(textFieldFileDati.getText()));
		converter.setFileOutput(new File(textFieldFileOutput.getText()));

		try {
			converter.avviaConversione();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informazione");
			alert.setHeaderText(null);
			alert.setContentText("Conversione completata");
			alert.showAndWait();
		} catch (Exception e1) {
			e1.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText(null);
			alert.setContentText(e1.getClass().getSimpleName() + ": " + e1.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Main dell'applicazione
	 * 
	 * @param args Argomenti (non utilizzati)
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Crea la GUI JavaFX
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Riscossione Converter");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/RiscossioneConverter.png")));

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/RiscossioneConverterGUIJavaFX.fxml"));
			AnchorPane primaryPanel = (AnchorPane) loader.load();

			Scene scene = new Scene(primaryPanel);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
