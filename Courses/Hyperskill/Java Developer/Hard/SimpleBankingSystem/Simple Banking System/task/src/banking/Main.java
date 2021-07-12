package banking;

import banking.ui.AbstractUserInterface;
import banking.ui.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AbstractUserInterface rootMenu = new MainMenu(scanner);
        while (!rootMenu.isStopped()) {
            rootMenu.invoke();
        }
        System.out.println("Bye!");
    }
}