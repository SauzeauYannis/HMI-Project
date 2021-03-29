package modele.command;

import modele.character.Player;
import modele.item.Item;

// This class is a subclass of Command
public class Use extends Command {

    /***************
     * Constructor *
     ***************/

    public Use() {
        super("use",
                "| use <object> : To use the object selected");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        // Check if a 2nd argument is past
        if (args.length > 1) {
            String item = args[1].toLowerCase();
            // Look each item of the player inventory
            for (Item playerItem: player.getItems()) {
                // Check if the player has the item in his inventory
                if (Interpreter.getFirstWord(playerItem.getName()).equalsIgnoreCase(item)) {
                    playerItem.use(player);
                    return;
                }
            }
            // If the player has not the item in his inventory
            System.out.println("| You have not " + item + " in your inventory");
        } else { // If there is only one argument
            System.out.println("| ou need to have a 2nd argument.\n" +
                    "| Type \"help use\" for more information.");
        }
    }
}
