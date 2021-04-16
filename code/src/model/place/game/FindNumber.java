package model.place.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Gameplay;
import model.Level;
import model.character.NPC;
import model.character.Player;
import model.place.Game;

import java.util.Scanner;

// This class is a subclass of Game
public class FindNumber extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    public final static int MAX_NUMBER = 999;
    public final static int DEFAULT_ATTEMPT = 10;

    private final IntegerProperty attempt = new SimpleIntegerProperty(DEFAULT_ATTEMPT);
    private int rand;

    // Constructor
    public FindNumber() {
        super("Find Number",
                "| In this game you need to find a number think by the man who is in front of you.",
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
        int chosenNumber;

        start();

        // While the player still has attempt
        while (attempt.get() > 0) {

            System.out.print(player);

            // Check if the player choose only a number
            try {
                chosenNumber = scanner.nextInt();
            } catch (Exception exception) {
                scanner.nextLine();
                mustBeNumber();
                continue;
            }

            playOneTurn(player, chosenNumber);
        }

        // Prevent a bug at the end of the game
        scanner.nextLine();

        finish();
    }

    public int getRand() {
        return rand;
    }

    public IntegerProperty attemptProperty() {
        return attempt;
    }

    public void finish() {
        System.out.println("\n--- Game finished ---\n");
    }

    public void mustBeNumber() {
        this.getNpc().talk("You need to write a number!");
    }

    public void start() {
        attempt.set(DEFAULT_ATTEMPT);
        rand = (int) (Math.random() * (MAX_NUMBER));

        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("You need to find my number between 0 and " + MAX_NUMBER + " in " + DEFAULT_ATTEMPT + " attempts!");
    }


    public void playOneTurn(Player player, int chosenNumber) {
        // Check if the player type a valid number
        if (chosenNumber > MAX_NUMBER || chosenNumber < 0) {
            this.getNpc().talk("Please entry a valid number!");
        } else {
            // The player has abandoned
            if (attempt.get() == 1 && chosenNumber != rand) {
                this.getNpc().talk("The number was " + rand);
                this.lose(player);
                attempt.set(0);
            } else {
                attempt.set(attempt.get()-1);

                //Check where is the number chosen
                if (rand > chosenNumber) {
                    this.getNpc().talk("It's more!");
                    this.getNpc().talk("You only have " +
                            attempt.get() +
                            " attempts left!");
                } else if (rand < chosenNumber) {
                    this.getNpc().talk("It's less!");
                    this.getNpc().talk("You only have " +
                            attempt.get() +
                            " attempts left!");
                } else {
                    this.win(player);
                    attempt.set(0);
                }
            }
        }
    }
}