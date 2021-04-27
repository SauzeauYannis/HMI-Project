package controller.place.game.copper;

import controller.PlaceController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.copper.FindNumber;
import view.CustomAlert;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Find number game controller.
 */
public class FindNumberController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private final Image plusImage = new Image("view/design/image/plus.png");
    private final Image lessImage = new Image("view/design/image/minus.png");

    private FindNumber findNumber;
    private PlaceController placeController;
    private Player player;

    @FXML
    private TextField numberField;

    @FXML
    private Label attemptLabel;

    @FXML
    private VBox historyBox;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.numberField, new Tooltip("Type enter or press submit button to validate"));

        this.numberField.setCursor(Cursor.TEXT);

        this.numberField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }

    /**
     * Number field key pressed.
     *
     * @param keyEvent the key event
     */
    @FXML
    public void numberFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            this.submitMouseClicked();
    }

    /**
     * Submit mouse clicked.
     */
    @FXML
    public void submitMouseClicked() {
        this.playTurn();
    }

    /**
     * Reset the game.
     */
    public void reset() {
        this.historyBox.getChildren().clear();
        this.findNumber.start();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets find number model
     *
     * @param findNumber the find number
     */
    public void setFindNumber(FindNumber findNumber) {
        this.findNumber = findNumber;

        this.attemptLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Attempts left: " + this.findNumber.attemptProperty().get() + "!",
                        this.findNumber.attemptProperty()
                )
        );
    }

    /**
     * Sets place controller.
     *
     * @param placeController the place controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
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
     * Play a turn.
     *
     * @see #submitMouseClicked()
     */
    private void playTurn() {
        String chosenNumber = this.numberField.getText();

        if (!chosenNumber.equals("")) {
            int number = Integer.parseInt(chosenNumber);

            if (this.findNumber.playOneTurn(this.player, number)) {
                if (this.findNumber.canContinue())
                    this.addHistory(chosenNumber, number);
                else
                    this.replay(this.findNumber.isWin());
            }
        } else
            this.findNumber.mustBeNumber();

        this.numberField.clear();
    }

    /**
     * Add the chosen number in the screen with a plus or less icon
     *
     * @param chosenNumber the chosen number
     * @param number       the number
     * @see #playTurn()
     */
    private void addHistory(String chosenNumber, int number) {
        HBox hBox = new HBox(20, new Label(chosenNumber));

        if (number < this.findNumber.getRand()) {
            ImageView plusIcon = new ImageView(this.plusImage);
            plusIcon.setFitHeight(20);
            plusIcon.setFitWidth(20);
            hBox.getChildren().add(plusIcon);
        } else {
            ImageView lessIcon = new ImageView(this.lessImage);
            lessIcon.setFitHeight(20);
            lessIcon.setFitWidth(20);
            hBox.getChildren().add(lessIcon);
        }

        this.historyBox.getChildren().add(hBox);
    }

    /**
     * Ask to replay the game.
     *
     * @param win true if the player won the game.
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "view/design/image/find_number.gif",
                "replay",
                "return to copper hub"
        );

        this.findNumber.finish();

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go copper");
            this.placeController.changePlace();
        }
    }
}
