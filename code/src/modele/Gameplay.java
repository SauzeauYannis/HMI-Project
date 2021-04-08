package modele;

import modele.command.Interpreter;
import modele.character.Player;
import modele.place.Place;

import java.util.List;
import java.util.Scanner;

// Main class of the program
public class Gameplay {

	/*************
	 * Attribute *
	 *************/

	public static final Scanner scanner = new Scanner(System.in);

	/****************
	 * Main methode *
	 ****************/

	public static void main(String[] args) {

		String command;

		// Generate all places in a list
		List<Place> placeList = Place.generateAllPlaces();

		// Create the player
		Player player = new Player("Benjapied Tablenuit", placeList.get(0));

		player.getPlace().getNpc().talk("Welcome to Gypsy's Carnival!\n" +
					"Type \"help\" to have the commands list.");
		player.printHealth();
		player.printMoney();
		player.printGames();

		// Ask the player to enter a command while he has not lose
		while (!player.getIsLose()) {
			System.out.print(player);
			command = scanner.nextLine();
			Interpreter.interpretCommand(player, command);
		}

		System.out.println("| Oh no you've have lose too much calories you can't continue!");
	}
}