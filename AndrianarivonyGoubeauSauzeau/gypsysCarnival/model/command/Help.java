package gypsysCarnival.model.command;

import gypsysCarnival.model.character.Player;
import java.util.List;

// This class is a subclass of Command
public class Help extends Command{

    /***************
     * Constructor *
     ***************/

    public Help() {
        super("help",
                "| help <command> : To have the list of the available command or to have an help on a specified command");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        List<Command> commandList = Command.commandList;
        // Check if a 2nd argument is past
        if (args.length > 1) {
            // For each command available
            for (Command command: commandList) {
                // If command equals the 2nd argument
                if (command.getCommand().equals(args[1].toLowerCase())) {
                    // Print command help
                    command.printHelp();
                    break;
                }
            }
        } else { // If there is only one argument
            // Print the command list
            System.out.println("| Command list :");
            for (Command command: commandList) {
                System.out.println("| - " + command.getCommand());
            }
            System.out.println("| Type \"help <command>\" to more information");
        }
    }
}
