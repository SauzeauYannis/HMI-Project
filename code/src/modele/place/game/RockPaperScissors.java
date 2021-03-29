package modele.place.game;

import modele.Gameplay;
import modele.character.NPC;
import modele.character.Player;
import modele.Level;
import modele.place.Game;

import java.util.Scanner;

// This class is a subclass of Game
public class RockPaperScissors extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    private static final String[] ROSHAMBO = {
            "rock",
            "paper",
            "scissors"
    };
    private static final int POINT_TO_WIN = 3;

    private int playerPoint;
    private int NPCPoint;

    // Constructor
    public RockPaperScissors() {
        super("Rock paper scissors",
                "| In this game you need to beat at roshambo the man who is in front of you.\n" +
                        "| Type \"play\" to start the game.",
                new NPC("Pierre Dupuis"),
                Level.COPPER);
    }

    /**********
     * Methods
     **********/

    // To play the game
    @Override
    public void play(Player player) {
        // Init the points
        playerPoint = 0;
        NPCPoint = 0;

        // To print the game
        System.out.println("\n--- Game launched ---\n");
        System.out.println("To play, type one of this proposition :");
        for (String proposition: ROSHAMBO) {
            System.out.println("-" + proposition);
        }
        System.out.println("Good luck!");

        this.getNpc().talk("I am unbeatable in that game!\n" +
                "I take you in 3 rounds!");

        // Play while nobody have reach the number of points to win
        while (playerPoint < POINT_TO_WIN && NPCPoint < POINT_TO_WIN) {
            this.getNpc().talk("\nRo..\nSham..\nBo!");

            // To get the result of npc and player
            String playerTurn = getPlayerTurn(player);
            int NPCTurn = getNPCTurn();

            // To check the winner
            checkWinner(playerTurn, NPCTurn);

            // To print the point
            printPoint(player.getName());
            printPoint(this.getNpc().getName());
        }

        // Check the winner of the game
        if (playerPoint == POINT_TO_WIN) {
            this.win(player);
        } else {
            this.lose(player);
        }

        // To flush scanner
        Gameplay.scanner.nextLine();

        System.out.println("\n--- Game finished ---\n");
    }

    // Getters
    private int getNPCTurn() {
        int rand = (int)(Math.random()*3);

        this.getNpc().talk(ROSHAMBO[rand]);

        return rand;
    }

    private String getPlayerTurn(Player player) {
        Scanner scanner = Gameplay.scanner;

        System.out.print(player);
        String playerTurn = scanner.next();

        while (!checkPlayerTurn(playerTurn)) {
            this.getNpc().talk("You haven't the right to use this!" +
                    "\nRo..\nSham..\nBo!");
            System.out.print(player);
            playerTurn = scanner.next();
        }

        return playerTurn;
    }

    // To check check the player turn
    private boolean checkPlayerTurn(String playerTurn) {
        for (String proposition: ROSHAMBO) {
            if (playerTurn.equals(proposition)) {
                return true;
            }
        }
        return false;
    }

    // To print point of player or npc by his name
    private void printPoint(String name) {
        // To get the point of player or npc by his name
        int point;
        if (name.equals(this.getNpc().getName())) {
            point = NPCPoint;
        } else {
            point = playerPoint;
        }

        // To print the points
        System.out.println("-" +
                name +
                " : " +
                point +
                " points.");
    }

    // To check winner of the round
    private void checkWinner(String playerTurn, int NPCTurn) {
        NPC npc = this.getNpc();
        // If draw
        if (playerTurn.equals(ROSHAMBO[NPCTurn])) {
            npc.talk("Draw, I'll get you in the next round!");
        } else if (playerTurn.equals(ROSHAMBO[(NPCTurn+1)%3])) { // If player win
            npc.talk("I lose, it's impossible you cheated!");
            playerPoint++;
        } else { // If npc win
            npc.talk("I win, i'm too strong for you!");
            NPCPoint++;
        }
    }
}