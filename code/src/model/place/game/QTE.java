package model.place.game;

import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// This class is a subclass of Game
public class QTE extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    private static final int ROUND_NUMBER = 3;
    private static final String[] NPC_TALK = {
            "Start slowly with this first punch!\n",
            "Okay now faster with this second punch!\n",
            "You're fast but you can't be more fast for this last punch!\n"
    };
    private static final String[] PUNCHLINE = {
            "My name is Ethoufet Kwallah the fast one",
            "You eat chocopipe during i eat sausage",
            "I rap faster than you and Eminem"
    };
    private static final int[] TIME = {
            20,
            15,
            10
    };

    // Constructor
    public QTE() {
        super("QTE",
                "| You are in front of the faster gipsy's rapper.\n" +
                "| Type \"play\" to start the game.",
                new NPC("Ethoufet Kwallah"),
                Level.COPPER);
    }

    /*********
     * Method
     *********/

    // To play the game
    @Override
    public void play(Player player) {
        // Method variables
        NPC npc = this.getNpc();
        int round = 0;

        System.out.println("\n--- Game launched ---\n");

        npc.talk("Yo! Welcome to my stand bro!\n" +
                "I am the fastest rapper of this carnival!\n" +
                "Try to repeat that I say in a limited time!");

        // To play 3 rounds
        while (round < ROUND_NUMBER) {

            // Print the punchline of the current round
            printPunchline(round);

            // To know the number of seconds the player has write the punchline
            Date start = new Date();
            System.out.print(player);
            String playerSentence = Gameplay.scanner.nextLine();
            Date end = new Date();
            int second = (int)((end.getTime() - start.getTime()) / 1000);

            // If the player lose then finish game
            if (!winRound(playerSentence, round, second)) {
                this.lose(player);
                return;
            } else { // Else if he wins then next round
                round++;
            }
        }

        // If the player wins the 3 rounds
        this.win(player);

        System.out.println("\n--- Game finished ---\n");
    }

    // To print the punchline
    private void printPunchline(int round) {
        // Method variable
        NPC npc = this.getNpc();

        npc.talk(NPC_TALK[round] +
                "You have " + TIME[round] + "s to write the punch, good luck!");

        // 3 second countdown
        for (int i = ROUND_NUMBER; i > 0; i--) {
            // Catch InterruptedException caused by sleep
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception exception) {
                System.err.println("Error during sleep program in QTE");
            }
            npc.talk(Integer.toString(i));
        }
        npc.talk(PUNCHLINE[round]);
    }

    // To check if the player win or not
    private boolean winRound(String playerSentence, int round, int second) {
        // Method variable
        NPC npc = this.getNpc();

        // If player writes the good punchline
        if (playerSentence.equalsIgnoreCase(PUNCHLINE[round])) {
            // If player is faster than NPC, he wins the round
            if (second < TIME[round]) {
                npc.talk(second +
                        "s.\nWell played for this round!");
                return true;
            } else { // If player is too slow for NPC, he loses the round
                npc.talk(second +
                        "s.\nToo slow for me!");
                return false;
            }
        } else { // If player doesn't write the good punchline
            npc.talk("It was not my punch!\n" +
                    "I am the best!");
            return false;
        }
    }
}