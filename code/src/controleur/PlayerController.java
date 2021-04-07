package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PlayerController  {

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
    private Label coinQuantity;

    public void incrementMoney(int quantity) {
        this.coinQuantity.setText(
                String.valueOf(Integer.parseInt(this.coinQuantity.getText()) + quantity)
        );
    }
}
