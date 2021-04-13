package controller;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.character.Player;
import model.command.Interpreter;

import java.net.URL;
import java.util.ResourceBundle;

public class CarnivalController implements Initializable {

    private TranslateTransition transition;

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane carnivalScene;

    @FXML
    private ImageView benjapiedIcon;

    @FXML
    private ImageView keyShopIconTest;

    @FXML
    public void iconMouseEntered(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, (ImageView) event.getTarget(), 1.2);
    }

    @FXML
    public void iconMouseExited(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, (ImageView) event.getTarget(), 1);
    }

    @FXML
    void gameSceneClicked(MouseEvent mouseEvent) {
        moveBenjapiedIcon(Duration.seconds(1), mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.carnivalScene.setFocusTraversable(true);
        this.carnivalScene.requestFocus();
        this.transition = new TranslateTransition(Duration.UNKNOWN, this.benjapiedIcon);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.benjapiedIcon.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (newValue.intersects(keyShopIconTest.getBoundsInParent())) {
                    Interpreter.interpretCommand(player, "go key");
                    gameController.changePlace();
                    benjapiedIcon.boundsInParentProperty().removeListener(this);
                }
            }
        });
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.getRoot().setOnKeyPressed(this::moveWithKeys);
    }

    public void reset() {
        this.benjapiedIcon.setTranslateX(0);
        this.benjapiedIcon.setTranslateY(0);
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
