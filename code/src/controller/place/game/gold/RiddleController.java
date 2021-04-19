package controller.place.game.gold;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.gold.Riddle;

import java.net.URL;
import java.util.ResourceBundle;

public class RiddleController implements Initializable {

    private Riddle riddle;
    private String currentRiddle;

    private GameController gameController;
    private Player player;
    private Scene scene;

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
    void yesButtonMouseClicked() {
        this.changeVisible(false);
        this.riddle.choseYes(true, this.currentRiddle);
    }

    @FXML
    void noButtonMouseClicked() {
        this.riddle.choseYes(false, this.currentRiddle);
        this.replay();
    }

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void goGold() {
        Interpreter.interpretCommand(this.player, "go gold");
        this.gameController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.goldHubIcon, new Tooltip("Go to gold hub"));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void reset() {
        this.questionLabel.setText("Are you ready?");
        this.changeVisible(true);

        this.riddle = (Riddle) this.player.getPlace();
        this.currentRiddle = this.riddle.startAndGetRiddle()[0];

        this.attemptLabel.textProperty().unbind();
        this.attemptLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Attempt left: " + this.riddle.attemptsProperty().get(),
                        this.riddle.attemptsProperty()
                )
        );
    }

    private void changeVisible(boolean start) {
        this.yesButton.setVisible(start);
        this.noButton.setVisible(start);
        this.answerTextField.setVisible(!start);
        this.attemptLabel.setVisible(!start);
    }

    private void replay() {
        this.riddle.finish();

        if (UtilsController.getAlertFinish().showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goGold();
    }
}
