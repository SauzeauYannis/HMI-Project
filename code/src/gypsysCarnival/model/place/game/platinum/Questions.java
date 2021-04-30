package gypsysCarnival.model.place.game.platinum;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import gypsysCarnival.model.Gameplay;
import gypsysCarnival.model.character.NPC;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.Level;
import gypsysCarnival.model.place.Game;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// This class is a subclass of gypsysCarnival.Game
public class Questions extends Game {

    /*****************************
     * Attributes and constructor
     *****************************/

    //ATTRIBUTES
    public final static int NB_ROUND = 5; //Must be <= nb of questions
    public final static String EASY = "EASY";
    public final static String DIFFICULT = "DIFFICULT";
    public final static String YES = "YES";
    public final static String NO = "NO";
    private final static int DEFAULT_REWARD_EASY = 2;
    private final static int DEFAULT_REWARD_DIFFICULT = 5;

    private final IntegerProperty jackpot = new SimpleIntegerProperty(0);
    private final IntegerProperty round= new SimpleIntegerProperty(0);

    private Random random;
    private String[][] QUESTIONS;
    private int[] questionUsed;
    private int goodAnswer;
    private int idQuestion;
    private int reward;
    private boolean lose;

    // Here, correct answers are always put in index 1
    private final String[][] QUESTIONS_EASY = {
            {"Which famous dictator ruled the USSR from the mid-1920s to 1953 ?","Stalin","Lenin","Trotsky","Gorbachev"},
            {"In which country can we find Catalonia, Andalusia and Castile ?","Spain","Italy","France","Portugal"},
            {"Who said: The die is cast (Alea jacta est)?","Ceasar","Attila","Auguste","Vercingetorix"},
            {"Which country won the soccer world cup in 2014?","Germany","Argentina","Italy","Brazil"},
            {"Who was the god of war in Greek mythology?","Ares","Athena","Hades","Hermes"},
            {"In which Italian city is the action of Shakespeare's play Romeo and Juliet located?","Verona","Venice","Rome","Milan"},
            {"In mathematics, how do we call the half-line that divides an angle into two equal angles?","bisector","median","mediator","fore height"}
    };
    private final String[][] QUESTIONS_DIFFICULT = {
            {"Where was Mozart born ?","Salzburg","Vienna","Turin","Venice"},
            {"What year is usually remembered as the year of the fall of the Western Roman Empire ?","476 AD","496 AD","387 AD","415 AD"},
            {"Which state in the United States has Montgomery as its capital ?","Alabama","New Mexico","California","Ohio"},
            {"Which animal is a drosophila, used in genetic experiments ?","A fly","A guinea pig","A goat","A rat"},
            {"Which architect designed and built the Hall of Mirrors at the Palace of Versailles ?","Jules Hardouin-Mansart","Louis Le Vau","André Le Nôtre","Bob the handyman"},
            {"What is the Chemical formula of methane ?","CH4","N2","SO2","CH3Cl"},
            {"Who invented the parachute ?", "Da Vinci","Montgolfier Brothers","Ader","Wright Brothers"},
            {"What is the capital of Zimbabwe ?", "Harare","Bulawayo","Mutare","Ouagadougou"}
    };

    //CONSTRUCTOR
    public Questions() {
        super("Questions",
                "| In this game, your culture will be useful to find out the correct answer to questions.",
                new NPC("Samuel Outienne"),
                Level.PLATINUM);
    }

    /**********
     * Methods
     **********/

