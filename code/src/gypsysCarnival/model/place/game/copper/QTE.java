package gypsysCarnival.model.place.game.copper;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import gypsysCarnival.model.Gameplay;
import gypsysCarnival.model.character.NPC;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.Level;
import gypsysCarnival.model.place.Game;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// This class is a subclass of gypsysCarnival.Game
public class QTE extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    public static final int ROUND_NUMBER = 3;
    public static final String[] NPC_TALK = {
            "Start slowly with this first punch!\n",
            "Okay now faster with this second punch!\n",
            "You're fast but you can't be more fast for this last punch!\n"
    };
    public static final String[] PUNCHLINE = {
            "My name is Ethoufet Kwallah the fast one",
            "You eat chocopipe during i eat sausage",
            "I rap faster than you and Eminem"
    };
    public static final int[] TIME = {
            20,
            15,
            10
    };

    private IntegerProperty round = new SimpleIntegerProperty(0);

    // Constructor
    public QTE() {
        super("QTE",
                "| You are in front of the faster gipsy's rapper.",
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
        this.start();

        // To play 3 rounds
        while (this.round.get() < ROUND_NUMBER) {

            // Print the punchline of the current round
            this.printPunchline(this.round.get());

            // To know the number of seconds the player has write the punchline
            Date start = new Date();
            System.out.print(player);
            String playerSentence = Gameplay.scanner.nextLine();
            Date end = new Date();
            int second = (int)((end.getTime() - start.getTime()) / 1000);

            // If the player lose then finish game
            if (!this.winRound(playerSentence, this.round.get(), second)) {
                this.lose(player);
                return;
            } else { // Else if he wins then next round
                this.nextRound();
            }
        }

        // If the player wins the 3 rounds
        this.finish(player);
    }

    public IntegerProperty roundProperty() {
        return this.round;
    }

    public void start() {
        round = new SimpleIntegerProperty(0);

        System.out.println("\n--- QTE launched ---\n");

        this.getNpc().talk("Yo! Welcome to my stand bro!\n" +
                "I am the fastest rapper of this carnival!\n" +
                "Try to repeat that I say in a limited time!");
    }

    public void preparing(int round) {
        this.getNpc().talk(NPC_TALK[round] +
                "You have " + TIME[round] + "s to write the punch, good luck!");
    }

    // To check if the player win or not
    public boolean winRound(String playerSentence, int round, int second) {
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

    public void nextRound() {
        this.round.set(round.get()+1);
    }

    public void finish(Player player) {
        this.win(player);
        System.out.println("\n--- QTE finished ---\n");
    }

    // To print the punchline
    private void printPunchline(int round) {
        this.preparing(round);

        // 3 second countdown
        for (int i = ROUND_NUMBER; i > 0; i--) {
            // Catch InterruptedException caused by sleep
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception exception) {
                System.err.println("Error during sleep program in QTE");
            }
            this.getNpc().talk(Integer.toString(i));
        }
        this.getNpc().talk(PUNCHLINE[round]);
    }
}