package banking.ui;

import banking.ui.commands.Callback;
import banking.ui.commands.ICommand;

import java.util.Scanner;

public abstract class AbstractUserInterface {

    enum ExitContext {
        EXIT_MENU,
        EXIT_PROGRAM;
    }

    protected final ICommand<ExitContext> exitCommand = (scanner, args) -> new Callback<>(ExitContext.EXIT_PROGRAM);
    protected  final ICommand<ExitContext> backCommand = (scanner, args) -> new Callback<>(ExitContext.EXIT_MENU);

    protected final Scanner scanner;
    protected boolean finishProgram = false;
    protected boolean finishMenu = false;

    AbstractUserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void invoke();
    protected abstract void showOptions();
    protected abstract Callback getUserInput();

    public boolean isStopped() {
        return finishProgram;
    }
    public boolean isFinished() { return finishMenu; }

    public Scanner getScanner() {
        return scanner;
    }
}
