package model.command;

import model.character.Player;
import model.place.exit.Exit;
import model.item.Item;
import model.item.Key;
import model.place.Ending;
import model.place.Game;
import model.place.Place;

import java.util.List;

// This class is a subclass of Command
public class Unlock extends Command{

    /***************
     * Constructor *
     ***************/

    public Unlock() {
        super("unlock",
                "| unlock <game> : to unlock the game sectioned");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        // Check if a 2nd argument is past
        if (args.length > 1) {
            // Get exit list of the player place
            List<Exit> exitList = player.getPlace().getExitList();
            // For each exit in the exit list of the place
            for (Exit exit: exitList) {
                // If the second argument is equal to the place name associated to the exit
                if (Interpreter.getFirstWord(exit.getPlace().getName()).equalsIgnoreCase(args[1])) {
                    // If exit is lock
                    if (exit.isLock()) {
                        Place place = exit.getPlace();
                        // If place is a game
                        if (place instanceof Game) {
                            Game game = (Game) place;
                            // Check if the player has a key
                            for (Item item: player.getItems()) {
                                // If player has a key
                                if (item instanceof Key) {
                                    Key key = (Key) item;
                                    // If the key level is equals to the game level
                                    if (key.getLevel() == game.getLevel()) {
                                        // Unlock the exit and remove the key of the player inventory
                                        exit.unlock();
                                        player.removeItem(item);
                                        // Stop the function
                                        return;
                                    }
                                }
                            }
                            // If the player hasn't a key of the same level of the game
                            System.out.println("| You haven't a " + game.getLevel().toString() + " key.\n" +
                                    "| Go to the shop to buy one.");
                            return;
                        } else if (place instanceof Ending) { // Else if a place is a Ending
                            // If player has finished all the games
                            if (player.getGamesFinished() == Game.NB_GAMES) {
                                // Unlock the exit
                                exit.unlock();
                                System.out.println("| Congratulations, you've unlocked all the locations.");
                            } else { // If player has not finished all the games
                                System.out.println("| This place is unlockable only when you have won all the games.");
                            }
                            return;
                        }
                    } else { // If the exit is not lock
                        System.out.println("| This place is always unlock!");
                        return;
                    }
                }
            }
            // If the second argument is equal to the place name associated to the exit
            System.out.println("| Unknown place.\n" +
                    "| Type \"go\" to have the list of place you can go.");
        } else { // If there is only one argument
            System.out.println("| You need to have a 2nd argument.\n" +
                    "| Type \"help unlock\" for more information.");
        }
    }
}
