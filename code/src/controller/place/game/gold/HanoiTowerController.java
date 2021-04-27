package controller.place.game.gold;

import controller.PlaceController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

/**
 * The Hanoi tower game controller.
 */
public class HanoiTowerController implements Initializable {

    /*--------------------- Private members -------------------------*/

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

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.disk1.setCursor(Cursor.HAND);
        this.disk2.setCursor(Cursor.HAND);
        this.disk3.setCursor(Cursor.HAND);
    }

    /**
     * Disk mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
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

    /**
     * Disk mouse dragged.
     *
     * @param mouseEvent the mouse event
     */
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

    /**
     * Disk mouse entered.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void diskMouseEntered(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getTarget()).setOpacity(0.5);
    }

    /**
     * Disk mouse exited.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void diskMouseExited(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getTarget()).setOpacity(1);
    }

    /**
     * Disk mouse released.
     *
     * @param mouseEvent the mouse event
     */
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

    /**
     * Reset.
     */
    public void reset() {
        this.hanoiTower.start();

        this.aPillarBox.getChildren().clear();
        this.bPillarBox.getChildren().clear();
        this.cPillarBox.getChildren().clear();

        this.aPillarBox.getChildren().addAll(this.disk1, this.disk2, this.disk3);

        this.diskSelected = false;
    }


    /*----------------------- Setters --------------------------------*/

    /**
     * Sets hanoi tower model.
     *
     * @param hanoiTower the hanoi tower
     */
    public void setHanoiTower(HanoiTower hanoiTower) {
        this.hanoiTower = hanoiTower;
    }

    /**
     * Sets place controller.
     *
     * @param placeController the place controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Reset src pillar.
     *
     * @param parent the parent
     * @see #diskMouseClicked(MouseEvent)
     */
    private void resetSrcPillar(Pane parent) {
        if (parent.equals(this.aPillarBox))
            this.srcPillar = "a";
        else if (parent.equals(this.bPillarBox))
            this.srcPillar = "b";
        else
            this.srcPillar = "c";
    }

    /**
     * Move disk.
     *
     * @param destPillar the dest pillar
     * @param pillarBox  the pillar box
     * @param diskIcon   the disk icon
     * @see #diskMouseReleased(MouseEvent)
     */
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

    /**
     * Ask to replay the game.
     *
     * @param win true if the player won the game.
     */
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
