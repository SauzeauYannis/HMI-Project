package modele.character;

import modele.Level;
import modele.command.Interpreter;
import modele.item.Item;
import modele.item.Key;
import modele.place.Ending;
import modele.place.Game;
import modele.place.Place;
import modele.place.exit.Exit;
import modele.place.Shop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// This class is a subclass of Character
public class Player extends Character {

	/*******************************
	 * Attributes and constructors *
	 *******************************/

	/// Constants ///
	static final int MAX_HEALTH = 100;
	static final int DEFAULT_MONEY = 50;

	/// Attributes ///
	private Place cur_place;
	private final List<Item> items;
	private int health;
	private int money;
	private boolean isLose;
	private int gamesFinished;

	/// Constructors ///
	public Player(String name, Place p, int money) {
		super(name);
		this.cur_place = p;
		this.health = MAX_HEALTH;
		this.money = money;
		this.items = new ArrayList<>();
		this.items.add(new Key("Copper key", 25, Level.COPPER));
		this.items.add(new Key("Gold key", 25, Level.COPPER));
		this.items.add(new Key("Copper key", 25, Level.COPPER));
		this.isLose = false;
		this.gamesFinished = 0;
	}

	public Player(String name, Place p) {
		this(name, p, DEFAULT_MONEY);
	}


	/***********
	 * Methods *
	 ***********/

	// Getters
	public Place getPlace(){
		return this.cur_place;
	}

	public boolean getIsLose(){
		return this.isLose;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public int getHealth(){
		return this.health;
	}

	public int getGamesFinished(){
		return this.gamesFinished;
	}

	/////////////
	// Setters //
	/////////////

	// Increase the player's health remain
	public boolean increaseHealth(int health) {
		// Check if the player's health is full
		if (this.health == MAX_HEALTH) {
			System.out.println("| You're calorie are always at max");
			return false;
		}
		// Check if the player's health will be superior to the max health
		else if (this.health + health >= MAX_HEALTH){
			this.health = MAX_HEALTH;
		}
		else{
			this.health += health;
		}
		printHealth();
		return true;
	}

	// Decrease the player's health
	public void decreaseHealth(int health) {
		this.health -= health;

		// Check if the player has lost
		if(this.health <= 0){
			this.lose();
		} else {
			printHealth();
		}
	}

	// Increase the player's money
	public void earnMoney(int money) {
		this.money += money;
		printMoney();
	}

	// Decrease the player's money
	public void loseMoney(int money) {
		// Check if the player's money will be inferior to 0
		if(this.money - money <= 0){
			this.money = 0;
		}
		else{
			this.money -= money;
		}
		printMoney();
	}

	// Allows to say if the player has win
	private void lose(){
		this.isLose = true;
	}

	// Add item to the player's inventory
	public void addItem(Item item) {
		int price = item.getPrice();

		// Check if the player has any money to buy the item
		if (price > this.money) {
			System.out.println("| You haven't any money to buy this item");
		} else {
			this.items.add(item);
			System.out.println("| " + item.getName() +
					" is now in your inventory.");
			this.loseMoney(price);
		}
	}

	// Remove an item from the player's inventory
	public void removeItem(Item item) {
		this.items.remove(item);
		System.out.println("| You have lose one " +
				item.getName().toLowerCase());
	}

	// Increase the number of games finished for the first time
	public void increaseGameFinished(){
		this.gamesFinished += 1;
		printGames();
	}

	///////////
	// Print //
	///////////

	// Display the player's health remain
	public void printHealth() {
		System.out.println("| You have " +
				this.getHealth() +
				"/" +
				MAX_HEALTH +
				" calories.");
	}

	// Display the player's money
	public void printMoney() {
		System.out.println("| You have " +
				this.money +
				" coins.");
	}

	// Display how many games the player has finished
	public void printGames() {
		System.out.println("| You have finish " +
				this.gamesFinished + "/" + Game.NB_GAMES +
				" games.");
	}

	// Display what the player has in his inventory
	public void printInventory() {

		// Check if his inventory is empty
		if (this.items.isEmpty()) {
			System.out.println("| You haven't anything in your inventory!\n" +
					"| Go to the shop to buy items.");
		} else {
			// Sort by alphabetic order
			this.items.sort(Comparator.comparing(Item::getName));
			System.out.println("| In you inventory you have :");

			// Display all items in the inventory
			for (Item item: this.items) {
				System.out.println("| - " + item.getName());
			}
		}
	}

	////////////
	// Others //
	////////////

	// Check if the place is valid, and change the player's position
	public boolean goToPlace(String location) {

		// For each exit in the list
		for (Exit exit: this.cur_place.getExitList()) {
			Place place = exit.getPlace();
			String placeName = place.getName();

			// Check if the place in parameter is equal to the place of the exit
			if (location.equals(Interpreter.getFirstWord(placeName))) {

				// Check if the exit is locked
				if (exit.isLock()) {

					// Check if 'place' is an instance of 'Game' class
					if (place instanceof Game) {

						// Casting of 'place' in Game
						Game game = (Game) place;
						String level = game.getLevel().toString();
						System.out.println("| You can't go to " +
								placeName +
								", this game is locked !\n" +
								"| If you have a " +
								level +
								" key in your inventory, type \"use " +
								level +
								"\" to unlock the first lock game.\n" +
								"| Else go to the shop to buy it.");

						// Check if 'place' is an instance of Ending
					} else if (place instanceof Ending) {
						System.out.println("You don't know why but this place attracts you but it's locked.\n" +
								"Try to type \"unlock sparkling\" to to get inside.");
					}
				} else {

					// Change definitively the player's current place
					place.getNpc().talk("Welcome to " + placeName + "!");
					changePlace(place);
				}
				return true;
			}
		}
		return false;
	}

	// Change the player's current place
	private void changePlace(Place place){
		this.cur_place = place;

		// Check if 'place' is an instance of Shop
		if (place instanceof Shop) {
			Shop shop = (Shop) place;

			// Display items in the shop
			shop.printItemsList();

			// Check if 'place' is an instance of Shop
		} else if (place instanceof Ending) {
			Ending ending = (Ending) place;

			// Display the credits of the Game
			ending.printCredits();
		}
	}

	// Allows to execute the current game
	public void playGame() {

		// // Check if 'place' is an instance of Game
		if (this.cur_place instanceof Game) {
			Game game = (Game) this.cur_place;
			game.play(this);
		} else {
			System.out.println("| You are not in a game");
		}
	}
}