    //OVERRIDE METHODS
    @Override
    public void play(Player player) {
        Scanner scanner;

        this.start();

        // To chose the level so to chose the string array we will work with
        this.generateQuestions(player);

        // Here starts the game loop
        while (this.canContinue() && !lose) {
            // Asks if the player wants to continue if it is NOt the first loop
            if (!this.wantStop(player)) {
                this.nextQuestion();

                System.out.println("\nROUND " + this.round.get() + " :");

                // Displays question and answers
                this.displayQuestionBoard(this.getShuffleQuestion());

                int answer = 0;

                // To get the player's answer
                while (answer < 1 || answer > 4) {
                    System.out.println("\t(TAPE: \"1\", \"2\", \"3\" or \"4\")");

                    scanner = Gameplay.scanner;
                    System.out.print(player);
                    try {
                        answer = scanner.nextInt();
                    } catch (Exception exception) {
                        scanner.nextLine();
                        this.getNpc().talk("I expect a number!");
                    }
                }

                lose = !this.isCorrectAnswer(answer);

                if (this.isTheLastRound()) {
                    this.winJackpot(player);
                    Gameplay.scanner.nextLine();
                    System.out.println("\n--- Questions finished ---\n");
                    return;
                }
            }
        }

        // Displays NPC reaction
        this.endGame();

        // Checks if player wins and acts accordingly
        if (lose)
            this.lose(player);
        else
            this.winJackpot(player);

        // To flush scanner
        Gameplay.scanner.nextLine();

        System.out.println("\n--- Questions finished ---\n");
    }

    //GETTERS
    public IntegerProperty jackpotProperty() {
        return this.jackpot;
    }

    public IntegerProperty roundProperty() {
        return this.round;
    }

    //METHODS
    public void start() {
        // To declare variable needed for the game
        this.jackpot.set(0);
        this.round.set(0);

        random = new Random();
        lose = false;
        questionUsed = new int[NB_ROUND];

        System.out.println("\n--- Questions launched ---\n");

        this.getNpc().talk("Laddies and Gentlemen, welcome to my stand! " +
                "Here your culture would be roughly tested...\n" +
                "To start, you have to choose the level of questions:\n" +
                EASY + " or " + DIFFICULT + "?"
        );
    }

    public void chooseEasyQuestion() {
        this.chooseQuestionLevel(true);
    }

    public void chooseDifficultQuestion() {
        this.chooseQuestionLevel(false);
    }

    public void nextQuestion() {
        this.round.set(this.round.get() + 1);
        this.jackpot.set(this.jackpot.get() + this.reward);
        this.jackpot.set(this.jackpot.get() * this.round.get());

        if (this.round.get() > 1)
            this.getNpc().talk("Let's move to the next question...");

        // To chose a question that had not be already chosen
        do
            this.idQuestion = this.random.nextInt(QUESTIONS.length);
        while (Arrays.stream(this.questionUsed).anyMatch(value -> value == this.idQuestion) && this.idQuestion != 0);

        // Stocks id of used question
        this.questionUsed[this.round.get() - 1] = this.idQuestion;
    }

    //Displays question and answers
    public String[] getShuffleQuestion() {
        String[] question = new String[5];
        question[4] = QUESTIONS[this.idQuestion][0];

        int rd = random.nextInt(100);      // To display randomly answers
        int num_answer = 1;

        for (int i = rd; i < rd + 5; i++) {
            int index = i % 5;
            if (index != 0) {
                if (index == 1)
                    this.goodAnswer = num_answer; // Stocks the number of the correct question
                question[num_answer - 1] = num_answer + " - " + QUESTIONS[this.idQuestion][index];
                num_answer++;
            }
        }

        return question;
    }

    public boolean isCorrectAnswer(int answer) {
        boolean isGoodAnswer = answer == this.goodAnswer;
        // Verifies if player's answer is good
        if (!isGoodAnswer)
            this.getNpc().talk("It was not the correct answer. Sorry, you lose the game and your jackpot.\n" +
                    "Next time will be the good one!");
        else
            this.getNpc().talk("Well played! It was the correct answer.");

        return isGoodAnswer;
    }

    public void npcAskForNextTurn() {
        this.getNpc().talk("Do you want to stop the game and cash your jackpot?\nOr you want to continue to improve the jackpot?");
    }

    public boolean isTheLastRound() {
        return this.round.get() == NB_ROUND;
    }

