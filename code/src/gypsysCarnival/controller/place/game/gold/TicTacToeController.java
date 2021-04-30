package gypsysCarnival.controller.place.game.gold;

import gypsysCarnival.controller.PlaceController;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.game.gold.TicTacToe;
import gypsysCarnival.view.CustomAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Random;

/**
 * The Tic tac toe game controller.
 */
public class TicTacToeController {

    /*--------------------- Private members -------------------------*/

    private PlaceController placeController;
    private TicTacToe tictactoe;
    private Player player;
    private Random rand;

    @FXML
    private AnchorPane blockChoose;

    @FXML
    private GridPane boardGame;

    @FXML
    private AnchorPane legendCross;

    @FXML
    private AnchorPane legendCircle;

    @FXML
    private AnchorPane blockReset;

    @FXML
    private TextField textFieldChoose;

    @FXML
    private ImageView crossCenter;
    @FXML
    private ImageView circleCenter;

    @FXML
    private ImageView crossLeft;
    @FXML
    private ImageView circleLeft;

    @FXML
    private ImageView crossRight;
    @FXML
    private ImageView circleRight;

    @FXML
    private ImageView crossTop;
    @FXML
    private ImageView circleTop;

    @FXML
    private ImageView crossTopLeft;
    @FXML
    private ImageView circleTopLeft;

    @FXML
    private ImageView crossTopRight;
    @FXML
    private ImageView circleTopRight;

    @FXML
    private ImageView crossBot;
    @FXML
    private ImageView circleBot;

    @FXML
    private ImageView crossBotLeft;
    @FXML
    private ImageView circleBotLeft;

    @FXML
    private ImageView crossBotRight;
    @FXML
    private ImageView circleBotRight;

    /*--------------------- Public methods -------------------------*/

    /**
     * Change the opacity when mouse is entered in image
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconCrossMouseEntered(MouseEvent mouseEvent) {
        ImageView crossTarget = (ImageView) mouseEvent.getTarget();
        if (crossTarget.getOpacity() != 1) {
            ImageView circle = this.posToCircle(this.symbolToPosI(crossTarget), this.symbolToPosJ(crossTarget));
            if (circle.getOpacity() != 1) {
                crossTarget.setOpacity(0.25);
            }
        }
    }

    /**
     * Change the opacity when mouse is getting out of image
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconCrossMouseExited(MouseEvent mouseEvent) {
        ImageView crossTarget = (ImageView) mouseEvent.getTarget();
        if (crossTarget.getOpacity() != 1) {
            crossTarget.setOpacity(0);
        }
    }

    /**
     * Change the opacity, set the case at this position, and check if the party is finished
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconCrossMouseClicked(MouseEvent mouseEvent) {
        ImageView crossTarget = (ImageView) mouseEvent.getTarget();

        if (!this.setCaseImage(crossTarget)) {
            System.out.println("This case is already use, choose an over one");
        } else {
            crossTarget.setOpacity(1);
            if (this.tictactoe.getWinner() == 1) {
                this.tictactoe.winGame(this.player);
                this.replay(true);
            } else {
                this.npcTurnImg();
                if (this.tictactoe.getWinner() == 2) {
                    this.tictactoe.looseGame(this.player);
                    this.replay(false);
                }
            }

        }
    }

    /**
     * Button submit at the beginning of party
     *
     */
    @FXML
    public void isButtonClicked() {
        if (this.beginner()) {
            this.blockChoose.setOpacity(0);
            this.blockChoose.setDisable(true);

            this.blockReset.setOpacity(1);
            this.blockReset.setDisable(false);

            this.boardGame.setOpacity(1);
            this.boardGame.setDisable(false);

            this.legendCircle.setOpacity(1);
            this.legendCross.setOpacity(1);
        } else {
            this.textFieldChoose.clear();
        }
    }

    /**
     * Button reset
     */
    @FXML
    public void buttonReset() {
        this.tictactoe.looseGame(this.player);
        this.reset();
    }

