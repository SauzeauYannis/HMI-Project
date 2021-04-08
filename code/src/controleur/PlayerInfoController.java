package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import modele.character.Player;

public class PlayerInfoController {

    private Player player;

    @FXML
    private VBox progress_bar;

    @FXML
    private VBox bar;

    @FXML
    private Label platinumKeyQuantity;

    @FXML
    private Label chocolateEclairQuantity;

    @FXML
    private Label goldKeyQuantity;

    @FXML
    private Label cottonCandyQuantity;

    @FXML
    private ImageView copperKeyIcon;

    @FXML
    private Label copperKeyQuantity;

    @FXML
    private Label appleCandyQuantity;

    @FXML
    private ImageView LockerIcon;

    @FXML
    private ImageView chocolateEclairIcon;

    @FXML
    private ImageView cottonCandyIcon;

    @FXML
    private ImageView appleCandyIcon;

    @FXML
    public Label coinQuantity;


    public void setPlayer(Player player) {
        this.player = player;
        this.coinQuantity.textProperty().bind(
                player.getMoney().asString()
        );
    }
}
