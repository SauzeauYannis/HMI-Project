package model.place.game;

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
    final static int DEFAULT_ATTEMPTS = 3;
    final static String[][] RIDDLES ={
            {"What is the summation between 1 and 2 ?", "3"},
            {"If I am mute, blind and deaf, how many senses do I have left ?", "3"},
            {"if 1 equals 5, 2 equals 25, 3 equals 605, 4 equals 10855, 5 equals what ?", "1"},
            {"You have this suite : 2 3 5 ? 11 ", "7"}
    };

    /// Attributes ///
    private int attempts;

    // Constructor
    public Riddle() {
        super("Riddle",
                "| Here you need to answer to a riddle. You have only " + DEFAULT_ATTEMPTS + " attempts!\n" +
                        "| Turn on your brain, and make it work hard!",
                new NPC("Jean-Pierre Fougas"),
                Level.GOLD);
        this.attempts = DEFAULT_ATTEMPTS;
    }


    /***********
     * Methods *
     ***********/

    @Override
    public void play(Player player) {

        Scanner scan = Gameplay.scanner;
        String answer;

        // Choose a random riddle
        Random rand = new Random();
        String[] riddle = RIDDLES[rand.nextInt(RIDDLES.length)];

        // Initialize the number of attempts (useless for the first time, but useful for the remain
        this.attempts = DEFAULT_ATTEMPTS;

        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("Oh oh oh, greetings traveler! I hope you aren't so tired to be arrived here!\n" +
                "Because you will be probationned, by my self! You should find the right answer to my riddle.\n" +
                "But you know, I am a professional in this domain! I have 50 years... bla bla bla...\n" +
                "... but I am remember this guy, Ethoufet Kwallah, my first rival in... bla bla bla...\n" +
                "... and this is how I am became what I am today! Wait you're sleeping during my story?!\n" +
                "Ooooh, like each time. Ok so, are you ready ?\n");

        // while attempts isn't equal to 0
        while(attempts != 0){

            System.out.print(player);
            System.out.print("-> Choice (yes or no): ");
            answer = scan.nextLine().toLowerCase();

            // Check if the answer is valid or not
            if(answer.equals("yes") || answer.equals("no")){

                // Check if the player is a joker
                if(answer.equals("no")){
                    this.getNpc().talk("Oh...Ok...So, goodbye kid. I hope you will change\n" +
                            " your opinion, and come back traveler...");

                    // To go out of the loop
                    attempts = 0;
                }
                else {
                    this.getNpc().talk("Oh oh perfect! Ok ok, let's begin!\n" +
                            "Sit down, calm down and listen:\n");

                    System.out.println("|[ " + riddle[0] + " ]|\n");

                    this.getNpc().talk("So traveler, what is your answer? O.O");

                    // While attempts isn't equal to 0
                    while(attempts != 0){
                        System.out.print(player);
                        System.out.print("-> Answer: ");
                        answer = scan.nextLine().toLowerCase();

                        // Check the answer for the riddle
                        if(answer.equals(riddle[1].toLowerCase())){
                            this.getNpc().talk("INCREDIBLE TRAVELER !!!!\n" +
                                    "You are a true Hercule Kourge ! (it isn't Poirot ?)\n" +
                                    "You have deserved your reward traveler! Take it!");
                            this.win(player);

                            // To go out of the loop
                            attempts = 0;
                        }

                        // Check if this is the last attempt
                        else if(attempts > 1){
                            // Remove 1 attempt
                            attempts -= 1;

                            this.getNpc().talk("Ok traveler, this isn't the end ! You can take sometime\n" +
                                    "to say a new proposal");
                            System.out.println("| Attempts left : " + attempts + "\n");
                        }
                        else {
                            // Remove the last attempt, to arrive to 0
                            attempts -= 1;

                            this.getNpc().talk("Oh no traveler, this isn't the good answer ...\n" +
                                    "I am so sorry to tell you this, but ...\n" +
                                    "this is the end, you lose ...");

                            this.lose(player);
                        }
                    }

                }
            }
            else{
                this.getNpc().talk("What did you said ? Sorry but I am an old man you know ... bla bla bla ...\n" +
                        "... and this is how I am became what I am today ! OH ! So-Sorry, I have started again...\n" +
                        "So ?\n");
            }
        }

        System.out.println("\n--- Game finished ---\n");
    }
}