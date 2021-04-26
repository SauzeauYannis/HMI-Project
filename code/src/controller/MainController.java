package controller;

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
import model.character.Player;
import model.command.Interpreter;
import model.place.Place;
import view.ClickableImage;
import view.CustomAlert;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final Image volumeOn = new Image("view/design/image/volume_on.png");
    private final Image volumeOff = new Image("view/design/image/volume_off.png");
    private final MediaPlayer mediaPlayer = new MediaPlayer(
            new Media(Objects.requireNonNull(getClass().getResource("../view/design/sound/theme.mp3")).toExternalForm())
    );

    private PlaceController placeController;
    private SceneController sceneController;
    private Player player;

    private boolean isVolumeOn;
    private Animation animation;

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

    @FXML
    private void scrollAnimation() {
        this.animation.play();
    }

    @FXML
    private void lookMouseClicked() {
        Interpreter.interpretCommand(this.player, "look");
    }

    @FXML
    private void helpMouseClicked() {
        this.sceneController.setCurrentPane("help");
    }

    @FXML
    private void quitMouseClicked() {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                "Quit the game",
                "Are you sure you want to exit the game?",
                "Your progress will be lost!",
                "view/design/image/quit.png"
        );

        if (alert.showAndWait().orElse(null) == ButtonType.OK)
            Interpreter.interpretCommand(this.player, "quit");
        else
            alert.close();
    }

    @FXML
    private void soundMouseClicked() {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

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

    public void setPlaceList(List<Place> placeList) {
        this.mapController.setPlaceList(placeList);
    }

    public void setPlayer(Player player) {
        this.player = player;

        player.getIsLose().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                CustomAlert alert = new CustomAlert(Alert.AlertType.INFORMATION,
                        "You lose",
                        "Oh no you've have lose too much calories you can't continue!",
                        "view/design/image/cry.png"
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

    public void setScene(Scene scene) {
        this.placeController.setScene(scene);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

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
