package controller.place;

import controller.GameController;
import controller.UtilsController;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import model.character.Player;
import model.command.Interpreter;
import model.place.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class CarnivalController implements Initializable {

    private PathTransition pathTransitionToCopperHub;
    private PathTransition pathTransitionToGoldHub;
    private PathTransition pathTransitionToPlatinumHub;
    private PathTransition pathTransitionToFoodShop;
    private PathTransition pathTransitionToKeyShop;
    private PathTransition pathTransitionToSparklingCaravan;

    private GameController gameController;
    private Player player;
    private Scene scene;

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
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getTarget().equals(this.copperHubIcon))
                this.pathTransitionToCopperHub.play();
            else if (mouseEvent.getTarget().equals(this.goldHubIcon))
                this.pathTransitionToGoldHub.play();
            else if (mouseEvent.getTarget().equals(this.platinumHubIcon))
                this.pathTransitionToPlatinumHub.play();
            else if (mouseEvent.getTarget().equals(this.foodShopIcon))
                this.pathTransitionToFoodShop.play();
            else if (mouseEvent.getTarget().equals(this.keyShopIcon))
                this.pathTransitionToKeyShop.play();
            else if (mouseEvent.getTarget().equals(this.sparklingCaravanIcon)) {
                if (this.padlockCaravanIcon.isVisible())
                    Interpreter.interpretCommand(this.player, "go sparkling");
                else
                    this.pathTransitionToSparklingCaravan.play();
            }
        }
    }

    @FXML
    void padlockClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            Interpreter.interpretCommand(this.player, "unlock sparkling");
            if (player.getGamesFinished().isEqualTo(Game.NB_GAMES).get())
                this.padlockCaravanIcon.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.keyShopIcon, new Tooltip("Go to the key shop!"));
        Tooltip.install(this.foodShopIcon, new Tooltip("Go to the food shop!"));
        Tooltip.install(this.sparklingCaravanIcon, new Tooltip("Go to the sparkling caravan!"));
        Tooltip.install(this.copperHubIcon, new Tooltip("Go to the copper hub!"));
        Tooltip.install(this.goldHubIcon, new Tooltip("Go to the gold hub!"));
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to the platinum hub!"));
        Tooltip.install(this.padlockCaravanIcon, new Tooltip("This game is lock!\nFinish the 9 games an right click on the padlock to unlock that place!"));

        double xOffset = UtilsController.getMiddleWidth(this.playerIcon);
        double yOffset  = UtilsController.getMiddleHeight(this.playerIcon);

        MoveTo startMoveTo = new MoveTo(xOffset, yOffset);

        VLineTo firstVLineTo = new VLineTo(yOffset + 250);
        VLineTo secondVLineToTop = new VLineTo(yOffset + 150);
        VLineTo secondVLineToBottom = new VLineTo(yOffset + 350);

        Path pathToCopperHub = new Path();
        pathToCopperHub.getElements().add(startMoveTo);
        pathToCopperHub.getElements().add(firstVLineTo);
        pathToCopperHub.getElements().add(new HLineTo(xOffset + 250));
        pathToCopperHub.getElements().add(secondVLineToTop);
        this.pathTransitionToCopperHub = new PathTransition(Duration.seconds(2), pathToCopperHub, playerIcon);

        Path pathToGoldHub = new Path();
        pathToGoldHub.getElements().add(startMoveTo);
        pathToGoldHub.getElements().add(firstVLineTo);
        pathToGoldHub.getElements().add(new HLineTo(xOffset + 485));
        pathToGoldHub.getElements().add(secondVLineToTop);
        this.pathTransitionToGoldHub = new PathTransition(Duration.seconds(3), pathToGoldHub, playerIcon);

        Path pathToPlatinumHub = new Path();
        pathToPlatinumHub.getElements().add(startMoveTo);
        pathToPlatinumHub.getElements().add(firstVLineTo);
        pathToPlatinumHub.getElements().add(new HLineTo(xOffset + 690));
        pathToPlatinumHub.getElements().add(secondVLineToTop);
        this.pathTransitionToPlatinumHub = new PathTransition(Duration.seconds(4), pathToPlatinumHub, playerIcon);

        Path pathToFoodShop = new Path();
        pathToFoodShop.getElements().add(startMoveTo);
        pathToFoodShop.getElements().add(firstVLineTo);
        pathToFoodShop.getElements().add(new HLineTo(xOffset + 75));
        pathToFoodShop.getElements().add(secondVLineToBottom);
        this.pathTransitionToFoodShop = new PathTransition(Duration.seconds(2), pathToFoodShop, playerIcon);

        Path pathToKeyShop = new Path();
        pathToKeyShop.getElements().add(startMoveTo);
        pathToKeyShop.getElements().add(firstVLineTo);
        pathToKeyShop.getElements().add(new HLineTo(xOffset + 385));
        pathToKeyShop.getElements().add(secondVLineToBottom);
        this.pathTransitionToKeyShop = new PathTransition(Duration.seconds(3), pathToKeyShop, playerIcon);

        Path pathToSparklingCaravan = new Path();
        pathToSparklingCaravan.getElements().add(startMoveTo);
        pathToSparklingCaravan.getElements().add(firstVLineTo);
        pathToSparklingCaravan.getElements().add(new HLineTo(xOffset + 680));
        pathToSparklingCaravan.getElements().add(secondVLineToBottom);
        this.pathTransitionToSparklingCaravan = new PathTransition(Duration.seconds(4), pathToSparklingCaravan, playerIcon);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
        // TODO: 14-Apr-21 Enlever lors du rendu
        player.earnMoney(1000);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void reset() {
        this.playerIcon.setTranslateX(0);
        this.playerIcon.setTranslateY(0);
        this.playerIcon.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (newValue.intersects(keyShopIcon.getBoundsInParent()))
                    go("key", this);
                else if (newValue.intersects(foodShopIcon.getBoundsInParent()))
                    go("food", this);
                else if (newValue.intersects(sparklingCaravanIcon.getBoundsInParent()))
                    go("sparkling", this);
                else if (newValue.intersects(copperHubIcon.getBoundsInParent()))
                    go("copper", this);
                else if (newValue.intersects(goldHubIcon.getBoundsInParent()))
                    go("gold", this);
                else if (newValue.intersects(platinumHubIcon.getBoundsInParent()))
                    go("platinum", this);
            }
        });
    }

    private void go(String place, ChangeListener<Bounds> boundsChangeListener) {
        Interpreter.interpretCommand(this.player, "go " + place);
        this.gameController.changePlace();
        this.playerIcon.boundsInParentProperty().removeListener(boundsChangeListener);
    }
}
