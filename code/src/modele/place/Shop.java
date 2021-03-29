package modele.place;

import modele.character.NPC;
import modele.item.Item;

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
						"| To have more information about one item, type \"look <item>\" (Example: look apple for information about apple candy).\n" +
						"| To buy an item, type \"take <item>\" (Example: take copper to buy a copper key).",
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
		npc.talk("Type \"look\" for more information");
	}
}