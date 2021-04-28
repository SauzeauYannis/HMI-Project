package gypsysCarnival.controller.place.game.gold;

import gypsysCarnival.controller.PlaceController;
import gypsysCarnival.controller.UtilsController;
import gypsysCarnival.model.place.game.gold.TicTacToe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {

    private PlaceController placeController;
    private TicTacToe tictactoe;
    private Player player;
    private Scene scene;


    // ================================
    //  ELEMENTS IN VIEW
    // ================================

    @FXML
    private ImageView goldHubIcon;

    @FXML
    private AnchorPane blockChoose;

    @FXML
    private GridPane boardGame;

    @FXML
    private AnchorPane legendCross;

    @FXML
    private AnchorPane legendCircle;

    @FXML
    private Button buttonChoose;

    @FXML
    private TextField textFieldChoose;

    @FXML
    private ImageView crossCenter;

    @FXML
    private ImageView crossLeft;

    @FXML
    private ImageView crossRight;

    @FXML
    private ImageView crossTop;

    @FXML
    private ImageView crossTopLeft;

    @FXML
    private ImageView crossTopRight;

    @FXML
    private ImageView crossBot;

    @FXML
    private ImageView crossBotLeft;

    @FXML
    private ImageView crossBotRight;


    // ================================
    //  SETTERS
    // ================================

    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setTictactoe(TicTacToe tictactoe) {
        this.tictactoe = tictactoe;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


    // ================================
    //  EVENTS ON ELEMENTS
    // ================================


    //-- Icon goGold
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
        this.placeController.changePlace();
    }

    //-- Icon Cross (and maybe circle)
    @FXML
    void iconCrossMouseEntered(MouseEvent mouseEvent) {
        ImageView crossTarget = (ImageView) mouseEvent.getTarget();
        if(crossTarget.getOpacity() != 1) {
            crossTarget.setOpacity(0.25);
        }
    }

    @FXML
    void iconCrossMouseExited(MouseEvent mouseEvent) {
        ImageView crossTarget = (ImageView) mouseEvent.getTarget();
        if(crossTarget.getOpacity() != 1) {
            crossTarget.setOpacity(0);
        }

    }

    @FXML
    void iconCrossMouseClicked(MouseEvent mouseEvent) {
        ImageView crossTarget = (ImageView) mouseEvent.getTarget();
        crossTarget.setOpacity(1);
    }

    @FXML
    void isButtonClicked(MouseEvent mouseEvent){
        this.blockChoose.setOpacity(0);
        this.blockChoose.setDisable(true);

        this.boardGame.setOpacity(1);
        this.boardGame.setDisable(false);

        this.legendCircle.setOpacity(1);
        this.legendCross.setOpacity(1);

        this.tictactoe.testPrint();
    }


    // ================================
    //  ACTIONS ON GAME AND ELEMENTS
    // ================================

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.goldHubIcon, new Tooltip("Go to gold hub"));
    }


}
