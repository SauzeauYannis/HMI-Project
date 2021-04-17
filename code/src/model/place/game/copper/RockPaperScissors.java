package model.place.game.copper;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Scanner;

// This class is a subclass of Game
public class RockPaperScissors extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    public static final String[] ROSHAMBO = {
            "rock",
            "paper",
            "scissors"
    };
    public static final int POINT_TO_WIN = 3;

    private final IntegerProperty playerPoint = new SimpleIntegerProperty();
    private final IntegerProperty NPCPoint = new SimpleIntegerProperty();

    // Constructor
    public RockPaperScissors() {
        super("Rock paper scissors",
                "| In this game you need to beat at roshambo the man who is in front of you.",
                new NPC("Pierre Dupuis"),
                Level.COPPER);
    }

    /**********
     * Methods
     **********/

    // To play the game
    @Override
    public void play(Player player) {
        start();

        System.out.println("To play, type one of this proposition :");
        for (String proposition: ROSHAMBO) {
            System.out.println("-" + proposition);
        }
        System.out.println("Good luck!");

        // Play while nobody have reach the number of points to win
        while (playerPoint.get() < POINT_TO_WIN && NPCPoint.get() < POINT_TO_WIN) {
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
        if (playerPoint.get() == POINT_TO_WIN) {
            this.win(player);
        } else {
            this.lose(player);
        }

        // To flush scanner
        Gameplay.scanner.nextLine();

        finish();
    }

    public IntegerProperty playerPointProperty() {
        return playerPoint;
    }

    public IntegerProperty NPCPointProperty() {
        return NPCPoint;
    }

    // Getters
    public int getNPCTurn() {
        int rand = (int)(Math.random()*3);

        this.getNpc().talk(ROSHAMBO[rand]);

        return rand;
    }

    public void start() {
        // Init the points
        playerPoint.set(0);
        NPCPoint.set(0);

        // To print the game
        System.out.println("\n--- Game launched ---\n");

        this.getNpc().talk("I am unbeatable in that game!\n" +
                "I take you in 3 rounds!");
    }

    // To check winner of the round
    public void checkWinner(String playerTurn, int NPCTurn) {
        NPC npc = this.getNpc();
        // If draw
        if (playerTurn.equals(ROSHAMBO[NPCTurn])) {
            npc.talk("Draw, I'll get you in the next round!");
        } else if (playerTurn.equals(ROSHAMBO[(NPCTurn+1)%3])) { // If player win
            npc.talk("I lose, it's impossible you cheated!");
            playerPoint.set(playerPoint.get()+1);
        } else { // If npc win
            npc.talk("I win, i'm too strong for you!");
            NPCPoint.set(NPCPoint.get()+1);
        }
    }

    public void finish() {
        System.out.println("\n--- Game finished ---\n");
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
            point = NPCPoint.get();
        } else {
            point = playerPoint.get();
        }

        // To print the points
        System.out.println("-" +
                name +
                " : " +
                point +
                " points.");
    }
}