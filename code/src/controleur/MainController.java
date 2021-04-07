package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import modele.character.Player;
import modele.command.Interpreter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final Image volumeOn = new Image("vue/image/volume_on.png");
    private final Image volumeOff = new Image("vue/image/volume_off.png");
    private final MediaPlayer mediaPlayer = new MediaPlayer(
            new Media(getClass().getResource("../vue/sound/music.mp3").toExternalForm())
    );

    private boolean isVolumeOn = true;

    private Player player;
    private Scene scene;

    @FXML
    private Button testBuyKey;

    @FXML
    private ImageView helpIcon;

    @FXML
    private ImageView soundIcon;

    @FXML
    private ImageView quitIcon;

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
    private Label copperKeyQuantity;

    @FXML
    private Label appleCandyQuantity;

    /**
     * Help mouse clicked.
     *
     * @throws IOException the io exception
     */
    public void helpMouseClicked() throws IOException {
        this.mediaPlayer.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/help.fxml"));
        Parent root = loader.load();
        HelpController helpController = loader.getController();

        scene.setRoot(root);

        helpController.setPlayer(this.player);
        helpController.setScene(this.scene);
    }

    /**
     * Help mouse entered.
     */
    @FXML
    void helpMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.helpIcon, 1.2);
    }

    /**
     * Help mouse exited.
     */
    @FXML
    void helpMouseExited() {
        UtilsController.rescaleNode(this.scene, this.helpIcon,1);
    }

    @FXML
    void quitMouseClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit the game");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Your progress will be lost!");

        if (alert.showAndWait().orElse(null) == ButtonType.OK) {
            Interpreter.interpretCommand(this.player, "quit");
        } else {
            alert.close();
        }
    }

    @FXML
    void quitMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.quitIcon, 1.2);
    }

    @FXML
    void quitMouseExited() {
        UtilsController.rescaleNode(this.scene, this.quitIcon, 1);
    }

    /**
     * Sound mouse clicked.
     */
    @FXML
    void soundMouseClicked() {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

    /**
     * Sound mouse entered.
     */
    @FXML
    void soundMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.soundIcon,1.2);
    }

    /**
     * Sound mouse exited.
     */
    @FXML
    void soundMouseExited() {
        UtilsController.rescaleNode(this.scene, this.soundIcon,1);
    }

    @FXML
    void testBuyKeyClicked() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mediaPlayer.play();
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets scene.
     *
     * @param scene the scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void changeSoundSetting() {
        if (this.isVolumeOn) {
            this.mediaPlayer.play();
            this.soundIcon.setImage(this.volumeOn);
        }
        else {
            this.mediaPlayer.pause();
            this.soundIcon.setImage(this.volumeOff);
        }
    }
}
