package gypsysCarnival.model.command;

import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.item.Item;
import gypsysCarnival.model.place.Place;
import gypsysCarnival.model.place.Shop;

// This class is a subclass of Command
public class Take extends Command {

    /***************
     * Constructor *
     ***************/

    public Take() {
        super("take",
                "| take <item> : To Add the object in your inventory");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        // Check if a 2nd argument is past
        if (args.length > 1) {
            Place place = player.getPlace();
            // If the player is in a shop
            if (place instanceof Shop) {
                String item = args[1].toLowerCase();
                Shop shop = (Shop) place;
                // For each item available in the shop
                for (Item shopItem: shop.getItemList()) {
                    // If the 2nd argument is equal to an item in the shop
                    if (Interpreter.getFirstWord(shopItem.getName()).equalsIgnoreCase(item)) {
                        player.addItem(shopItem);
                        return;
                    }
                }
                // If the item is not in the shop
                System.out.println("| This item is not in the shop");
            } else { // If player is not in a shop
                System.out.println("| You need to go in a shop to take items.");
            }
        } else { // If there is only one argument
            System.out.println("| You need to have a 2nd argument.\n" +
                    "| Type \"help take\" for more information.");
        }
    }
}
