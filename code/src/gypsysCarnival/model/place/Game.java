package gypsysCarnival.model.place;

import gypsysCarnival.model.character.NPC;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.Level;

// This class is a subclass of Place and an abstract class
public abstract class Game extends Place {

	/*****************************
	 * Attributes and constructor
	 *****************************/

	// Class attributes
	public static int NB_GAMES = 0;

	private static final int COPPER_REWARD = 50;
	private static final int GOLD_REWARD = 100;
	private static final int PLATINUM_REWARD = 200;

	// Attributes
	private final Level level;
	private final int reward;
	private boolean firstTime;

	// Constructor
	public Game(String name, String description, NPC npc, Level level) {
		super(name, description, npc);
		this.level = level;
		switch (this.level) {
			case COPPER:
				this.reward = COPPER_REWARD;
				break;
			case GOLD:
				this.reward = GOLD_REWARD;
				break;
			default:
				this.reward = PLATINUM_REWARD;
		}
		this.firstTime = true;
		NB_GAMES++;
	}

	/**********
	 * Methods
	 **********/

	// Getter
	public Level getLevel() {
		return this.level;
	}

	// To play the game, must be override by each subclasses
	public abstract void play(Player player);

	// To reward the winner with a predefined amount of money
	public void win(Player player, int money) {
		System.out.println("| You win a " + this.level.toString().toLowerCase() + " game!\n" +
				"| Here are " + money + " coins to reward you!");

		// Add money to the winner
		player.earnMoney(money);

		// Add the game like a finish game
		if(this.firstTime){
			player.increaseGameFinished();
			this.firstTime = false;
		}
	}

	// To reward the winner with a random amount of money
	public void win(Player player) {
		// Generate a random amount of money
		int money = (int) (Math.random() * this.reward);

		win(player, money);
	}

	// To reduce the loser calories
	public void lose(Player player) {
		// Generate a random amount of calories
		int losingCalories = 10 + (int) (Math.random() * 10);

		System.out.println("| You lose a " + this.level.toString().toLowerCase() + " game!\n" +
				"| You lose " + losingCalories + " calories!");

		// Reduce the health
		player.decreaseHealth(losingCalories);
	}
}