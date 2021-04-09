package controleur;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modele.character.Player;

public class PlayerInfoController {

    private Player player;
    private Scene scene;

    @FXML
    private VBox progress_bar;

    @FXML
    private VBox bar;

    // --- ICONS

    @FXML
    private ImageView LockerIcon;

    @FXML
    private ProgressBar healthProgressBar;

    @FXML
    private ImageView platinumKeyIcon;

    @FXML
    private ImageView goldKeyIcon;

    @FXML
    private ImageView copperKeyIcon;

    @FXML
    private ImageView chocolateEclairIcon;

    @FXML
    private ImageView cottonCandyIcon;

    @FXML
    private ImageView appleCandyIcon;

    // --- QUANTITY

    @FXML
    private Label platinumKeyQuantity;

    @FXML
    private Label goldKeyQuantity;

    @FXML
    private Label copperKeyQuantity;

    @FXML
    private Label chocolateEclairQuantity;

    @FXML
    private Label cottonCandyQuantity;

    @FXML
    private Label appleCandyQuantity;

    @FXML
    public Label coinQuantity;


    public void setPlayer(Player player) {
        this.player = player;
        this.coinQuantity.textProperty().bind(
                player.getMoney().asString()
        );
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void iconMouseEntered(MouseEvent event){
        UtilsController.changeOpacity(this.scene, (ImageView) event.getTarget(),1);
    }

    @FXML
    public void iconMouseExited(MouseEvent event) {
        UtilsController.changeOpacity(this.scene, (ImageView) event.getTarget(),0.25);
    }
}
