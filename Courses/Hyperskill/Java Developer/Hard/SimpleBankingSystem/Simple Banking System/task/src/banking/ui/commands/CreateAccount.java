package banking.ui.commands;


public class CreateAccount implements ICommand {

    @Override
    public void execute() {
        System.out.println("Your card has been created");
        // TODO: Display CARD INFORMATION
    }
}
