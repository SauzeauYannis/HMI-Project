package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import modele.character.Player;
import modele.command.Interpreter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Start controller.
 */
public class StartController implements Initializable {

    private final Image volumeOn = new Image("vue/image/volume_on.png");
    private final Image volumeOff = new Image("vue/image/volume_off.png");
    private final MediaPlayer mediaPlayer = new MediaPlayer(
            new Media(getClass().getResource("../vue/sound/music.mp3").toExternalForm())
    );

    private boolean isVolumeOn = true;

    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane root;

    @FXML
    private HBox iconsBox;

    @FXML
    private ImageView helpIcon;

    @FXML
    private ImageView soundIcon;

    @FXML
    private Label name;

    @FXML
    private Button playButton;

    @FXML
    private ImageView sceneImage;

    @FXML
    private Button quitButton;

    /**
     * Help mouse clicked.
     *
     * @param event the event
     */
    public void helpMouseClicked(MouseEvent event) throws IOException {
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
     *
     * @param event the event
     */
    @FXML
    void helpMouseEntered(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, this.helpIcon, 1.2);
    }

    /**
     * Help mouse exited.
     *
     * @param event the event
     */
    @FXML
    void helpMouseExited(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, this.helpIcon,1);
    }

    /**
     * Play mouse clicked.
     *
     * @param event the event
     */
    public void playMouseClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();

        scene.setRoot(root);
    }

    /**
     * Play mouse entered.
     *
     * @param event the event
     */
    @FXML
    void playMouseEntered(MouseEvent event) {
        this.buttonFocusOn(this.playButton);
    }

    /**
     * Play mouse exited.
     *
     * @param event the event
     */
    @FXML
    void playMouseExited(MouseEvent event) {
        this.buttonFocusOff(this.playButton);
    }

    /**
     * Quit mouse clicked.
     *
     * @param event the event
     */
    @FXML
    void quitMouseClicked(MouseEvent event) {
        scene.setCursor(Cursor.WAIT);
        Interpreter.interpretCommand(this.player, "quit");
    }

    /**
     * Quit mouse entered.
     *
     * @param event the event
     */
    @FXML
    void quitMouseEntered(MouseEvent event) {
        this.buttonFocusOn(this.quitButton);
    }

    /**
     * Quit mouse exited.
     *
     * @param event the event
     */
    @FXML
    void quitMouseExited(MouseEvent event) {
        this.buttonFocusOff(this.quitButton);
    }

    /**
     * Sound mouse clicked.
     *
     * @param event the event
     */
    @FXML
    void soundMouseClicked(MouseEvent event) {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

    /**
     * Sound mouse entered.
     *
     * @param event the event
     */
    @FXML
    void soundMouseEntered(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, this.soundIcon,1.2);
    }

    /**
     * Sound mouse exited.
     *
     * @param event the event
     */
    @FXML
    void soundMouseExited(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, this.soundIcon,1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playButton.setBackground(this.setFocusBackground(false));
        this.quitButton.setBackground(this.setFocusBackground(false));
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

    private void buttonFocusOn(Button button) {
        UtilsController.rescaleNode(this.scene, button, 1.1);
        button.setBackground(this.setFocusBackground(true));
    }

    private void buttonFocusOff(Button button) {
        UtilsController.rescaleNode(this.scene, button, 1);
        button.setBackground(this.setFocusBackground(false));
    }

    private Background setFocusBackground(boolean isFocus) {
        Color color = Color.rgb(140, 140, 255);
        if (isFocus)
            color = Color.rgb(100, 100, 255);

        return new Background(
                new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)
        );
    }
}
