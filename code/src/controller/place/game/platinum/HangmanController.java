package controller.place.game.platinum;

import controller.GameController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.character.Player;
import model.command.Interpreter;

import java.net.URL;
import java.util.ResourceBundle;

public class HangmanController implements Initializable {

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView platinumHubIcon;

    @FXML
    private Line line1;

    @FXML
    private Line line2;

    @FXML
    private Line line3;

    @FXML
    private Line line4;

    @FXML
    private Line line5;

    @FXML
    private ImageView hangmanIcon;

    @FXML
    private HBox wordBox;

    @FXML
    private TextField letterTextField;

    @FXML
    private VBox notInWordBox;

    @FXML
    void checkIconClicked() {
        this.letterTextField.clear();
    }

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void goPlatinum() {
        Interpreter.interpretCommand(this.player, "go platinum");
        this.gameController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to platinum hub"));
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
