package gypsysCarnival.model.command;

import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.item.Item;
import gypsysCarnival.model.place.Place;
import gypsysCarnival.model.place.Shop;

// This class is a subclass of Command
public class Look extends Command {

    /***************
     * Constructor *
     ***************/

    public Look() {
        super("look",
                "| look <item> : To Read the description of the object or the place description if you have not specified an object");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        // If there is only one argument
        Place playerPlace = player.getPlace();
        if (args.length == 1) {
            // Read description of the place
            playerPlace.readDescription();
        } else { // Check if a 2nd argument is past
            String item = args[1].toLowerCase();
            // If the player is in a shop
            if (playerPlace instanceof Shop) {
                Shop shop = (Shop) playerPlace;
                // For each item available in the shop
                for (Item shopItem: shop.getItemList()) {
                    // If the 2nd argument is equal to an item in the shop
                    if (Interpreter.getFirstWord(shopItem.getName()).equalsIgnoreCase(item)) {
                        // Read description of the item
                        shopItem.readDescription();
                        return;
                    }
                }
                // If the item is not in the shop
                System.out.println("| This item is not in the shop");
            } else { // If the player is not in a shop
                // For each item in his inventory
                for (Item playerItem: player.getItems()) {
                    // If the 2nd argument is equal to an item in the player inventory
                    if (Interpreter.getFirstWord(playerItem.getName()).equalsIgnoreCase(item)) {
                        // Read description of the item
                        playerItem.readDescription();
                        return;
                    }
                }
                // If the item is not in the player inventory
                System.out.println("| This item is not in your inventory");
            }
        }
    }
}
