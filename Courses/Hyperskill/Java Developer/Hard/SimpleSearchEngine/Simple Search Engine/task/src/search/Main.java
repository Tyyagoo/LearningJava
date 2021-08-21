package search;

public class Main {
    public static void main(String[] args) {
        Menu.initialize();
        while (Menu.isRunning()) {
            Menu.invoke();
        }
    }
}
