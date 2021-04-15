package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.character.Player;
import model.command.Interpreter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final Image volumeOn = new Image("view/image/volume_on.png");
    private final Image volumeOff = new Image("view/image/volume_off.png");
    private final MediaPlayer mediaPlayer = new MediaPlayer(
            new Media(Objects.requireNonNull(getClass().getResource("../view/sound/theme.mp3")).toExternalForm())
    );

    private boolean isVolumeOn = true;
    private Animation animation;

    private Player player;
    private Scene scene;

    @FXML
    private GameController gameController;

    @FXML
    private PlayerInfoController playerInfoController;

    @FXML
    private ScrollPane scrollPaneDialog;

    @FXML
    private ImageView downArrowIcon;

    @FXML
    private ImageView soundIcon;

    @FXML
    private Label labelDialog;

    @FXML
    void scrollAnimation() {
        this.animation.play();
    }

    @FXML
    public void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    public void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void lookMouseClicked() {
        Interpreter.interpretCommand(this.player, "look");
    }

    @FXML
    void helpMouseClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/help.fxml"));
        Parent root = loader.load();
        HelpController helpController = loader.getController();

        helpController.setRoot((AnchorPane) this.scene.getRoot());

        this.scene.setRoot(root);

        helpController.setScene(this.scene);
    }

    @FXML
    void quitMouseClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit the game");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Your progress will be lost!");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(
                new Image("view/image/quit.png")
        );

        if (alert.showAndWait().orElse(null) == ButtonType.OK) {
            Interpreter.interpretCommand(this.player, "quit");
        } else {
            alert.close();
        }
    }

    @FXML
    void soundMouseClicked() {
        this.isVolumeOn = !this.isVolumeOn;
        changeSoundSetting();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mediaPlayer.play();

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
    }

    public void setPlayer(Player player) {
        this.player = player;

        player.getIsLose().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose!");
                alert.setContentText("Oh no you've have lose too much calories you can't continue!");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(
                        new Image("view/image/cry.png")
                );
                alert.showAndWait();
                Interpreter.interpretCommand(player, "quit");
            }
        });

        this.playerInfoController.setPlayer(player);
        this.gameController.setPlayer(player);
        this.gameController.setPlayerInfoController(this.playerInfoController);
        this.player.getPlace().getNpc().talk("Welcome to Gypsy's Carnival!\n");
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
        } else {
            this.mediaPlayer.pause();
            this.soundIcon.setImage(this.volumeOff);
        }
    }
}
