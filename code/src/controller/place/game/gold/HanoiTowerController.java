package controller.place.game.gold;

import controller.GameController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
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

    private HanoiTower hanoiTower;
    private String srcPillar;

    private GameController gameController;
    private Player player;
    private Scene scene;

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

    @FXML
    void diskMouseClicked(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof VBox && parent.getChildren().get(0).equals(diskIcon)) {
            resetSrcPillar(parent);

            diskIcon.setLayoutX(695);
            diskIcon.setLayoutY(570);

            parent.getChildren().remove(diskIcon);
            this.pane.getChildren().add(diskIcon);
        }
    }

    @FXML
    void diskMouseDragged(MouseEvent mouseEvent) {
        this.scene.setCursor(Cursor.CLOSED_HAND);

        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof AnchorPane) {
            diskIcon.setLayoutX(mouseEvent.getSceneX() - 15);
            diskIcon.setLayoutY(mouseEvent.getSceneY() - 45);
        }
    }

    @FXML
    void diskMouseEntered(MouseEvent mouseEvent) {
        UtilsController.changeOpacity(this.scene, (Ellipse) mouseEvent.getTarget(), 0.5, true);
    }

    @FXML
    void diskMouseExited(MouseEvent mouseEvent) {
        UtilsController.changeOpacity(this.scene, (Ellipse) mouseEvent.getTarget(), 1, false);
    }

    @FXML
    void diskMouseReleased(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof AnchorPane) {
            if (diskIcon.getBoundsInParent().intersects(this.aPillarBox.getBoundsInParent())) {
                this.moveDisk("a", this.aPillarBox, diskIcon);
            } else if (diskIcon.getBoundsInParent().intersects(this.bPillarBox.getBoundsInParent())) {
                this.moveDisk("b", this.bPillarBox, diskIcon);
            } else if (diskIcon.getBoundsInParent().intersects(this.cPillarBox.getBoundsInParent())) {
                this.moveDisk("c", this.cPillarBox, diskIcon);
            }
        }
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
        this.hanoiTower = (HanoiTower) this.player.getPlace();
        this.hanoiTower.start();

        this.aPillarBox.getChildren().clear();
        this.bPillarBox.getChildren().clear();
        this.cPillarBox.getChildren().clear();

        this.aPillarBox.getChildren().add(this.disk1);
        this.aPillarBox.getChildren().add(this.disk2);
        this.aPillarBox.getChildren().add(this.disk3);
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
        if (!this.hanoiTower.wrongCommand(this.srcPillar + " " + destPillar)) {
            pillarBox.getChildren().add(0, diskIcon);
            if (this.hanoiTower.moveDisk(this.srcPillar, destPillar)) {
                if (this.hanoiTower.isWin()) {
                    this.hanoiTower.hasWin(this.player, true);
                    this.replay();
                }
            } else {
                this.hanoiTower.hasWin(this.player, false);
                this.replay();
            }
        }
    }

    private void replay() {
        if (UtilsController.getAlertFinish().showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goGold();
    }
}
