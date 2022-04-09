package command;

public class CommandManager {

    CommandFactory commandFactory = new CommandFactory();
    CommandExecution commandExecution;

    public void executeCommand(String input) {
        String[] split = input.split(" ");
        String command = split[0];
        commandExecution = commandFactory.getInstance(command, split);
        commandExecution.execute();
    }
}
