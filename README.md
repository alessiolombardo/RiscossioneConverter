# RiscossioneConverter

RiscossioneConverter consente di convertire in file Excel 97/2003 (.xls) un file a blocchi contenente record a lunghezza fissa secondo le specifiche degli [Allegati 1/2 del Decreto 15/06/2015 del Ministero dell'Economia e delle Finanze](
https://www.gazzettaufficiale.it/atto/serie_generale/caricaDettaglioAtto/originario?atto.dataPubblicazioneGazzetta=2015-06-22&atto.codiceRedazionale=15A04675&elenco30giorni=false) inerente la trasmissione agli enti creditori delle quote annullate e di rimborso agli agenti della riscossione delle spese esecutive sostenute per i ruoli.

Interamente sviluppato in Java e con interfaccia grafica minimale disponibile in due versioni:
- Swing: Testata su Windows XP (con JRE7), Windows 7 (con JRE8), Windows 10 (con JDK15).
- JavaFX: Testata su Windows 7 (con JRE8), Windows 10 (con JDK15). Windows XP non supportato. NOTA: Ove la libreria JavaFX non fosse già integrata nel JDK/JRE, specificarla avviando il jar con:
```
java --module-path <PERCORSO LIBRERIA JAVAFX> --add-modules=javafx.controls,javafx.fxml -jar <NOME JAR>
```

L'approccio di programmazione MVC consente facilmente di estrarre/modificare/sostituire il modello dei dati, il formato di output o l'interfaccia grafica.

Se viene selezionato un file Excel non vuoto, i record convertiti vengono inseriti in coda, senza alterare i record precedenti.
