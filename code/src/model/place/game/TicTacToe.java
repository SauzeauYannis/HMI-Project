package model.place.game;

import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Random;
import java.util.Scanner;

// This class is a subclass of Game
public class TicTacToe extends Game {

    /******************************
     * Attributes and constructor *
     ******************************/

    /// Constants ///
    final static int NEUTRAL = 0;
    final static int CROSS = 1;
    final static int CIRCLE = 2;
    final static int DEFAULT_SIZE = 3;
    final static String[] sentences = {
            "Mmmmh, an interesting choice..",
            "Well, it's my turn now <3",
            "You know what you're doing darling!",
            "I find this party interesting.."
    };

    /// Attributes ///
    private final int[][] cases;
    private int winner;


    /// Constructor ///
    public TicTacToe() {
        super("Tic Tac Toe",
                "| This the game of the TicTacToe, you must to align three same symbols to horizontally, vertical or diagonal.",
                new NPC("Sagrat Opubice"),
                Level.GOLD);
        this.cases = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        this.winner = NEUTRAL;
    }


    /***********
     * Methods *
     ***********/

    @Override
    public void play(Player player) {
        System.out.println("\n--- Game launched ---\n");

        // Initialize the game
        this.initGame();

        // Method variables
        Scanner scan = Gameplay.scanner;
        Random rand = new Random();
        int beginner = rand.nextInt(2) + 1;
        int chosen = -1;

        this.getNpc().talk("Hello little darling,  how are you today? \n" +
                "What?! You want to play with me? Hahaha, alright darling ;)\n" +
                "We will see who begin  with a Heads or Tails! Choose 1 or 2.");

        // While chosen isn't initialize
        while(chosen == -1){
            System.out.print(player);
            System.out.print("-> Choice: ");

            // Check if the player choose only a number
            try {
                chosen = scan.nextInt();
            } catch (Exception exception) {
                scan.nextLine();
                this.getNpc().talk("You need to write a number!");
            }
        }

        // While chosen isn't equal to 1 or 2
        while (chosen < 1 || chosen > 2) {
            this.getNpc().talk("What are you going? I said choose between 1 or 2! Darling...\n" +
                    "So, which one?");

            System.out.print(player);
            System.out.print("-> Choose your case: ");

            // Check if the player choose only a number
            try {
                chosen = scan.nextInt();
            } catch (Exception exception) {
                scan.nextLine();
                this.getNpc().talk("You need to write a number!");
            }
        }

        this.getNpc().talk("Ok, so the result is...\n=> " +
                beginner + "!!");

        // Carries out the first action of the beginner
        if (chosen == beginner) {
            this.getNpc().talk("Right, you're the beginner!\n" +
                    "So you're cross, and I take circle.\n" +
                    "Now choose where you want to place you cross!");
        } else {
            this.getNpc().talk("Nice, I'm the beginner.\n" +
                    "So you're cross, and I take circle.\n" +
                    "I'll choose my case!  Good luck darling ;)");

            this.npcTurn(rand);
        }

        // While the variable 'winner' equal to NEUTRAL
        while (this.winner == NEUTRAL) {
            this.playerTurn(player, scan);

            // Checked if there is a winner
            if(this.winner == NEUTRAL){
                this.npcTurn(rand);
            }

        }

        // Check who is the winner, CROSS = player, CIRCLE = NPC
        if(winner == CROSS){
            this.getNpc().talk("Ooooh, you're pretty strong darling! <3\n" +
                    "You've deserve your reward, my little boy!\n" +
                    "I hope I can see you again, maybe to show you something... new ;)");

            this.win(player);
        }
        else {
            this.getNpc().talk("Tsss, sorry darling but you've lost this time...\n" +
                    "You're not as strong as I thought.\n" +
                    "Give me your money darling, an see you later to give it to me again ;)");

            this.lose(player);
        }

        // Prevent a bug at the end of the game
        scan.nextLine();

        System.out.println("\n--- Game finished ---\n");
    }

    // Initialize the game
    private void initGame() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                this.cases[i][j] = NEUTRAL;
            }
        }

        this.winner = NEUTRAL;
    }

    // Set the case to the coordinates [i, j], with the symbol
    //if the place is valid
    private boolean setCase(int i, int j, int symbol) {
        if (this.cases[i][j] == NEUTRAL) {
            this.cases[i][j] = symbol;

            this.isWin(i, j, symbol);

            this.displayGame();

            return true;
        }
        else{
            return false;
        }
    }

    // Check if the current player has win, with 3 sames symbols aligned
    private void isWin(int i, int j, int symbol) {
        if ((this.cases[i][0] == this.cases[i][1] && this.cases[i][1] == this.cases[i][2])  // horizontally
                ||
                (this.cases[0][j] == this.cases[1][j] && this.cases[1][j] == this.cases[2][j]) // vertically
                ||
                ((i == j) && (this.cases[0][0] == this.cases[1][1] && this.cases[1][1] == this.cases[2][2])) // first diagonal
                ||
                ((i + j == 2) && this.cases[2][0] == this.cases[1][1] && this.cases[1][1] == this.cases[0][2]) // second diagonal
        ) {
            // Change winner to the current player symbol
            this.winner = symbol;
        }
    }

    // Carry out the NPC turn
    private void npcTurn(Random rand){
        boolean stop = false;
        int i;
        int j;

        // Takes an random sentence
        String sentence = sentences[rand.nextInt(sentences.length)];

        System.out.println("\n|| NPC turn ||\n");
        this.getNpc().talk(sentence);

        // While stop equal to false
        while(!stop){

            // Takes random coordinates
            i = rand.nextInt(DEFAULT_SIZE);
            j = rand.nextInt(DEFAULT_SIZE);

            // Checked if the coordinates are valid
            if(this.setCase(i, j, CIRCLE)){
                stop = true;
            }
        }
    }

    // Carry out the player turn
    private void playerTurn(Player player, Scanner scan){
        boolean stop = false;
        int i;
        int j;

        System.out.println("\n|| Your turn ||\n" +
                "| Type a sequence like this: \"2 2\", to put your symbol to the case (2,2)");

        // While stop equal to false
        while(!stop){

            System.out.print(player);
            System.out.print("-> Choose your case: ");

            // Recovers the coordinates chosen by the player
            try {
                i = scan.nextInt()-1;
            } catch (Exception exception) {
                scan.nextLine();
                this.getNpc().talk("You need to write a number!");
                continue;
            }
            try {
                j = scan.nextInt()-1;
            } catch (Exception exception) {
                scan.nextLine();
                this.getNpc().talk("You need to write a number!");
                continue;
            }

            // Check if the coordinates are valid
            if((i<0 || i>2 || j<0 || j>2)){
                System.out.println("Please choose valid case!\n(Between 1 and 3)");
            }
            else if(!this.setCase(i, j, CROSS)){
                System.out.println("This case is already use, choose an over one");
            }
            else {
                stop = true;
            }
        }
    }

    // Display the game
    private void displayGame(){

        System.out.println();

        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                // Display the right symbol
                if(this.cases[i][j] == CROSS){
                    System.out.print(" X ");
                }
                else if(this.cases[i][j] == CIRCLE){
                    System.out.print(" O ");
                }
                else{
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
