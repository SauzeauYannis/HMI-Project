package controller.place.game.gold;

import controller.PlaceController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.gold.Riddle;
import view.CustomAlert;

import java.net.URL;
import java.util.ResourceBundle;

public class RiddleController implements Initializable {

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

    @FXML
    public void answerButtonClicked() {
        if (this.riddle.isGoodAnswer(this.player, this.answerTextField.getText(), this.currentRiddle))
            this.replay(true);
        else if (!this.riddle.canContinue())
            this.replay(false);
        else
            this.answerTextField.clear();
    }

    @FXML
    public void yesButtonMouseClicked() {
        this.riddle.choseYes(this.currentRiddle);
        this.changeVisible(false);
    }

    @FXML
    public void noButtonMouseClicked() {
        this.riddle.choseNo(this.currentRiddle);
        this.replay(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.answerTextField.setCursor(Cursor.TEXT);
    }

    public void setRiddle(Riddle riddle) {
        this.riddle = riddle;

        this.attemptLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Attempt left: " + this.riddle.attemptsProperty().get(),
                        this.riddle.attemptsProperty()
                )
        );
    }

    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.changeVisible(true);

        this.answerTextField.clear();

        this.currentRiddle = this.riddle.startAndGetRiddle();
    }

    private void changeVisible(boolean start) {
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

    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "view/design/image/riddle.gif",
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
