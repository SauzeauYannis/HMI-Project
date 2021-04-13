package model.command;

import model.character.Player;

import java.util.ArrayList;
import java.util.List;

// This class is an abstract class
public abstract class Command {

    /******************************
     * Attributes and constructor *
     ******************************/

    /// Attributes ///
    public static final List<Command> commandList = generateAllCommands();

    private final String command;
    private final String help;

    /// Constructor ///
    public Command(String command, String help) {
        this.command = command;
        this.help = help;
    }

    /***********
     * Methods *
     ***********/

    // Getter
    public String getCommand() {
        return command;
    }

    // To execute the command, must be override by each subclasses
    public abstract void executeCommand(Player player, String[] args);

    // To print help
    public void printHelp() {
        System.out.println(this.help);
    }

    // This static method generate all commands to return a list of them
    private static List<Command> generateAllCommands() {
        List<Command> commandList = new ArrayList<>();

        commandList.add(new Go());
        commandList.add(new Help());
        commandList.add(new Look());
        commandList.add(new Take());
        commandList.add(new Quit());
        commandList.add(new Use());
        commandList.add(new Play());
        commandList.add(new Inventory());
        commandList.add(new Unlock());

        return commandList;
    }
}
