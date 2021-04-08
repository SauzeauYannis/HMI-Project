package controleur;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
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

    // --- ICONS ANIMATIONS

    @FXML
    public void goldKeyIconMouseEntered () {
        UtilsController.changeOpacity(this.scene,this.goldKeyIcon,1);
    }

    @FXML
    public void goldKeyIconMouseExited () {
        UtilsController.changeOpacity(this.scene,this.goldKeyIcon,0.25);
    }

    @FXML
    public void copperKeyIconMouseEntered () {
        UtilsController.changeOpacity(this.scene,this.copperKeyIcon,1);
    }

    @FXML
    public void copperKeyIconMouseExited () {
        UtilsController.changeOpacity(this.scene,this.copperKeyIcon,0.25);
    }

    @FXML
    public void platinumKeyIconMouseEntered () {
        UtilsController.changeOpacity(this.scene,this.platinumKeyIcon,1);
    }

    @FXML
    public void platinumKeyIconMouseExited () {
        UtilsController.changeOpacity(this.scene,this.platinumKeyIcon,0.25);
    }

    @FXML
    public void chocolateEclairIconMouseEntered () {
        UtilsController.changeOpacity(this.scene,this.chocolateEclairIcon,1);
    }

    @FXML
    public void chocolateEclairIconMouseExited () {
        UtilsController.changeOpacity(this.scene,this.chocolateEclairIcon,0.25);
    }

    @FXML
    public void cottonCandyIconMouseEntered () {
        UtilsController.changeOpacity(this.scene,this.cottonCandyIcon,1);
    }

    @FXML
    public void cottonCandyIconMouseExited () {
        UtilsController.changeOpacity(this.scene,this.cottonCandyIcon,0.25);
    }

    @FXML
    public void appleCandyIconMouseEntered () {
        UtilsController.changeOpacity(this.scene,this.appleCandyIcon,1);
    }

    @FXML
    public void appleCandyIconMouseExited () {
        UtilsController.changeOpacity(this.scene,this.appleCandyIcon,0.25);
    }

    //

}
