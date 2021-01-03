package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.RiscossioneConverter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;

/**
 * 
 * GUI Swing (compatibile con Java 7 e superiori)
 * 
 * @author Alessio Lombardo
 *
 */
public class RiscossioneConverterGUISwing extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Pannello principale
	 */
	private JPanel contentPane;

	/**
	 * Text Field che visualizza percorso e nome del file dati selezionato
	 */
	private JTextField textFieldFileDati;

	/**
	 * Text Field che visualizza percorso e nome del file di output selezionato
	 */
	private JTextField textFieldFileOutput;

	/**
	 * Controller di tutte le procedure dell'applicazione
	 */
	private RiscossioneConverter converter = new RiscossioneConverter();

	/**
	 * Main dell'applicazione
	 * 
	 * @param args Argomenti (non utilizzati)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiscossioneConverterGUISwing frame = new RiscossioneConverterGUISwing();
					frame.setResizable(false);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea la GUI Swing
	 */
	public RiscossioneConverterGUISwing() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/RiscossioneConverter.png")));
		setTitle("Riscossione Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 222);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnConverti = new JButton("Converti");
		btnConverti.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnConverti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				converter.setFileDati(new File(textFieldFileDati.getText()));
				converter.setFileOutput(new File(textFieldFileOutput.getText()));

				try {
					converter.avviaConversione();
					JOptionPane.showMessageDialog(contentPane, "Conversione completata", "Informazione",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, e1.getClass().getSimpleName() + ": " + e1.getMessage(),
							"Errore", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnConverti.setBounds(197, 138, 113, 44);
		contentPane.add(btnConverti);

		JButton btnApriFileDati = new JButton("Apri File Dati");
		btnApriFileDati.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApriFileDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Apri File Dati");
				fileChooser.setCurrentDirectory(new File(converter.getLastPathDati()));
				int result = fileChooser.showOpenDialog(contentPane);
				File selectedFile = fileChooser.getSelectedFile();
				if (result == JFileChooser.APPROVE_OPTION) {
					if (selectedFile.exists())
						converter.setLastPathDati(selectedFile.getParent());
					textFieldFileDati.setText(selectedFile.getAbsolutePath());

				}
			}
		});
		btnApriFileDati.setBounds(349, 71, 148, 25);
		contentPane.add(btnApriFileDati);

		JButton btnApriFileOutput = new JButton("Apri File di Output");
		btnApriFileOutput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApriFileOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Apri File di Output");
				fileChooser.setCurrentDirectory(new File(converter.getLastPathOutput()));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 97-2003 (*.xls)", "xls"));
				fileChooser.addChoosableFileFilter(
						new FileNameExtensionFilter("Open Document Spreadsheet (*.ods)", "ods"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML (*.xml)", "xml"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON (*.json)", "json"));
				int result = fileChooser.showSaveDialog(contentPane);
				File selectedFile = fileChooser.getSelectedFile();
				if (result == JFileChooser.APPROVE_OPTION) {
					String selectedFilter = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
					if (!selectedFile.getAbsolutePath().endsWith("." + selectedFilter))
						selectedFile = new File(selectedFile + "." + selectedFilter);

					if (selectedFile.exists())
						converter.setLastPathOutput(selectedFile.getParent());
					textFieldFileOutput.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnApriFileOutput.setBounds(349, 102, 148, 25);
		contentPane.add(btnApriFileOutput);

		textFieldFileDati = new JTextField();
		textFieldFileDati.setBounds(10, 71, 335, 25);
		contentPane.add(textFieldFileDati);
		textFieldFileDati.setColumns(10);

		textFieldFileOutput = new JTextField();
		textFieldFileOutput.setColumns(10);
		textFieldFileOutput.setBounds(10, 102, 335, 25);
		contentPane.add(textFieldFileOutput);

		JLabel lblSoftwareRealizzatoDa = new JLabel("Software realizzato da Alessio Lombardo - Gennaio 2021 - Ver 1.1");
		lblSoftwareRealizzatoDa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSoftwareRealizzatoDa.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoftwareRealizzatoDa.setBounds(10, 47, 487, 14);
		contentPane.add(lblSoftwareRealizzatoDa);

		JLabel lblTitolo = new JLabel("Convertitore da Formato Dati Riscossione a Excel, ODS, XML, JSON");
		lblTitolo.setForeground(Color.RED);
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitolo.setBounds(10, 11, 487, 14);
		contentPane.add(lblTitolo);

		JLabel lblDecreto = new JLabel(
				"come da Decreto 15/06/2015 del Ministero dell'Economia e delle Finanze - Allegati 1/2");
		lblDecreto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDecreto.setHorizontalAlignment(SwingConstants.CENTER);
		lblDecreto.setBounds(10, 28, 487, 14);
		contentPane.add(lblDecreto);
	}
}
