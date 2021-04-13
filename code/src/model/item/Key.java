package model.item;

import model.character.Player;
import model.Level;
import model.place.exit.Exit;
import model.place.Game;
import model.place.Place;

// This class is a subclass of Item
public class Key extends Item {

	/*****************************
	 * Attributes and constructor
	 *****************************/

	//ATTRIBUTES
	final private Level level;

	// CONSTRUCTOR
	public Key(String name, int price, Level level) {
		super(name,
				price,
				"TYPE: KEY\t| OBJECT: " +
						name +
						"\t| PRICE: " +
						price +
						" coins \t| USE: Unlock a " +
						level.toString() +
						" game.");
		this.level = level;
	}

	/**********
	 * Methods
	 **********/

	// GETTER
	public Level getLevel() {
		return this.level;
	}

	// OVERRIDE METHODS
	@Override
	public void use(Player player) {
		// Takes the 2nd existing exit of the place where player is (will always be a game)
		try {
			Place nextPlace = player.getPlace().getExitList().get(1).getPlace();

			// Verifies if player is in a hub
			if (nextPlace instanceof Game) {
				Game game = (Game) nextPlace;

				// Verifies if key level exists and is similar to the game level
				if (this.level == game.getLevel()) {
					// Unlocks the game
					for (Exit exit: player.getPlace().getExitList()) {
						if (exit.isLock()) {
							exit.unlock();
							player.removeItem(this);
							return;
						}
					}
					System.out.println("All games are always unlock");
				} else {
					System.out.println("You haven't a " +
							this.level.toString() +
							" key.\nGo to the shop to buy one");
				}
			} else {
				System.out.println("You need to be in front of the game to unlock it");
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("You need to be in front of the game to unlock it");
		}
	}
}