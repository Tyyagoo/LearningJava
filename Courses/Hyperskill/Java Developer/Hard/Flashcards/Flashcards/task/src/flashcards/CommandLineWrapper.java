package flashcards;

import flashcards.ui.Menu;

public class CommandLineWrapper {

    private static String importFile = null;
    private static String exportFile = null;

    public static void initialize(String[] commandLineArgs) {
        for (int i = 0; i < commandLineArgs.length; i++) {
            if (commandLineArgs[i].equals("-import")) {
                importFile = commandLineArgs[++i];
            }

            if (commandLineArgs[i].equals("-export")) {
                exportFile = commandLineArgs[++i];
            }
        }
    }

    public static void onStart() {
        if (importFile == null) return;
        Menu.forceImport(importFile);
    }

    public static void onExit() {
        if (exportFile == null) return;
        Menu.forceExport(exportFile);
    }
}
