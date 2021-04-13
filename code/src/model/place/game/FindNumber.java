package model.place.game;

import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Scanner;

// This class is a subclass of Game
public class FindNumber extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    private final static int MAX_NUMBER = 999;
    private final static int DEFAULT_ATTEMPT = 10;

    // Constructor
    public FindNumber() {
        super("Find Number",
                "| In this game you need to find a number think by the man who is in front of you.\n" +
                "| Type \"play\" to start the game.",
                new NPC("Vincent Faygaf"),
                Level.COPPER);
    }

    /*********
     * Method
     *********/

    // To play the game
    @Override
    public void play(Player player) {
        // Method variables
        Scanner scanner = Gameplay.scanner;
        int attempt = DEFAULT_ATTEMPT;
        int rand = (int) (Math.random() * (MAX_NUMBER));
        int chosenNumber;

        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("You need to find my number between 0 and " + MAX_NUMBER + " in " + DEFAULT_ATTEMPT + " attempts!");

        // While the player still has attempt
        while (attempt > 0) {

            System.out.print(player);

            // Check if the player choose only a number
            try {
                chosenNumber = scanner.nextInt();
            } catch (Exception exception) {
                scanner.nextLine();
                this.getNpc().talk("You need to write a number!");
                continue;
            }

            // Check if the player type a valid number
            if(chosenNumber > MAX_NUMBER || chosenNumber < 0) {
                this.getNpc().talk("Please entry a valid number!");
            } else {
                attempt--;

                // The player has abandoned
                if (attempt == 0 && chosenNumber != rand) {
                    this.getNpc().talk("The number was " + rand);
                    this.lose(player);
                    break;
                } else {

                    //Check where is the number chosen
                    if (rand > chosenNumber) {
                        this.getNpc().talk("It's more!");
                    } else if (rand < chosenNumber) {
                        this.getNpc().talk("It's less!");
                    } else {
                        this.win(player);
                        break;
                    }
                    this.getNpc().talk("You only have " +
                            attempt +
                            " attempts left!");
                }
            }
        }

        // Prevent a bug at the end of the game
        scanner.nextLine();

        System.out.println("\n--- Game finished ---\n");
    }
}