    //Gives NPC's reactions according to player's conditions
    public void endGame() {
        if (this.round.get() == NB_ROUND + 1)
            this.getNpc().talk("A faultless! You win the ultimate reward. A real winner, congratulations!");
        else if (!this.lose)
            this.getNpc().talk("You've made a good beginning but it is a wise decision!");
        this.getNpc().talk("I hope I will see soon ! :)");
    }

    public void winJackpot(Player player) {
        this.win(player, this.jackpot.get());
    }

    //Asks if player wants stop
    private boolean wantStop(Player player) {
        if (this.round.get() > 0) {
            Scanner scanner = Gameplay.scanner;
            scanner.nextLine();
            String response = "";

            System.out.println("Jackpot: " + this.jackpot.get() + " coins.");
            npcAskForNextTurn();

            while ((response.compareTo(YES) != 0) && (response.compareTo(NO) != 0)) {
                System.out.println("\t(TYPE: \"" + YES + "\" or \"" + NO + "\")");
                System.out.print(player);
                response = scanner.nextLine().toUpperCase();
            }

            return response.equals(YES);
        }
        return false;
    }

    private boolean canContinue() {
        return this.round.get() <= NB_ROUND;
    }

    //Provides an questions array from level that player wants
    private void generateQuestions(Player player) {
        Scanner scanner;
        String lvl_chosen = "";

        // Asks to the player level he/she wants
        while ((lvl_chosen.compareTo(EASY) != 0) && (lvl_chosen.compareTo(DIFFICULT) != 0)) {
            System.out.println("\t(TAPE: \"" + EASY + "\" or \"" + DIFFICULT + "\")");
            scanner = Gameplay.scanner;
            System.out.print(player);
            lvl_chosen = scanner.nextLine();
            lvl_chosen = lvl_chosen.toUpperCase();
        }

        this.chooseQuestionLevel(lvl_chosen.compareTo(EASY) == 0);
    }

    private void displayQuestionBoard(String[] question) {
        System.out.println("\"" + question[4] + "\"");
        for (int i = 0; i < question.length; i++) {
            if (i % 2 == 0)
                System.out.print(question[i]);
            else
                System.out.println("\t\t\t" + question[i]);
        }
    }

    private void chooseQuestionLevel(boolean isEASY) {
        // Create an array of EASY or diffucult questions according to the choice
        if (isEASY)
            QUESTIONS = chooseEASY();
        else
            QUESTIONS = chooseDIFFICULT();

        this.getNpc().talk("Right. Get ready, get set, go!");
    }

    private String[][] chooseEASY() {
        String[][] QUESTIONS;
        this.reward = DEFAULT_REWARD_EASY;
        this.getNpc().talk("You choose \"" + EASY + "\". Well, easily but surely!");
        QUESTIONS = new String[this.QUESTIONS_EASY.length][];
        this.cloneEASY(QUESTIONS, this.QUESTIONS_EASY.length);
        return QUESTIONS;
    }

    private String[][] chooseDIFFICULT() {
        String[][] QUESTIONS;
        this.reward = DEFAULT_REWARD_DIFFICULT;
        this.getNpc().talk("You choose \"" + DIFFICULT + "\". Such a warrior!");
        QUESTIONS = new String[this.QUESTIONS_DIFFICULT.length][];
        this.cloneDIFFICULT(QUESTIONS, this.QUESTIONS_DIFFICULT.length);
        return QUESTIONS;
    }

    //Copies QUESTIONS_EASY to an targeted array
    private void cloneEASY(String[][] target, int length) {
        for (int i = 0; i < length; i++)
            target[i] = QUESTIONS_EASY[i].clone();
    }

    //Copies QUESTIONS_DIFFICULT to an targeted array
    private void cloneDIFFICULT(String[][] target, int length) {
        for (int i = 0; i < length; i++)
            target[i] = QUESTIONS_DIFFICULT[i].clone();
    }
}
