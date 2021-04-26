package controller.place.game.gold;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.gold.Riddle;

import java.net.URL;
import java.util.ResourceBundle;

public class RiddleController implements Initializable {

    private String[] currentRiddle;

    private Riddle riddle;
    private GameController gameController;
    private Player player;

    @FXML
    private ImageView goldHubIcon;

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
    private void answerButtonClicked() {
        if (this.riddle.isGoodAnswer(this.player, this.answerTextField.getText(), this.currentRiddle))
            this.replay(true);
        else if (!this.riddle.canContinue())
            this.replay(false);
        else
            this.answerTextField.clear();
    }

    @FXML
    private void yesButtonMouseClicked() {
        this.riddle.choseYes(this.currentRiddle);
        this.changeVisible(false);
    }

    @FXML
    private void noButtonMouseClicked() {
        this.riddle.choseNo(this.currentRiddle);
        this.replay(false);
    }

    @FXML
    private void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode((Node) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    private void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.defaultScaleNode((Node) mouseEvent.getTarget());
    }

    @FXML
    private void goGold() {
        Interpreter.interpretCommand(this.player, "go gold");
        this.gameController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.goldHubIcon, new Tooltip("Go to gold hub"));

        this.goldHubIcon.setCursor(Cursor.HAND);
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

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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
        this.riddle.finish();

        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goGold();
    }
}
