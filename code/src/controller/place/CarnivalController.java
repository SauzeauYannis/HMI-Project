package controller.place;

import controller.GameController;
import controller.UtilsController;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.character.Player;
import model.command.Interpreter;
import model.place.Game;

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
    private ImageView playerIcon;

    @FXML
    private ImageView keyShopIcon;

    @FXML
    private ImageView foodShopIcon;

    @FXML
    private ImageView sparklingCaravanIcon;

    @FXML
    private ImageView goldHubIcon;

    @FXML
    private ImageView platinumHubIcon;

    @FXML
    private ImageView copperHubIcon;

    @FXML
    private ImageView padlockCaravanIcon;

    // TODO: 14-Apr-21 Enlever lors du rendu
    @FXML
    void cheat() {
        this.player.increaseHealth(100);
        this.player.earnMoney(10000);
    }

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
        movePlayerIcon(Duration.seconds(1), mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transition = new TranslateTransition(Duration.UNKNOWN, this.playerIcon);

        Tooltip.install(this.keyShopIcon, new Tooltip("Go to the key shop!"));
        Tooltip.install(this.foodShopIcon, new Tooltip("Go to the food shop!"));
        Tooltip.install(this.sparklingCaravanIcon, new Tooltip("Go to the sparkling caravan!"));
        Tooltip.install(this.copperHubIcon, new Tooltip("Go to the copper hub!"));
        Tooltip.install(this.goldHubIcon, new Tooltip("Go to the gold hub!"));
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to the platinum hub!"));
        Tooltip.install(this.padlockCaravanIcon, new Tooltip("This place is lock!\nYou need to finish the 9 mini-games\nto automatically unlock it"));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.padlockCaravanIcon.visibleProperty().bind(
                player.getGamesFinished().isEqualTo(Game.NB_GAMES).not()
        );
        this.reset();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.getRoot().setOnKeyPressed(this::moveWithKeys);
    }

    public void reset() {
        this.playerIcon.setTranslateX(0);
        this.playerIcon.setTranslateY(0);
        this.playerIcon.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (newValue.intersects(keyShopIcon.getBoundsInParent())) {
                    Interpreter.interpretCommand(player, "go key");
                    gameController.changePlace();
                    playerIcon.boundsInParentProperty().removeListener(this);
                } else if (newValue.intersects(foodShopIcon.getBoundsInParent())) {
                    Interpreter.interpretCommand(player, "go food");
                    gameController.changePlace();
                    playerIcon.boundsInParentProperty().removeListener(this);
                } else if (newValue.intersects(sparklingCaravanIcon.getBoundsInParent())) {
                    if (!padlockCaravanIcon.isVisible()) {
                        Interpreter.interpretCommand(player, "go sparkling");
                        gameController.changePlace();
                        playerIcon.boundsInParentProperty().removeListener(this);
                    } else {
                        playerIcon.setTranslateX(675);
                        playerIcon.setTranslateY(300);
                    }
                } else if (newValue.intersects(copperHubIcon.getBoundsInParent())) {
                    Interpreter.interpretCommand(player, "go copper");
                    gameController.changePlace();
                    playerIcon.boundsInParentProperty().removeListener(this);
                } else if (newValue.intersects(goldHubIcon.getBoundsInParent())) {
                    Interpreter.interpretCommand(player, "go gold");
                    gameController.changePlace();
                    playerIcon.boundsInParentProperty().removeListener(this);
                } else if (newValue.intersects(platinumHubIcon.getBoundsInParent())) {
                    Interpreter.interpretCommand(player, "go platinum");
                    gameController.changePlace();
                    playerIcon.boundsInParentProperty().removeListener(this);
                }
            }
        });
    }

    private void moveWithKeys(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z:
                movePlayerIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.playerIcon),
                        UtilsController.getTranslateCenterY(this.playerIcon) - 20);
                break;
            case Q:
                movePlayerIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.playerIcon) - 20,
                        UtilsController.getTranslateCenterY(this.playerIcon));
                break;
            case S:
                movePlayerIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.playerIcon),
                        UtilsController.getTranslateCenterY(this.playerIcon) + 20);
                break;
            case D:
                movePlayerIcon(
                        Duration.millis(20),
                        UtilsController.getTranslateCenterX(this.playerIcon) + 20,
                        UtilsController.getTranslateCenterY(this.playerIcon));
                break;
            // TODO: 15-Apr-21 Enlever debug decrease quand ca sera fini
            case C:
                this.player.decreaseHealth(11);
                break;
            case F:
                this.player.increaseGameFinished();
                break;
        }
    }

    private void movePlayerIcon(Duration duration, double X, double Y) {
        if (X > 0 && Y > 0 && X < this.carnivalScene.getWidth() && Y < this.carnivalScene.getHeight()) {
            transition.setDuration(duration);
            transition.setToX(X - UtilsController.getMiddleWidth(this.playerIcon));
            transition.setToY(Y - UtilsController.getMiddleHeight(this.playerIcon));
            transition.playFromStart();
        }
    }
}