    /**
     * Reset the game.
     */
    public void reset() {
        this.blockChoose.setOpacity(1);
        this.blockChoose.setDisable(false);

        this.blockReset.setOpacity(0);
        this.blockReset.setDisable(true);

        this.boardGame.setOpacity(0.25);
        this.boardGame.setDisable(true);

        this.legendCircle.setOpacity(0);
        this.legendCross.setOpacity(0);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.posToCircle(i, j).setOpacity(0);
                this.posToCross(i, j).setOpacity(0);
            }
        }

        this.tictactoe.initGame();

        this.textFieldChoose.clear();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets place controller.
     *
     * @param placeController the place controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    /**
     * Sets tictactoe.
     *
     * @param tictactoe the tictactoe
     */
    public void setTictactoe(TicTacToe tictactoe) {
        this.tictactoe = tictactoe;
        this.rand = new Random();
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Choose who will begin
     *
     * @return if choice is valid or not
     */
    private boolean beginner() {
        int beginner = this.rand.nextInt(2) + 1;

        String chosenNumber = this.textFieldChoose.getText();

        if (chosenNumber.equals("1") || chosenNumber.equals("2")) {
            this.tictactoe.getNpc().talk("Ok, so the result is...\n=> " +
                    beginner + " !!");

            // Carries out the first action of the beginner
            if (chosenNumber.equals(Integer.toString(beginner))) {
                this.tictactoe.getNpc().talk("Right, you're the beginner!\n" +
                        "So you're cross, and I take circle.\n" +
                        "Now choose where you want to place you cross!");
            } else {
                this.tictactoe.getNpc().talk("Nice, I'm the beginner.\n" +
                        "So you're cross, and I take circle.\n" +
                        "I'll choose my case!  Good luck darling ;)");
                this.npcTurnImg();
            }
            return true;
        } else {
            this.tictactoe.getNpc().talk("What are you going? I said choose between 1 or 2! Darling...\n" +
                    "So, which one?");
            return false;
        }
    }

    /**
     * Ask to replay the game
     *
     * @param win true if the player won the game
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "gypsysCarnival/view/design/image/find_number.gif",
                "replay",
                "return to gold hub"
        );

        this.tictactoe.finish();

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0)) {
            this.reset();
        } else {
            this.reset();
            Interpreter.interpretCommand(this.player, "go gold");
            this.placeController.changePlace();
        }
    }

    /**
     * Set case for the image in parameter
     *
     * @param imgSymbol Image which concerned
     * @return if setting has been complete
     */
    private boolean setCaseImage(ImageView imgSymbol) {
        int i = this.symbolToPosI(imgSymbol);
        int j = this.symbolToPosJ(imgSymbol);

        boolean res = this.tictactoe.setCase(i, j, 1, false);

        if (res) {
            this.tictactoe.isWin(i, j, 1);
        }

        return res;
    }

    /**
     * Manage turn of the npc player
     */
    private void npcTurnImg() {
        boolean stop = false;
        ImageView circleImg;
        int i;
        int j;

        while (!stop) {

            // Takes random coordinates
            i = rand.nextInt(3);
            j = rand.nextInt(3);

            // Checked if the coordinates are valid
            if (this.tictactoe.setCase(i, j, 2, false)) {
                circleImg = this.posToCircle(i, j);
                circleImg.setOpacity(1);
                stop = true;
            }
        }
    }

    /**
     * Search circle image for this position
     *
     * @param i position in i
     * @param j position in j
     * @return ImageView for this position
     */
    private ImageView posToCircle(int i, int j) {
        if (i == 0 && j == 0) {
            return this.circleTopLeft;
        } else if (i == 0 && j == 1) {
            return this.circleTop;
        } else if (i == 0 && j == 2) {
            return this.circleTopRight;
        } else if (i == 1 && j == 0) {
            return this.circleLeft;
        } else if (i == 1 && j == 1) {
            return this.circleCenter;
        } else if (i == 1 && j == 2) {
            return this.circleRight;
        } else if (i == 2 && j == 0) {
            return this.circleBotLeft;
        } else if (i == 2 && j == 1) {
            return this.circleBot;
        } else {
            return this.circleBotRight;
        }
    }

    /**
     * Search cross image for this position
     *
     * @param i position in i
     * @param j position in j
     * @return ImageView for this position
     */
    private ImageView posToCross(int i, int j) {
        if (i == 0 && j == 0) {
            return this.crossTopLeft;
        } else if (i == 0 && j == 1) {
            return this.crossTop;
        } else if (i == 0 && j == 2) {
            return this.crossTopRight;
        } else if (i == 1 && j == 0) {
            return this.crossLeft;
        } else if (i == 1 && j == 1) {
            return this.crossCenter;
        } else if (i == 1 && j == 2) {
            return this.crossRight;
        } else if (i == 2 && j == 0) {
            return this.crossBotLeft;
        } else if (i == 2 && j == 1) {
            return this.crossBot;
        } else {
            return this.crossBotRight;
        }
    }

    /**
     * Search the position i for this image
     *
     * @param symbol image view
     * @return integer of position in i
     */
    private int symbolToPosI(ImageView symbol) {
        if (symbol == this.crossTopLeft || symbol == this.circleTopLeft || symbol == this.crossTop || symbol == this.circleTop || symbol == this.crossTopRight || symbol == this.circleTopRight) {
            return 0;
        } else if (symbol == this.crossLeft || symbol == this.circleLeft || symbol == this.crossCenter || symbol == this.circleCenter || symbol == this.crossRight || symbol == this.circleRight) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Search the position j for this image
     *
     * @param symbol image view
     * @return integer of position in j
     */
    private int symbolToPosJ(ImageView symbol) {
        if (symbol == this.crossTopLeft || symbol == this.circleTopLeft || symbol == this.crossLeft || symbol == this.circleLeft || symbol == this.crossBotLeft || symbol == this.circleBotLeft) {
            return 0;
        } else if (symbol == this.crossTop || symbol == this.circleTop || symbol == this.crossCenter || symbol == this.circleCenter || symbol == this.crossBot || symbol == this.circleBot) {
            return 1;
        } else {
            return 2;
        }
    }
}
