package gypsysCarnival.model.item;

import gypsysCarnival.model.character.Player;

// This class is a subclass of Item
public class Food extends Item {

	/*****************************
	 * Attributes and constructor
	 *****************************/

	//ATTRIBUTES
	private final int calorie;

	// CONSTRUCTOR
	public Food(String name, int price, int calorie) {
		super(name,
				price,
				"TYPE: FOOD\t| OBJECT: " +
						name +
						"\t| PRICE: " +
						price +
						" coins \t| USE: +" +
						calorie +
						" to calorie");
		this.calorie = calorie;
	}

	/**********
	 * Methods
	 **********/

	//OVERRIDE METHODS
	@Override
	public void use(Player player) {
		if (player.increaseHealth(this.calorie)) {
			player.removeItem(this);
		}
	}
}