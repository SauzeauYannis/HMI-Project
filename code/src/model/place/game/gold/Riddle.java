package model.place.game.gold;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Random;
import java.util.Scanner;

// This class is a subclass of Game
public class Riddle extends Game {

    /******************************
     * Attributes and constructor *
     ******************************/

    /// Constants ///
    public final static int DEFAULT_ATTEMPTS = 3;
    private final static String[][] RIDDLES = {
            {"What is the summation between 1 and 2?", "3"},
            {"If I am mute, blind and deaf, how many senses do I have left?", "3"},
            {"If 1 equals 5, 2 equals 25, 3 equals 605, 4 equals 10855, 5 equals what?", "1"},
            {"You have this suite : 2 3 5 ? 11 ", "7"}
    };

    /// Attributes ///
    private final IntegerProperty attempts = new SimpleIntegerProperty();

    // Constructor
    public Riddle() {
        super("Riddle",
                "| Here you need to answer to a riddle. You have only " + DEFAULT_ATTEMPTS + " attempts!\n" +
                        "| Turn on your brain, and make it work hard!",
                new NPC("Jean-Pierre Fougas"),
                Level.GOLD);

    }


    /***********
     * Methods *
     ***********/

    @Override
    public void play(Player player) {
        Scanner scan = Gameplay.scanner;
        String answer;

        String[] riddle = startAndGetRiddle();

        boolean win = false;

        // while attempts isn't equal to 0
        while(!win || attempts.get() != 0){

            System.out.print(player);
            System.out.print("-> Choice (yes or no): ");
            answer = scan.nextLine().toLowerCase();

            // Check if the answer is valid or not
            if (answer.equals("yes") || answer.equals("no")) {

                // Check if the player is a joker
                if (answer.equals("no")) {
                    choseYes(false, riddle);

                    // To go out of the loop
                    attempts.set(0);
                }
                else {
                    choseYes(true, riddle);

                    // While attempts isn't equal to 0
                    while(attempts.get() != 0) {
                        System.out.print(player);
                        System.out.print("-> Answer: ");
                        answer = scan.nextLine().toLowerCase();

                        win = giveAnswer(player, answer, riddle);
                    }
                }
            }
            else {
                this.getNpc().talk("What did you said ? Sorry but I am an old man you know ... bla bla bla ...\n" +
                        "... and this is how I am became what I am today ! OH ! So-Sorry, I have started again...\n" +
                        "So ?\n");
            }
        }

        finish();
    }

    public IntegerProperty attemptsProperty() {
        return attempts;
    }

    public String[] startAndGetRiddle() {
        // Choose a random riddle
        Random rand = new Random();
        String[] riddle = RIDDLES[rand.nextInt(RIDDLES.length)];

        // Initialize the number of attempts
        this.attempts.set(DEFAULT_ATTEMPTS);

        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("Oh oh oh, greetings traveler! I hope you aren't so tired to be arrived here!\n" +
                "Because you will be probationned, by my self! You should find the right answer to my riddle.\n" +
                "But you know, I am a professional in this domain! I have 50 years... bla bla bla...\n" +
                "... but I am remember this guy, Ethoufet Kwallah, my first rival in... bla bla bla...\n" +
                "... and this is how I am became what I am today! Wait you're sleeping during my story?!\n" +
                "Ooooh, like each time. Ok so, are you ready ?\n");

        return riddle;
    }

    public void choseYes(boolean yes, String[] riddle) {
        if (yes) {
            this.getNpc().talk("Oh oh perfect! Ok ok, let's begin!\n" +
                    "Sit down, calm down and listen:\n");

            System.out.println("|[ " + riddle[0] + " ]|\n");

            this.getNpc().talk("So traveler, what is your answer? O.O");
        } else
            this.getNpc().talk("Oh...Ok...So, goodbye kid. I hope you will change\n" +
                    " your opinion, and come back traveler...");
    }

    public boolean giveAnswer(Player player, String answer, String[] riddle) {
        // Check the answer for the riddle
        if (answer.equals(riddle[1].toLowerCase())) {
            this.getNpc().talk("INCREDIBLE TRAVELER !!!!\n" +
                    "You are a true Hercule Kourge ! (it isn't Poirot ?)\n" +
                    "You have deserved your reward traveler! Take it!");
            this.win(player);

            return true;
        }

        // Check if this is the last attempt
        else if (attempts.get() > 1) {
            // Remove 1 attempt
            attempts.set(attempts.get() - 1);

            this.getNpc().talk("Ok traveler, this isn't the end ! You can take sometime\n" +
                    "to say a new proposal");

            System.out.println("| Attempts left : " + attempts.get() + "\n");
        }

        else {
            // Remove the last attempt, to arrive to 0
            attempts.set(attempts.get() - 1);

            this.getNpc().talk("Oh no traveler, this isn't the good answer ...\n" +
                    "I am so sorry to tell you this, but ...\n" +
                    "this is the end, you lose ...");

            this.lose(player);
        }

        return false;
    }

    public void finish() {
        System.out.println("\n--- Game finished ---\n");
    }
}