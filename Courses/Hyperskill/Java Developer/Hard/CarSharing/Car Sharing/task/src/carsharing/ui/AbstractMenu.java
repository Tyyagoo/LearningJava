package carsharing.ui;

import java.util.Scanner;

public abstract class AbstractMenu {
    protected static final Scanner scanner = new Scanner(System.in);
    protected boolean invokable = true;
    protected boolean running = true;

    protected final ICommand backCommand = () -> invokable = false;
    protected final ICommand exitCommand = () -> running = false;

    public abstract void invoke();

    public boolean isInvokable() {
        return invokable;
    }

    public boolean isRunning() {
        return running;
    }

    public int readIntegerLine() {
        return Integer.parseInt(scanner.nextLine());
    }

    public Scanner getScanner() {
        return scanner;
    }
}
