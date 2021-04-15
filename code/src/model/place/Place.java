package model.place;

import model.command.Interpreter;
import model.Level;
import model.item.Food;
import model.item.Key;
import model.Describable;
import model.character.NPC;
import model.place.exit.Exit;
import model.place.game.*;

import java.util.ArrayList;
import java.util.List;

// This class implements an interface to force use a description and implements Describable interface
public class Place implements Describable {

	/*****************************
	 * Attributes and constructor
	 *****************************/

	// Attributes
	private final String name;
	private final String description;
	private final NPC npc;
	private final List<Exit> exitList;

	// Constructor
	public Place(String name, String description, NPC npc) {
		this.name = name;
		this.description = description;
		this.npc = npc;
		this.exitList = new ArrayList<>();
	}

	/**********
	 * Methods
	 **********/

	// Getters
	public String getName() {
		return this.name;
	}

	public NPC getNpc() {
		return this.npc;
	}

	public List<Exit> getExitList() {
		return this.exitList;
	}

	// Setters
	public void addExit(Exit exit) {
		this.exitList.add(exit);
	}

	// Override the method of Describable interface
	@Override
	public void readDescription() {
		System.out.println(this.description);
	}

	// To print the places accessible from the current place
	public void printExitPlaces() {
		List<Exit> exitList = this.exitList;

		System.out.println("| You can go to :");
		// Print each place of the exit in the exit list attribute
		for (Exit exit : exitList) {
			Place place = exit.getPlace();
			System.out.print("| - " + place.getName());
			if (exit.isLock()) {
				System.out.println(" [lock]");
			} else {
				System.out.println(" [unlock]");
			}
		}

		// Print a advice to help the player
		String hintPlace = exitList.get(0).getPlace().getName();
		System.out.println("| Try to typing \"go " +
				Interpreter.getFirstWord(hintPlace) +
				"\" to go to " +
				hintPlace
		);
	}

	// This static method generate all places to return a list of them
	public static List<Place> generateAllPlaces() {
		List<Place> placeList = new ArrayList<>();

		NPC npcMain = new NPC("Gesui'hun Guyde");
		NPC npcShop = new NPC("Gesui'hun Von Deur");

		// Create instance of each place and add to the list

		// Carnival
		placeList.add(new Place("Carnival",
						"| This is the principal place of the carnival",
						npcMain));

		// Hubs
		placeList.add(new Place("Copper hub",
						"| This is the place where you can choose to go to a copper Game",
						npcMain));
		placeList.add(new Place("Gold hub",
						"| This is the place where you can choose to go to a gold Game",
						npcMain));
		placeList.add(new Place("Platinum hub",
						"| This is the place where you can choose to go to a platinum Game",
						npcMain));

		// Key shop
		Shop keyShop = new Shop("Key shop",
				"| This is the place where you can buy key to unlock different games.\n",
				npcShop);
		keyShop.addItem(new Key("Copper key", 25, Level.COPPER));
		keyShop.addItem(new Key("Gold key", 50, Level.GOLD));
		keyShop.addItem(new Key("Platinum key", 100, Level.PLATINUM));
		placeList.add(keyShop);

		// Food shop
		Shop foodShop = new Shop("Food shop",
				"| This is the place where you can buy some food, like Apple Candy.\n",
				npcShop);
		foodShop.addItem(new Food("Apple candy", 5, 5));
		foodShop.addItem(new Food("Cotton candy", 10, 15));
		foodShop.addItem(new Food("Chocolate eclair", 15, 20));
		placeList.add(foodShop);

		// Copper games
		placeList.add(new FindNumber());
		placeList.add(new QTE());
		placeList.add(new RockPaperScissors());

		// Gold games
		placeList.add(new HanoiTower());
		placeList.add(new Riddle());
		placeList.add(new TicTacToe());

		// Platinum games
		placeList.add(new Hangman());
		placeList.add(new Karaoke());
		placeList.add(new Questions());

		// Last place
		placeList.add(new Ending());

		// Generate the exits
		List<Exit> exitList = Exit.generateAllExits(placeList);

		int carnival = 0;
		int copperHub = 1;
		int goldHub = 2;
		int platinumHub = 3;

		// Add exit for each place
		for (Place place: placeList) {
			// If the place is a shop
			if (place instanceof Shop) {
				// Exit to carnival
				place.addExit(exitList.get(carnival));
			} else if (place instanceof Game) { // If the place is a game
				Game game = (Game) place;
				// Add exit according to the game level
				switch (game.getLevel()) {
					case COPPER:
						// Exit to copper hub
						place.addExit(exitList.get(copperHub));
						break;
					case GOLD:
						// Exit to gold hub
						place.addExit(exitList.get(goldHub));
						break;
					default:
						// Exit to platinum hub
						place.addExit(exitList.get(platinumHub));
				}
			} else { // If place is not a game nor a shop
				// For each exit
				for (Exit exit: exitList) {
					Place placeExit = exit.getPlace();
					// If exit to a game
					if (placeExit instanceof Game) {
						Game game = (Game) placeExit;
						// If place is copper hub
						if (place.equals(placeList.get(copperHub))) {
							// Exit to the copper games
							if (game.getLevel() == Level.COPPER) {
								place.addExit(exit);
							}
						} else if (place.equals(placeList.get(goldHub))) { // If place is gold hub
							// Exit to the gold games
							if (game.getLevel() == Level.GOLD) {
								place.addExit(exit);
							}
						} else if (place.equals(placeList.get(platinumHub))) { // If place is platinum hub
							// Exit to the platinum games
							if (game.getLevel() == Level.PLATINUM) {
								place.addExit(exit);
							}
						}
					} else { // If exit to not a game
						// If exit to the main place
						if (placeExit.equals(placeList.get(carnival))) {
							// If it's not the main place
							if (!place.equals(placeList.get(carnival))) {
								place.addExit(exit);
							}
						} else { // If not exit to the main place
							// If it's the main place
							if (place.equals(placeList.get(carnival))) {
								place.addExit(exit);
							}
						}
					}
				}
			}
		}

		return placeList;
	}
}