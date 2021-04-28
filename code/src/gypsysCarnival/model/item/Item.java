package gypsysCarnival.model.item;

import gypsysCarnival.model.Describable;
import gypsysCarnival.model.character.Player;

// This class is an abstract class and implements Describable interface
public abstract class Item implements Describable {

	/*****************************
	 * Attributes and constructor
	 *****************************/

	//ATTRIBUTES
	private final String name;
	private final int price;
	private final String description;

	//CONSTRUCTOR
	public Item(String name, int price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	/**********
	 * Methods
	 **********/

	//GETTERS

	public String getName() {
		return this.name;
	}

	public int getPrice() {
		return this.price;
	}

	//OTHER METHODS
	@Override
	public void readDescription() {
		System.out.println(this.description);
	}

	public abstract void use(Player player);

	public void printItem() {
		System.out.println("- " +
				this.getName() +
				" " +
				this.getPrice() +
				" coins.");
	}
}