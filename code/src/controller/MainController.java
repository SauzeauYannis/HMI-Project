package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.character.Player;
import model.command.Interpreter;
import model.place.Place;

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

    private GameController gameController;
    private SceneController sceneController;
    private Player player;

    private boolean isVolumeOn = true;
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
    private ImageView downArrowIcon;

    @FXML
    private ImageView lookIcon;

    @FXML
    private ImageView helpIcon;

    @FXML
    private ImageView quitIcon;

    @FXML
    private ImageView soundIcon;

    @FXML
    private Label labelDialog;

    @FXML
    void scrollAnimation() {
        this.animation.play();
    }

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode((Node) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.defaultScaleNode((Node) mouseEvent.getTarget());
    }

    @FXML
    void lookMouseClicked() {
        Interpreter.interpretCommand(this.player, "look");
    }

    @FXML
    void helpMouseClicked() {
        this.sceneController.setCurrentPane("help");
    }

    @FXML
    void quitMouseClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit the game");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Your progress will be lost!");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(
                new Image("view/design/image/quit.png")
        );

        if (alert.showAndWait().orElse(null) == ButtonType.OK)
            Interpreter.interpretCommand(this.player, "quit");
        else
            alert.close();
    }

    @FXML
    void soundMouseClicked() {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.downArrowIcon, new Tooltip("Click to scroll down"));
        Tooltip.install(this.lookIcon, new Tooltip("Click to look the place"));
        Tooltip.install(this.helpIcon, new Tooltip("Click to go to help page"));
        Tooltip.install(this.quitIcon, new Tooltip("Click to quit the game"));

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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose!");
                alert.setContentText("Oh no you've have lose too much calories you can't continue!");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(
                        new Image("view/design/image/cry.png")
                );
                alert.showAndWait();
                Interpreter.interpretCommand(player, "quit");
            }
        });

        this.gameController = new GameController(this.gameTab, player);

        this.playerInfoController.setPlayer(player);
        this.gameController.setPlayerInfoController(this.playerInfoController);

        this.player.getPlace().getNpc().talk("Welcome to Gypsy's Carnival!");
    }

    public void setScene(Scene scene) {
        this.gameController.setScene(scene);
        this.playerInfoController.setScene(scene);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    private void changeSoundSetting() {
        if (this.isVolumeOn) {
            Tooltip.install(this.soundIcon, new Tooltip("Click to set the volume off"));
            this.mediaPlayer.play();
            this.soundIcon.setImage(this.volumeOn);
        } else {
            Tooltip.install(this.soundIcon, new Tooltip("Click to set the volume on"));
            this.mediaPlayer.pause();
            this.soundIcon.setImage(this.volumeOff);
        }
    }
}
