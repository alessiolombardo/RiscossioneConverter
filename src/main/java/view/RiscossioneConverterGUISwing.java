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

/**
 * 
 * GUI Swing (compatibile con Java 7)
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
	 * Text Field che visualizza percorso e nome del file Excel selezionato
	 */
	private JTextField textFieldFileExcel;

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
		setBounds(100, 100, 470, 222);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnConverti = new JButton("Converti");
		btnConverti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				converter.setFileDati(new File(textFieldFileDati.getText()));
				converter.setFileExcel(new File(textFieldFileExcel.getText()));

				try {
					converter.avviaConversione();
					JOptionPane.showMessageDialog(contentPane, "Conversione completata", "Informazione",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnConverti.setBounds(171, 134, 113, 44);
		contentPane.add(btnConverti);

		JButton btnApriFileDati = new JButton("Apri File Dati");
		btnApriFileDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
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
		btnApriFileDati.setBounds(326, 71, 118, 23);
		contentPane.add(btnApriFileDati);

		JButton btnApriFileExcel = new JButton("Apri File Excel");
		btnApriFileExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(converter.getLastPathExcel()));
				fileChooser.setFileFilter(new FileNameExtensionFilter("File Excel (*.xls)", "xls"));
				int result = fileChooser.showSaveDialog(contentPane);
				File selectedFile = fileChooser.getSelectedFile();
				if (result == JFileChooser.APPROVE_OPTION) {
					if (!selectedFile.getAbsolutePath().endsWith(".xls"))
						selectedFile = new File(selectedFile.getAbsolutePath() + ".xls");
					if (selectedFile.exists())
						converter.setLastPathExcel(selectedFile.getParent());
					textFieldFileExcel.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnApriFileExcel.setBounds(326, 102, 118, 23);
		contentPane.add(btnApriFileExcel);

		textFieldFileDati = new JTextField();
		textFieldFileDati.setBounds(10, 71, 306, 23);
		contentPane.add(textFieldFileDati);
		textFieldFileDati.setColumns(10);

		textFieldFileExcel = new JTextField();
		textFieldFileExcel.setColumns(10);
		textFieldFileExcel.setBounds(10, 102, 306, 23);
		contentPane.add(textFieldFileExcel);

		JLabel lblSoftwareRealizzatoDa = new JLabel(
				"Software realizzato da Alessio Lombardo - Dicembre 2020 - Ver 1.0");
		lblSoftwareRealizzatoDa.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoftwareRealizzatoDa.setBounds(10, 47, 434, 14);
		contentPane.add(lblSoftwareRealizzatoDa);

		JLabel lblTitolo = new JLabel("Convertitore da Formato Dati Riscossione a Excel");
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitolo.setBounds(10, 11, 434, 14);
		contentPane.add(lblTitolo);

		JLabel lblDecreto = new JLabel(
				"come da Decreto 15/06/2015 del Ministero dell'Economia e delle Finanze - Allegati 1/2");
		lblDecreto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDecreto.setHorizontalAlignment(SwingConstants.CENTER);
		lblDecreto.setBounds(10, 28, 434, 14);
		contentPane.add(lblDecreto);
	}
}
