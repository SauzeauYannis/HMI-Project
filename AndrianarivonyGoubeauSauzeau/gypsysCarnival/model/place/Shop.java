package gypsysCarnival.model.place;

import gypsysCarnival.model.character.NPC;
import gypsysCarnival.model.item.Item;

import java.util.ArrayList;
import java.util.List;

// This class is a subclass of Place
public class Shop extends Place {

	/****************************
	 * Attribute and constructor
	 ****************************/

	// Attribute
	private final List<Item> itemList;

	// Constructor
	public Shop(String name, String description, NPC npc) {
		super(name,
				description +
						"| To have more information about one item, left click on the item in your inventory\n" +
						"| To buy an item, left click on the item in the shop",
				npc);
		this.itemList = new ArrayList<>();
	}

	/**********
	 * Methods
	 **********/

	// Getter
	public List<Item> getItemList() {
		return this.itemList;
	}

	// Setter
	public void addItem(Item item) {
		this.itemList.add(item);
	}

	// To print the items available in the shop
	public void printItemsList() {
		NPC npc = this.getNpc();

		npc.talk("Here are items available in my shop:");
		for (Item item: this.itemList) {
			item.printItem();
		}
	}
}