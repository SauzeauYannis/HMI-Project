package modele.command;

import modele.character.Player;

// This class is a subclass of Command
public class Quit extends Command {

    /***************
     * Constructor *
     ***************/

    public Quit() {
        super("quit",
                "| quit : To quit the game");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        System.exit(0);
    }
}
