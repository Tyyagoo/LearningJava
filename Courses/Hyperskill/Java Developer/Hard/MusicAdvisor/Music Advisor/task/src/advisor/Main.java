package advisor;

import advisor.ui.Menu;

public class Main {
    public static void main(String[] args) {
        Menu.initialize();
        while (Menu.isRunning()) {
            Menu.invoke();
        }
    }
}
