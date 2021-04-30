package gypsysCarnival.model.place.game.platinum;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import gypsysCarnival.model.Gameplay;
import gypsysCarnival.model.Level;
import gypsysCarnival.model.character.NPC;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.place.Game;
import java.util.Random;
import java.util.Scanner;

// This class is a subclass of Game
public class Karaoke extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    //ATTRIBUTES
    private final static int NB_TRY = 3;

    private String word;
    private String sentence;
    private IntegerProperty left_trials = new SimpleIntegerProperty(NB_TRY);
    private String guess;

    private final String[][] LYRICS = {
            {"I believe I can fly! I believe I can touch the ___!","sky"},
            {"___ _ ____ meet you! And this is crazy!", "Hey i just"},
            {"Boom! Boom! Boom! Boom! I want you in my ____ !", "room"},
            {"Above us only sky. _______ all the people living for today","Imagine"},
            {"____ ___ my lover! ____ ___ my friend", "Good bye"},
            {"Oh, I'll ____ ___ ___ ____ __ when I see you again. When I see you again!", "tell you all about it" },
            {"Oh no, not I, I will survive! Oh, as long as I know ___ __ ____, I know I'll stay alive", "how to love"},
    };

    private final String[] COMMENTARY = {
            "(This one is unmissable!)",
            "(Singing this one since my mother's womb!)",
            "(It's a gift...)",
            "(Seriously? I could guess without listening!)",
            "(My dad taught me this one.)",
            "(It's my grandma's favorite song!)",
            "(It's a classic one.)"
    };

    //CONSTRUCTOR
    public Karaoke() {
        super("Karaoke",
                "| In this game, your music culture will be useful to find out the missing lyrics.",
                new NPC("Kharra Okey"),
                Level.PLATINUM);
    }

    /**********
     * Methods
     **********/

    //GETTERS
    private int getLengthSENTENCES(){
        return this.LYRICS.length;
    }

    private int getLengthCOMMENTARY(){
        return this.LYRICS.length;
    }

    public IntegerProperty getLeftTrials() {
        return this.left_trials;
    }

    public String getWord() {
        return word;
    }

    public String getSentence() {
        return sentence;
    }

    public String getGuess() {
        return guess;
    }

    //SETTERS

    public void setGuess(String guess) {
        this.guess = guess.toUpperCase();
    }

    //OVERRIDE METHODS
    @Override
    public void play(Player player) {
        Scanner scanner;

        //Initialize the game
        start();

        // Here starts the game loop
        while (continueGame()) {

            // Displays when it's not the first loop
            reactionCommentary();

            this.getNpc().talk("Find the words to complete the song :" );
            System.out.println("\"" + sentence + "\"");

            // Displays a commentary from NPC if it's the first loop
            firstCommentary();

            // Displays NPC's question
            eachTrialQuestion();

            // Catches player guess' and convert it to upper case to be comparable
            System.out.print(player);
            scanner = Gameplay.scanner;
            setGuess(scanner.nextLine());

            // Leads to the next trials
            nextTrial();
        }

        // Ends the game
        end(player);

        // To flush scanner
        Gameplay.scanner.nextLine();

        System.out.println("\n--- Karaoke finished ---\n");
    }

    //METHODS TO PLAY

    //Initializes the game
    public void start() {
        System.out.println("\n--- Karaoke launched ---\n");

        left_trials.set(NB_TRY);
        guess = "";

        // To choose randomly a lyrics and the word to find associated from the words array
        int index = randNum(this.getLengthSENTENCES());
        sentence = LYRICS[index][0];
        word = LYRICS[index][1];
        word = word.toUpperCase();



        this.getNpc().talk("Laddies and Gentlemen, welcome to my stand! " +
                "Here your music culture would be roughly tested...\n" +
                "You have " + NB_TRY + " guesses. Good luck!\n" +
                "Get ready, get set, go!\n");

    }

    // Checks if player wins and acts accordingly
    public boolean end(Player player) {
        if(this.checkWin()) {
            this.win(player);
            return true;
        }
        else {
            this.lose(player);
            return false;
        }
    }

    public boolean continueGame() {
        return (guess.compareTo(word)!=0) && (left_trials.get() >0);
    }

    //Makes first commentary from NPC
    public void firstCommentary() {
        if (left_trials.get() == NB_TRY) {
            int rd_index = randNum(this.getLengthCOMMENTARY());
            this.getNpc().talk(COMMENTARY[rd_index]);
        }
    }

    //Makes reaction commentary from NPC about player response
    public void reactionCommentary() {
        if (left_trials.get() != NB_TRY) {
            this.getNpc().talk(guess + " is not what I expected... Maybe a typing mistake?");
            this.getNpc().talk("You still have " + left_trials.get() + " trial(s) to find it. Think carefully...\n");
        }
    }

    // Displays NPC's question on each trial
    public void eachTrialQuestion() {
        this.getNpc().talk("What is your guess ?");
    }

    // Goes on next trial
    public void nextTrial() {
        left_trials.setValue(left_trials.get()-1);
    }

    //Provides a random number
    private int randNum(int length){
        Random rd = new Random();
        rd.nextInt(length);
        return rd.nextInt(length);
    }

    //Checks if player wins
    private boolean checkWin(){
        if (left_trials.get() <1){
            this.getNpc().talk("Sorry for your lack of culture :/ (You're a miss...)");
            return false;
        }
        else {
            this.getNpc().talk("Here we are! \"" +
                    word + "\" was the missing lyric." +
                    "\n Hell yeah, you find it ! What a singer ! ;)");
            return true;
        }
    }
}