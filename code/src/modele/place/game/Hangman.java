package modele.place.game;

import modele.Gameplay;
import modele.character.NPC;
import modele.character.Player;
import modele.Level;
import modele.place.Game;

import java.util.Random;
import java.util.Scanner;

// This class is a subclass of Game
public class Hangman extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/
    // ATTRIBUTES
    private final static int NB_TRY = 5;

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

    //CONSTRUCTOR
    public Hangman() {
        super("Hangman",
                "| In this game, you have to found the word the stand owner is thinking of by giving him letters.\n" +
                        "| Type \"play\" to start the game.",
                new NPC("Marina Lependu"),
                Level.PLATINUM);
    }

    /**********
     * Methods
     **********/

    //GETTERS
    private int getLengthWORDS(){
        return this.WORDS.length;
    }

    //OVERRIDE METHODS
    @Override
    public void play(Player player) {
        NPC npc = this.getNpc();
        Scanner scanner;

        System.out.println("\n--- Game launched ---\n");

        npc.talk("ZZzzzZZZ... Wha... Sh... Hello! ^^'\n" +
                "I wasn't sleeping... Well, I'm Mrs.Lependu and welcome to my stand! \n" +
                "The hangman game here is about fruits and vegetables...\n" +
                "I hope you've reviewed your voc!\n" +
                "Ok, let's play!\n" +
                "Try to find the word... " +
                "You have " + NB_TRY + " guesses. Good luck!");

        // To choose randomly a word from the words array
        String word = this.randWORDS();

        // To show the word in underscores form
        String wordToFind = word.replaceAll("[A-Z]", "_ ");

        // To declare variable needed for the game
        int trials = 0;
        int trials_left = NB_TRY;
        String guess;
        char letter;
        // To store and know if a letter was already guessed
        String lettersFound = "";
        boolean letterFound;

        // Here starts the game loop
        while (wordToFind.contains("_") && (trials_left>0)) {

            // Displays the word to find underscored
            System.out.println(wordToFind);

            // Displays if it is not the first loop
            if (trials_left != NB_TRY)
                npc.talk("You have " + trials_left + " guesses left.");

            // Catches player guess' and convert it to upper case to be comparable
            npc.talk("What is your guess?");
            System.out.print(player);
            scanner = Gameplay.scanner;
            guess = scanner.nextLine();
            guess = guess.toUpperCase();

            // Catches the first char (letter) of guess and convert it to upper case to be comparable
            letter = guess.charAt(0);
            letter = Character.toUpperCase(letter);

            // Checks if the letter was already found
            letterFound = isLetterFound(lettersFound,letter,npc);

            if (!letterFound) {
                // Adds the letter to lettersFound
                lettersFound += letter;

                // Check if the letter is in the word
                if ((word.indexOf(letter)) != -1) {
                    npc.talk("Well done! " +
                            letter +
                            " is in the word!");

                    for (int i = 0; i < word.length(); i++) {

                        if (word.charAt(i) == letter && wordToFind.charAt(i) != letter) {
                            // To have the right element in index i
                            wordToFind = wordToFind.replaceAll("_ ", "1");
                            // Adds the letter found is the right place
                            String tmp = wordToFind.substring(0, i)
                                    + letter
                                    + wordToFind.substring(i + 1);
                            tmp = tmp.replaceAll("1", "_ ");
                            wordToFind = tmp;
                        }
                    }
                } else {
                    npc.talk(letter + " is not in the word.");
                    trials_left--;
                }
                trials++;
            }
        }

        // Checks if player wins and acts accordingly
        boolean win = this.checkWin(trials_left, wordToFind,trials,npc);
        if(win) {
            this.win(player);
        }
        else {
            this.lose(player);
        }

        System.out.println("\n--- Game finished ---\n");
    }

    // Checks if lettersFound contains letter
    private boolean isLetterFound(String lettersFound, char letter, NPC npc){
        boolean letterFound;
        if ((lettersFound.indexOf(letter)) != -1) {
            npc.talk("You have already guessed this letter.");
            letterFound = true;
        } else {
            letterFound = false;
        }
        return letterFound;
    }

    //Chose a random word from WORDS array
    private String randWORDS(){
        Random rd = new Random();
        int rd_num = rd.nextInt(this.getLengthWORDS());
        String word = WORDS[rd_num];
        return word.toUpperCase();
    }

    //Checks if player wins
    private boolean checkWin(int trials_left, String wordToFind,int trials, NPC npc){
       boolean win = false;
        if (trials_left<1){
            npc.talk("You reached the number of incorrect guesses. Sorry you lose! :/");
        }
        else {
            npc.talk("Here we are! The word is: " +
                    wordToFind +
                    "\n What a winner! Work done in " +
                    trials +
                    " trials. Congrats dude! ;)");
            win = true;
        }
        return win;
    }
}