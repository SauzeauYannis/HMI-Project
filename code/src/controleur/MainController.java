package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import modele.character.Player;
import modele.command.Interpreter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final Image volumeOn = new Image("vue/image/volume_on.png");
    private final Image volumeOff = new Image("vue/image/volume_off.png");
    private final MediaPlayer mediaPlayer = new MediaPlayer(
            new Media(Objects.requireNonNull(getClass().getResource("../vue/sound/theme.mp3")).toExternalForm())
    );

    private boolean isVolumeOn = true;

    private Player player;
    private Scene scene;

    @FXML
    private GameController gameController;

    @FXML
    private PlayerInfoController playerInfoController;

    @FXML
    private ImageView helpIcon;

    @FXML
    private ImageView soundIcon;

    @FXML
    private ImageView quitIcon;

    @FXML
    void helpMouseClicked() throws IOException {
        this.mediaPlayer.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/help.fxml"));
        Parent root = loader.load();
        HelpController helpController = loader.getController();

        scene.setRoot(root);

        helpController.setPlayer(this.player);
        helpController.setScene(this.scene);
    }

    @FXML
    void helpMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.helpIcon, 1.2);
    }

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

    @FXML
    void soundMouseClicked() {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

    @FXML
    void soundMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.soundIcon,1.2);
    }

    @FXML
    void soundMouseExited() {
        UtilsController.rescaleNode(this.scene, this.soundIcon,1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mediaPlayer.play();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.gameController.setPlayer(player);
        this.playerInfoController.setPlayer(player);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.gameController.setScene(scene);
        this.playerInfoController.setScene(scene);
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
