package model.place.game.platinum;

import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Random;
import java.util.Scanner;

// This class is a subclass of Game
public class Karaoke extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    //ATTRIBUTES
    private final static int NB_TRY = 3;

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

    //OVERRIDE METHODS
    @Override
    public void play(Player player) {
        NPC npc = this.getNpc();
        Scanner scanner;

        System.out.println("\n--- Game launched ---\n");

        npc.talk("Laddies and Gentlemen, welcome to my stand! " +
                "Here your music culture would be roughly tested...\n" +
                "You have " + NB_TRY + " guesses. Good luck!\n" +
                "Get ready, get set, go!\n");

        // To choose randomly a lyrics and the word to find associated from the words array
        int index = randNum(this.getLengthSENTENCES());
        String sentence = LYRICS[index][0];
        String word = LYRICS[index][1];
        word = word.toUpperCase();

        // To declare variable needed for the game
        int trials_left = NB_TRY;
        String guess = "";

        // Here starts the game loop
        while ((guess.compareTo(word)!=0) && (trials_left>0)) {

            // Displays when it's not the first loop
            if (trials_left != NB_TRY) {
                npc.talk(guess + " is not what I expected... Maybe a typing mistake?");
                npc.talk("You still have " + trials_left + " trial(s) to find it. Think carefully...\n");
            }

            npc.talk("Find the words to complete the song :" );
            System.out.println("\"" + sentence + "\"");

            // Displays a commentary from NPC if it's the first loop
            if (trials_left == NB_TRY) {
                int rd_index = randNum(this.getLengthCOMMENTARY());
                npc.talk(COMMENTARY[rd_index]);
            }

            // Catches player guess' and convert it to upper case to be comparable
            npc.talk("What is your guess ?");
            System.out.print(player);
            scanner = Gameplay.scanner;
            guess = scanner.nextLine();
            guess = guess.toUpperCase();

            trials_left--;
        }

        // Test if player wins and acts accordingly
        boolean win = this.checkWin(trials_left, word,npc);
        if(win)
            this.win(player);
        else
            this.lose(player);

        System.out.println("\n--- Game finished ---\n");
    }

    //Provides a random number
    private int randNum(int length){
        Random rd = new Random();
        rd.nextInt(length);
        return rd.nextInt(length);
    }

    //Checks if player wins
    private boolean checkWin(int trials_left, String word, NPC npc){
        boolean win = false;
        if (trials_left<1){
            npc.talk("Sorry for your lack of culture :/ (You're a miss...)");
        }
        else {
            npc.talk("Here we are! \"" +
                    word + "\" was the missing lyric." +
                    "\n Hell yeah, you find it ! What a singer ! ;)");
            win = true;
        }
        return win;
    }
}