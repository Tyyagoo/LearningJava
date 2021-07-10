package banking.ui.commands;

public class LoginAccount implements ICommand {

    @Override
    public void execute() {
        System.out.println("Enter your card number:");

        System.out.println("Enter your PIN:");
    }
}
