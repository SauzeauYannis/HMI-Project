package controller.place.game.gold;

import controller.PlaceController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
import view.CustomAlert;

import java.net.URL;
import java.util.ResourceBundle;

public class HanoiTowerController implements Initializable {

    private String srcPillar;
    private boolean diskSelected;

    private HanoiTower hanoiTower;
    private PlaceController placeController;
    private Player player;

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
    public void diskMouseClicked(MouseEvent mouseEvent) {
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
    public void diskMouseDragged(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        Pane parent = (Pane) diskIcon.getParent();

        if (parent instanceof AnchorPane) {
            diskIcon.setCursor(Cursor.CLOSED_HAND);
            diskIcon.setLayoutX(mouseEvent.getSceneX() - 15);
            diskIcon.setLayoutY(mouseEvent.getSceneY() - 45);
        }
    }

    @FXML
    public void diskMouseEntered(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getTarget()).setOpacity(0.5);
    }

    @FXML
    public void diskMouseExited(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getTarget()).setOpacity(1);
    }

    @FXML
    public void diskMouseReleased(MouseEvent mouseEvent) {
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
        this.disk1.setCursor(Cursor.HAND);
        this.disk2.setCursor(Cursor.HAND);
        this.disk3.setCursor(Cursor.HAND);
    }

    public void setHanoiTower(HanoiTower hanoiTower) {
        this.hanoiTower = hanoiTower;
    }

    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
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
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "view/design/image/hanoi_tower.gif",
                "replay",
                "return to gold hub"
        );

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go gold");
            this.placeController.changePlace();
        }
    }
}
