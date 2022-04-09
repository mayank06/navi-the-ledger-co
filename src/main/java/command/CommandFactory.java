package command;

public class CommandFactory {

    public CommandExecution getInstance(String command, String[] input) {
        CommandExecution commandExecution;
        switch (command) {
            case "LOAN":
                commandExecution = new LoanManager(input);
                break;
            case "PAYMENT":
                commandExecution = new PaymentManager(input);
                break;
            case "BALANCE":
                commandExecution = new BalanceManager(input);
                break;
            default:
                throw new IllegalStateException("Unexpected command: " + command);
        }

        return commandExecution;
    }
}
