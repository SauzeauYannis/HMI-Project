package controller.place.game.gold;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private String[] currentRiddle;
    private final ChangeListener<Number> numberChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            attemptLabel.setText("Attempt left: " + newValue.intValue());
            if (newValue.intValue() == 0)
                replay(false);
        }
    };

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
    private Button answerButton;

    @FXML
    void answerButtonClicked() {
        if (this.riddle.giveAnswer(this.player, this.answerTextField.getText(), this.currentRiddle))
            this.replay(true);
        this.answerTextField.clear();
    }

    @FXML
    void yesButtonMouseClicked() {
        this.changeVisible(false);
        this.riddle.choseYes(true, this.currentRiddle);
    }

    @FXML
    void noButtonMouseClicked() {
        this.riddle.choseYes(false, this.currentRiddle);
        this.replay(false);
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
        this.changeVisible(true);

        this.riddle = (Riddle) this.player.getPlace();
        this.currentRiddle = this.riddle.startAndGetRiddle();
        this.attemptLabel.setText("Attempt left: " + Riddle.DEFAULT_ATTEMPTS);

        this.riddle.attemptsProperty().removeListener(numberChangeListener);
        this.riddle.attemptsProperty().addListener(numberChangeListener);
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
