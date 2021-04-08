package controleur;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modele.character.Player;
import modele.command.Interpreter;

import java.net.URL;
import java.util.ResourceBundle;

public class CarnivalController implements Initializable {

    private TranslateTransition transition;
    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane carnivalScene;

    @FXML
    private ImageView benjapiedIcon;

    @FXML
    void gameSceneClicked(MouseEvent mouseEvent) {
        moveBenjapiedIcon(Duration.seconds(1), mouseEvent.getX(), mouseEvent.getY());
    }

    @FXML
    void testBuyKeyClicked() {
        Interpreter.interpretCommand(this.player, "go key");
        Interpreter.interpretCommand(this.player, "take copper");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.carnivalScene.setFocusTraversable(true);
        this.carnivalScene.requestFocus();
        this.transition = new TranslateTransition(Duration.UNKNOWN, this.benjapiedIcon);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.getRoot().setOnKeyPressed(this::moveWithKeys);
    }

    private void moveWithKeys(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z:
                moveBenjapiedIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.benjapiedIcon),
                        UtilsController.getTranslateCenterY(this.benjapiedIcon) - 20);
                break;
            case Q:
                moveBenjapiedIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.benjapiedIcon) - 20,
                        UtilsController.getTranslateCenterY(this.benjapiedIcon));
                break;
            case S:
                moveBenjapiedIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.benjapiedIcon),
                        UtilsController.getTranslateCenterY(this.benjapiedIcon) + 20);
                break;
            case D:
                moveBenjapiedIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.benjapiedIcon) + 20,
                        UtilsController.getTranslateCenterY(this.benjapiedIcon));
                break;
        }
    }

    private void moveBenjapiedIcon(Duration duration, double X, double Y) {
        if (X > 0 && Y > 0 && X < this.carnivalScene.getWidth() && Y < this.carnivalScene.getHeight()) {
            transition.setDuration(duration);
            transition.setToX(X - UtilsController.getMiddleWidth(this.benjapiedIcon));
            transition.setToY(Y - UtilsController.getMiddleHeight(this.benjapiedIcon));
            transition.playFromStart();
        }
    }
}
