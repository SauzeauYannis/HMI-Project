package model.character;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.command.Interpreter;
import model.item.Item;
import model.place.Ending;
import model.place.Game;
import model.place.Place;
import model.place.Shop;
import model.place.exit.Exit;

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
	private final IntegerProperty health;
	private final IntegerProperty money;
	private final IntegerProperty gamesFinished;
	private final BooleanProperty isLose;

	/// Constructors ///
	public Player(String name, Place p, int money) {
		super(name);
		this.cur_place = p;
		this.money = new SimpleIntegerProperty(money);
		this.health = new SimpleIntegerProperty(MAX_HEALTH);
		this.gamesFinished = new SimpleIntegerProperty(0);
		this.isLose = new SimpleBooleanProperty(false);
		this.items = new ArrayList<>();
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

	public List<Item> getItems() {
		return this.items;
	}

	public IntegerProperty getMoney()
	{
		return this.money;
	}

	public IntegerProperty getHealth(){
		return this.health;
	}

	public IntegerProperty getGamesFinished(){
		return this.gamesFinished;
	}

	public BooleanProperty getIsLose(){
		return this.isLose;
	}

	/////////////
	// Setters //
	/////////////

	// Increase the player's health remain
	public boolean increaseHealth(int health) {
		// Check if the player's health is full
		if (this.health.get() == MAX_HEALTH) {
			System.out.println("| You're calorie are always at max");
			return false;
		}
		// Check if the player's health will be superior to the max health
		else if (this.health.get() + health >= MAX_HEALTH){
			this.health.set(MAX_HEALTH);
		}
		else{
			this.health.set(this.health.get() + health);
		}
		printHealth();
		return true;
	}

	// Decrease the player's health
	public void decreaseHealth(int health) {
		this.health.set(this.health.get() - health);

		// Check if the player has lost
		if (this.health.get() <= 0){
			this.lose();
		} else {
			printHealth();
		}
	}

	// Increase the player's money
	public void earnMoney(int money) {
		this.money.set(
				this.money.get() + money
		);
		printMoney();
	}

	// Decrease the player's money
	public void loseMoney(int money) {
		// Check if the player's money will be inferior to 0
		this.money.set(Math.max(this.money.get() - money, 0));
		printMoney();
	}

	// Allows to say if the player has win
	private void lose(){
		this.isLose.setValue(true);
	}

	// Add item to the player's inventory
	public void addItem(Item item) {
		int price = item.getPrice();

		// Check if the player has any money to buy the item
		if (price > this.money.get()) {
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
		this.gamesFinished.set(this.gamesFinished.get() + 1);
		printGames();
	}

	///////////
	// Print //
	///////////

	// Display the player's health remain
	public void printHealth() {
		System.out.println("| You have " +
				this.getHealth().get() +
				"/" +
				MAX_HEALTH +
				" calories.");
	}

	// Display the player's money
	public void printMoney() {
		System.out.println("| You have " +
				this.money.get() +
				" coins.");
	}

	// Display how many games the player has finished
	public void printGames() {
		System.out.println("| You have finish " +
				this.gamesFinished.get() + "/" + Game.NB_GAMES +
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
								" key in your inventory, right click on it to unlock the first lock game.\n" +
								"| Else go to the shop to buy it.");

						// Check if 'place' is an instance of Ending
					} else if (place instanceof Ending) {
						System.out.println("You don't know why but this place attracts you but it's locked.");
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