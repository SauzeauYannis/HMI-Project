package model.place.game.platinum;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Random;
import java.util.Scanner;

// This class is a subclass of Game
public class Hangman extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // ATTRIBUTES
    public final static int NB_TRY = 5;
    private final String[] WORDS = {
            "Apple",
            "Pear",
            "Pineapple",
            "Peach",
            "Pumpkin",
            "Strawberry",
            "Apricot",
            "Eggplant",
            "Cherry",
            "Banana",
            "Avocado",
            "Lime",
            "Melon",
            "Watermelon",
            "Fig",
            "Tomato",
            "Artichoke",
            "Asparagus",
            "Broccoli",
            "Cabbage",
            "Corn",
            "Garlic",
            "Onion",
            "Leek",
            "Soy",
            "Potato",
            "Cucumber"
    };

    private IntegerProperty trialsLeft;
    private StringProperty wordToFind;

    private String word;
    private String lettersFound;
    private int trials;

    //CONSTRUCTOR
    public Hangman() {
        super("Hangman",
                "| In this game, you have to found the word the stand owner is thinking of by giving him letters.",
                new NPC("Marina Lependu"),
                Level.PLATINUM);
    }

    /**********
     * Methods
     **********/

    //OVERRIDE METHODS
    @Override
    public void play(Player player) {
        Scanner scanner = Gameplay.scanner;

        String guess;

        this.start();

        // Here starts the game loop
        while (!this.isWordFind() && (this.trialsLeft.get() > 0)) {
            startRound();

            System.out.print(player);
            guess = scanner.nextLine();

            playRound(guess);
        }

        // Checks if player wins and acts accordingly
        if (this.checkWin())
            this.win(player);
        else
            this.lose(player);

        System.out.println("\n--- Game finished ---\n");
    }

    //GETTERS
    public StringProperty getWordToFind() {
        return wordToFind;
    }

    public IntegerProperty trialsLeftProperty() {
        return trialsLeft;
    }

    //METHODS
    public void start() {
        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("ZZzzzZZZ... Wha... Sh... Hello! ^^'\n" +
                "I wasn't sleeping... Well, I'm Mrs.Lependu and welcome to my stand! \n" +
                "The hangman game here is about fruits and vegetables...\n" +
                "I hope you've reviewed your voc!\n" +
                "Ok, let's play!\n" +
                "Try to find the word... " +
                "You have " + NB_TRY + " guesses. Good luck!");

        // To choose randomly a word from the words array
        this.randWordInWORDS();

        // To show the word in underscores form
        this.wordToFind = new SimpleStringProperty(this.word.replaceAll("[A-Z]", "_"));

        // To declare variable needed for the game
        this.trialsLeft = new SimpleIntegerProperty(NB_TRY);
        this.trials = 0;

        // To store and know if a letter was already guessed
        this.lettersFound = "";
    }

    public void startRound() {
        // Displays the word to find underscored
        System.out.println(this.wordToFind.get());

        // Displays if it is not the first loop
        if (this.trialsLeft.get() != NB_TRY)
            this.getNpc().talk("You have " + this.trialsLeft.get() + " guesses left.");

        // Catches player guess' and convert it to upper case to be comparable
        this.getNpc().talk("What is your guess?");
    }

    public String playRound(String guess) {
        String result = null;

        // Catches the first char (letter) of guess and convert it to upper case to be comparable
        char letter = guess.charAt(0);
        letter = Character.toUpperCase(letter);

        // Checks if the letter was already found
        if (!isLetterFound(letter)) {
            // Check if the letter is in the word
            if ((this.word.indexOf(letter)) != -1) {
                this.getNpc().talk("Well done! " +
                        letter +
                        " is in the word!");

                for (int i = 0; i < this.word.length(); i++)
                    if (this.word.charAt(i) == letter && this.wordToFind.get().charAt(i) != letter)
                        // Adds the letter found is the right place
                        this.wordToFind.set(
                                this.wordToFind.get().substring(0, i)
                                        + letter
                                        + this.wordToFind.get().substring(i + 1)
                        );
            } else {
                this.getNpc().talk(letter + " is not in the word.");
                this.trialsLeft.set(this.trialsLeft.get() - 1);
                result = String.valueOf(letter);
            }
            this.trials++;
        }

        return result;
    }

    public boolean isWordFind() {
        return !this.wordToFind.get().contains("_");
    }

    // Checks if lettersFound contains letter
    private boolean isLetterFound(char letter) {
        if ((this.lettersFound.indexOf(letter)) != -1) {
            this.getNpc().talk("You have already guessed this letter.");
            return true;
        } else {
            // Adds the letter to lettersFound
            this.lettersFound += letter;
            return false;
        }
    }

    //Chose a random word from WORDS array
    private void randWordInWORDS() {
        Random rd = new Random();
        int rd_num = rd.nextInt(this.WORDS.length);
        this.word = WORDS[rd_num].toUpperCase();
    }

    //Checks if player wins
    private boolean checkWin() {
        if (this.trialsLeft.get() < 1) {
            this.getNpc().talk("You reached the number of incorrect guesses. Sorry you lose! :/");
            return false;
        }
        else {
            this.getNpc().talk("Here we are! The word is: " +
                    this.wordToFind.get() +
                    "\n What a winner! Work done in " +
                    this.trials +
                    " trials. Congrats dude! ;)");
            return true;
        }
    }
}