package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;

import java.net.URL;
import java.util.ResourceBundle;

public class CopperHubController implements Initializable {

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView findNumberIcon;

    @FXML
    private ImageView rockPaperScissorsIcon;

    @FXML
    private ImageView qteIcon;

    @FXML
    private ImageView padlockFindNumberIcon;

    @FXML
    private ImageView padlockRockPaperScissorsIcon;

    @FXML
    private ImageView padlockQteIcon;

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.gameController.changePlace();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: 14-Apr-21 Mettre les tooltips
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
