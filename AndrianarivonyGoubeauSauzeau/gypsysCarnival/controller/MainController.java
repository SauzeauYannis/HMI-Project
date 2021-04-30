package gypsysCarnival.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.Place;
import gypsysCarnival.view.ClickableImage;
import gypsysCarnival.view.CustomAlert;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Main gypsysCarnival.controller.
 */
public class MainController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private final Image volumeOn = new Image("gypsysCarnival/view/design/image/volume_on.png");
    private final Image volumeOff = new Image("gypsysCarnival/view/design/image/volume_off.png");
    private final MediaPlayer mediaPlayer = new MediaPlayer(
            new Media(Objects.requireNonNull(getClass().getResource("../view/design/sound/theme.mp3")).toExternalForm())
    );

    private boolean isVolumeOn;
    private Animation animation;

    private PlaceController placeController;
    private SceneController sceneController;
    private Player player;

    @FXML
    private MapController mapController;

    @FXML
    private PlayerInfoController playerInfoController;

    @FXML
    private Tab gameTab;

    @FXML
    private ScrollPane scrollPaneDialog;

    @FXML
    private ClickableImage downArrowIcon;

    @FXML
    private ClickableImage soundIcon;

    @FXML
    private Label labelDialog;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.isVolumeOn = true;

        this.changeSoundSetting();

        System.setOut(new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) {
                        labelDialog.setText(labelDialog.getText() + (char) b);
                    }
                }
        ));

        this.animation = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(this.scrollPaneDialog.vvalueProperty(), 1))
        );

        this.downArrowIcon.visibleProperty().bind(
                this.scrollPaneDialog.vvalueProperty().lessThan(1)
        );

        this.labelDialog.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() > oldValue.doubleValue())
                this.scrollAnimation();
        });

        this.scrollPaneDialog.vvalueProperty().setValue(1);
    }

    /**
     * Scroll animation.
     */
    @FXML
    public void scrollAnimation() {
        this.animation.play();
    }

    /**
     * Look mouse clicked.
     */
    @FXML
    public void lookMouseClicked() {
        Interpreter.interpretCommand(this.player, "look");
    }

    /**
     * Help mouse clicked.
     */
    @FXML
    public void helpMouseClicked() {
        this.sceneController.setCurrentPane("help");
    }

    /**
     * Quit mouse clicked.
     */
    @FXML
    public void quitMouseClicked() {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                "Quit the game",
                "Are you sure you want to exit the game?",
                "Your progress will be lost!",
                "gypsysCarnival/view/design/image/quit.png"
        );

        if (alert.showAndWait().orElse(null) == ButtonType.OK)
            Interpreter.interpretCommand(this.player, "quit");
        else
            alert.close();
    }

    /**
     * Sound mouse clicked.
     */
    @FXML
    public void soundMouseClicked() {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets place list.
     *
     * @param placeList the place list
     */
    public void setPlaceList(List<Place> placeList) {
        this.mapController.setPlaceList(placeList);
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;

        player.getIsLose().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                CustomAlert alert = new CustomAlert(Alert.AlertType.INFORMATION,
                        "You lose",
                        "Oh no you've have lose too much calories you can't continue!",
                        "gypsysCarnival/view/design/image/cry.png"
                );

                alert.showAndWait();
                Interpreter.interpretCommand(player, "quit");
            }
        });

        this.placeController = new PlaceController(this.gameTab, player);

        this.playerInfoController.setPlayer(player);
        this.placeController.setPlayerInfoController(this.playerInfoController);

        this.player.getPlace().getNpc().talk("Welcome to Gypsy's Carnival!");
    }

    /**
     * Sets scene.
     *
     * @param scene the scene
     */
    public void setScene(Scene scene) {
        this.placeController.setScene(scene);
    }

    /**
     * Sets scene gypsysCarnival.controller.
     *
     * @param sceneController the scene gypsysCarnival.controller
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Change sound setting.
     */
    private void changeSoundSetting() {
        if (this.isVolumeOn) {
            this.soundIcon.setTooltipText("Click to set the volume off");
            this.mediaPlayer.play();
            this.soundIcon.setImage(this.volumeOn);
        } else {
            this.soundIcon.setTooltipText("Click to set the volume on");
            this.mediaPlayer.pause();
            this.soundIcon.setImage(this.volumeOff);
        }
    }
}
