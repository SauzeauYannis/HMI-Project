package gypsysCarnival.controller.place.game.gold;

import gypsysCarnival.controller.PlaceController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.game.gold.Riddle;
import gypsysCarnival.view.CustomAlert;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Riddle game gypsysCarnival.controller.
 */
public class RiddleController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private String[] currentRiddle;

    private Riddle riddle;
    private PlaceController placeController;
    private Player player;

    @FXML
    private Label questionLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private TextField answerTextField;

    @FXML
    private Label attemptLabel;

    @FXML
    private Button answerButton;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.answerTextField.setCursor(Cursor.TEXT);
    }

    /**
     * Yes button mouse clicked.
     */
    @FXML
    public void yesButtonMouseClicked() {
        this.riddle.choseYes(this.currentRiddle);
        this.changeVisibility(false);
    }

    /**
     * No button mouse clicked.
     */
    @FXML
    public void noButtonMouseClicked() {
        this.riddle.choseNo(this.currentRiddle);
        this.replay(false);
    }

    /**
     * Answer button clicked.
     */
    @FXML
    public void answerButtonClicked() {
        if (this.riddle.isGoodAnswer(this.player, this.answerTextField.getText(), this.currentRiddle))
            this.replay(true);
        else if (!this.riddle.canContinue())
            this.replay(false);
        else
            this.answerTextField.clear();
    }

    /**
     * Answer text field key pressed.
     *
     * @param keyEvent the key event
     */
    @FXML
    public void answerTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.answerButtonClicked();
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        this.changeVisibility(true);

        this.answerTextField.clear();

        this.currentRiddle = this.riddle.startAndGetRiddle();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets riddle gypsysCarnival.model.
     *
     * @param riddle the riddle
     */
    public void setRiddle(Riddle riddle) {
        this.riddle = riddle;

        this.attemptLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Attempt left: " + this.riddle.attemptsProperty().get(),
                        this.riddle.attemptsProperty()
                )
        );
    }

    /**
     * Sets place gypsysCarnival.controller.
     *
     * @param placeController the place gypsysCarnival.controller
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
     * Change visibility.
     *
     * @param start if is the starting position.
     * @see #yesButtonMouseClicked()
     * @see #reset()
     */
    private void changeVisibility(boolean start) {
        this.yesButton.setVisible(start);
        this.noButton.setVisible(start);
        this.answerTextField.setVisible(!start);
        this.answerButton.setVisible(!start);
        this.attemptLabel.setVisible(!start);

        if (start)
            this.questionLabel.setText("Are you ready?");
        else
            this.questionLabel.setText(this.currentRiddle[0]);
    }

    /**
     * Ask to replay the game.
     *
     * @param win true if the player won the game.
     * @see #answerButtonClicked()
     * @see #noButtonMouseClicked()
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "gypsysCarnival/view/design/image/riddle.gif",
                "replay",
                "return to gold hub"
        );

        this.riddle.finish();

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go gold");
            this.placeController.changePlace();
        }
    }
}
