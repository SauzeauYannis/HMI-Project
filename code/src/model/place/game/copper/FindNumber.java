package model.place.game.copper;

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
    private boolean win;

    // Constructor
    public FindNumber() {
        super("Find number",
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

        this.start();

        // While the player still has attempt
        while (this.canContinue()) {
            System.out.print(player);

            // Check if the player choose only a number
            try {
                chosenNumber = scanner.nextInt();
            } catch (Exception exception) {
                scanner.nextLine();
                this.mustBeNumber();
                continue;
            }

            this.playOneTurn(player, chosenNumber);
        }

        // Prevent a bug at the end of the game
        scanner.nextLine();

        this.finish();
    }

    public IntegerProperty attemptProperty() {
        return this.attempt;
    }

    public int getRand() {
        return this.rand;
    }

    public boolean isWin() {
        return this.win;
    }

    public void start() {
        this.attempt.set(DEFAULT_ATTEMPT);
        this.rand = (int) (Math.random() * (MAX_NUMBER));
        this.win = false;

        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("You need to find my number between 0 and " + MAX_NUMBER + " in " + DEFAULT_ATTEMPT + " attempts!");
    }

    public boolean canContinue() {
        return this.attempt.get() > 0;
    }

    public void mustBeNumber() {
        this.getNpc().talk("You need to write a number!");
    }

    public boolean playOneTurn(Player player, int chosenNumber) {
        // Check if the player type a valid number
        if (chosenNumber > MAX_NUMBER || chosenNumber < 0) {
            this.getNpc().talk("Please entry a valid number!");
            return false;
        } else {
            // The player has abandoned
            if (this.attempt.get() == 1 && chosenNumber != this.rand) {
                this.getNpc().talk("The number was " + this.rand);
                this.lose(player);
                this.attempt.set(0);
            } else {
                this. attempt.set(this.attempt.get() - 1);

                //Check where is the number chosen
                if (this.rand > chosenNumber)
                    this.getNpc().talk("It's more!\n" +
                            "You only have " +
                            this.attempt.get() +
                            " attempts left!");
                else if (rand < chosenNumber)
                    this.getNpc().talk("It's less!\n" +
                            "You only have " +
                            this.attempt.get() +
                            " attempts left!");
                else {
                    this.getNpc().talk(this.rand + " is the correct number, you win, well done!");
                    this.win(player);
                    this.win = true;
                    this.attempt.set(0);
                }
            }
            return true;
        }
    }

    public void finish() {
        System.out.println("\n--- Game finished ---\n");
    }
}