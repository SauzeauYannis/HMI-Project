package controller.place.game.gold;

import controller.GameController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.gold.HanoiTower;

import java.net.URL;
import java.util.ResourceBundle;

public class HanoiTowerController implements Initializable {

    private String srcPillar;
    private boolean diskSelected;

    private HanoiTower hanoiTower;
    private GameController gameController;
    private Player player;

    @FXML
    private ImageView goldHubIcon;

    @FXML
    private AnchorPane pane;

    @FXML
    private Ellipse disk1;

    @FXML
    private Ellipse disk2;

    @FXML
    private Ellipse disk3;

    @FXML
    private VBox aPillarBox;

    @FXML
    private VBox bPillarBox;

    @FXML
    private VBox cPillarBox;

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

    @FXML
    private void diskMouseClicked(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof VBox && parent.getChildren().get(0).equals(diskIcon) && !this.diskSelected) {
            this.diskSelected = true;

            resetSrcPillar(parent);

            diskIcon.setLayoutX(695);
            diskIcon.setLayoutY(570);

            parent.getChildren().remove(diskIcon);
            this.pane.getChildren().add(diskIcon);
        }
    }

    @FXML
    private void diskMouseDragged(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof AnchorPane) {
            diskIcon.setCursor(Cursor.CLOSED_HAND);
            diskIcon.setLayoutX(mouseEvent.getSceneX() - 15);
            diskIcon.setLayoutY(mouseEvent.getSceneY() - 45);
        }
    }

    @FXML
    private void diskMouseEntered(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getTarget()).setOpacity(0.5);
    }

    @FXML
    private void diskMouseExited(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getTarget()).setOpacity(1);
    }

    @FXML
    private void diskMouseReleased(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof AnchorPane) {
            diskIcon.setCursor(Cursor.HAND);
            if (diskIcon.getBoundsInParent().intersects(this.aPillarBox.getBoundsInParent()))
                this.moveDisk("a", this.aPillarBox, diskIcon);
            else if (diskIcon.getBoundsInParent().intersects(this.bPillarBox.getBoundsInParent()))
                this.moveDisk("b", this.bPillarBox, diskIcon);
            else if (diskIcon.getBoundsInParent().intersects(this.cPillarBox.getBoundsInParent()))
                this.moveDisk("c", this.cPillarBox, diskIcon);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.goldHubIcon, new Tooltip("Go to gold hub"));

        this.goldHubIcon.setCursor(Cursor.HAND);
        this.disk1.setCursor(Cursor.HAND);
        this.disk2.setCursor(Cursor.HAND);
        this.disk3.setCursor(Cursor.HAND);
    }

    public void setHanoiTower(HanoiTower hanoiTower) {
        this.hanoiTower = hanoiTower;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.hanoiTower.start();

        this.aPillarBox.getChildren().clear();
        this.bPillarBox.getChildren().clear();
        this.cPillarBox.getChildren().clear();

        this.aPillarBox.getChildren().addAll(this.disk1, this.disk2, this.disk3);

        this.diskSelected = false;
    }

    private void resetSrcPillar(Pane parent) {
        if (parent.equals(this.aPillarBox))
            this.srcPillar = "a";
        else if (parent.equals(this.bPillarBox))
            this.srcPillar = "b";
        else
            this.srcPillar = "c";
    }

    private void moveDisk(String destPillar, VBox pillarBox, Ellipse diskIcon) {
        pillarBox.getChildren().add(0, diskIcon);

        if (this.hanoiTower.canMoveDisk(this.srcPillar, destPillar)) {
            if (this.hanoiTower.isWin()) {
                this.hanoiTower.hasWin(this.player, true);
                this.replay(true);
            } else
                this.diskSelected = false;
        } else {
            this.hanoiTower.hasWin(this.player, false);
            this.replay(false);
        }
    }

    private void replay(boolean win) {
        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goGold();
    }
}
