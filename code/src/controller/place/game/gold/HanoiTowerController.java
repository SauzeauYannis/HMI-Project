package controller.place.game.gold;

import controller.GameController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import model.character.Player;
import model.command.Interpreter;

import java.net.URL;
import java.util.ResourceBundle;

public class HanoiTowerController implements Initializable {

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView goldHubIcon;

    @FXML
    private AnchorPane pane;

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
    void test(MouseEvent mouseEvent) {
        Ellipse diskIcon = (Ellipse) mouseEvent.getTarget();
        this.aPillarBox.getChildren().remove(diskIcon);
        diskIcon.setLayoutX(mouseEvent.getSceneX());
        diskIcon.setLayoutY(mouseEvent.getSceneY());
        this.pane.getChildren().add(diskIcon);
        UtilsController.changeOpacity(this.scene, diskIcon, 0.25, true);
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
}
