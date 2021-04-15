package model.place.game;

import model.Gameplay;
import model.character.NPC;
import model.character.Player;
import model.Level;
import model.place.Game;

import java.util.Scanner;
import java.util.Stack;

// This class is a subclass of Game
public class HanoiTower extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    // Class attributes
    private final static int DISK_NUMBER = 3;

    private String[][] game;
    private Stack<Integer> aPillar;
    private Stack<Integer> bPillar;
    private Stack<Integer> cPillar;

    // Constructor
    public HanoiTower() {
        super("Hanoi tower",
                "| You are in front of a person who needs help to solve a puzzle.",
                new NPC("Edwardo Nald"),
                Level.GOLD);
    }

    /**********
     * Methods
     **********/

    // To play the game
    @Override
    public void play(Player player) {
        // Method variables
        Scanner scanner = Gameplay.scanner;
        NPC npc = this.getNpc();
        String command = "";
        String[] commandTab;

        System.out.println("\n--- Game launched ---\n");

        npc.talk("Hi young man, I'm really annoyed by this problem, can you help me?\n" +
                "I have three pillars in front of me and I have to pass the three discs from pillar A to pillar C using pillar B.\n" +
                "You have to respect these 2 rules in order not to lose:\n" +
                "-You can only move one disc at a time.\n" +
                "-You can only move a disk to an empty slot or a smaller disk.");

        // To initialize the stacks
        initialize();
        // To print the pillars
        printPillars();

        // To force the player to write "a c"
        while (!command.equalsIgnoreCase("A C")) {
            npc.talk("To start type \"a c\" to move disc from A to C.");
            System.out.print(player);
            command = scanner.nextLine();
        }
        // To execute the first disk move of a pillar to c pillar
        commandTab = command.toLowerCase().split(" ");
        moveDisk(commandTab[0], commandTab[1]);
        printPillars();

        // Play while the player has not win (or lose)
        while (!isWin()) {
            // Re-init the command and check the next command
            command = "";
            while (wrongCommand(command)) {
                npc.talk("What is your next move?");
                System.out.print(player);
                command = scanner.nextLine();
                if (wrongCommand(command)) {
                    System.out.println("| Unknown command");
                }
            }

            commandTab = command.toLowerCase().split(" ");

            // To check if the move is possible
            if (!canMove(commandTab[0])) {
                npc.talk("You need to choose a non empty pillar!");
                continue;
            }

            // If the move is correct then print the pillars and go to the next step
            if (moveDisk(commandTab[0], commandTab[1])) {
                printPillars();
            } else { // Else the player lose and the game stop
                npc.talk("Oh no you've lose!");
                this.lose(player);
                return;
            }
        }

        // The player has win
        this.win(player);

        npc.talk("Oh thanks a lot for helping me!");
        System.out.println("\n--- Game finished ---\n");
    }

    // Getter
    private Stack<Integer> getPillar(String pillar) {
        switch (pillar) {
            case "a":
                return aPillar;
            case "b":
                return bPillar;
            default:
                return cPillar;
        }
    }

    // To initialize the game
    private void initialize() {
        // Init the print matrix
        this.game = new String[DISK_NUMBER][DISK_NUMBER];
        for (int i = 0; i < DISK_NUMBER; i++) {
            for (int j = 0; j < DISK_NUMBER; j++) {
                if (j == 0) {
                    this.game[i][j] = Integer.toString(i+1);
                } else {
                    this.game[i][j] = "|";
                }
            }
        }

        // Init the 3 stacks
        this.aPillar = new Stack<>();
        this.bPillar = new Stack<>();
        this.cPillar = new Stack<>();

        // Add the disk on the first pillar
        for (int i = DISK_NUMBER; i > 0; i--) {
            aPillar.push(i);
        }
    }

    // To print the pillars
    private void printPillars() {
        for (int i = 0; i < DISK_NUMBER; i++) {
            for (int j = 0; j < DISK_NUMBER; j++) {
                System.out.print(this.game[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-\t-\t-\t");
        System.out.println("a\tb\tc\t");
    }

    // To check the command
    private boolean wrongCommand(String cmd) {
        // The command need to have the first and second part a, b or c
        String[] cmdTab = cmd.toLowerCase().split(" ");
        String match = "[abc]";
        // The command need have at least 2 string matches with regex [abc] to represent pillars
        if (cmdTab.length >= 2) {
            return !cmdTab[0].matches(match) || !cmdTab[1].matches(match) || cmdTab[0].equals(cmdTab[1]);
        } else {
            return true;
        }
    }

    // To check if the player win
    private boolean isWin() {
        return this.cPillar.size() == 3;
    }

    // To check if the player can move the disks
    private boolean canMove(String src) {
        return !getPillar(src).empty();
    }

    // To move a disk
    private boolean moveDisk(String src, String dest) {
        // Variables method
        int disk;
        boolean isWin;
        Stack<Integer> srcPillar = getPillar(src);
        Stack<Integer> destPillar = getPillar(dest);
        int srcLength = srcPillar.size();

        // Pop the head of the source pillar
        disk = srcPillar.pop();

        // If the destination pillar is empty the move is possible
        if (destPillar.empty()) {
            isWin = true;
        } else { // Else the disk move need to be lower than the head of the destination pillar
            isWin = disk < destPillar.peek();
        }

        // If the player lose
        if (!isWin) {
            return false;
        } else { // Else if the move is correct
            // Push the disk at the head of the destination pillar
            destPillar.push(disk);
            changeGame(src, dest, srcLength, destPillar.size());
            return true;
        }
    }

    // To change the screen print of the pillars
    private void changeGame(String src, String dest, int srcLength, int destLength) {
        // Source pillar transform in int
        int s = src.charAt(0) - 97;
        // Destination pillar transform in int
        int d = dest.charAt(0) - 97;

        // To switch the values in the print matrix
        String tmp = this.game[DISK_NUMBER-srcLength][s];
        this.game[DISK_NUMBER-srcLength][s] = this.game[DISK_NUMBER-destLength][d];
        this.game[DISK_NUMBER-destLength][d] = tmp;
    }
}