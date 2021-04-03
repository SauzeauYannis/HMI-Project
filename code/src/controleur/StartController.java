package controleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import modele.character.Player;
import modele.command.Interpreter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Start controller.
 */
public class StartController implements Initializable {

    private final Image volumeOn = new Image("vue/image/volume_on.png");
    private final Image volumeOff = new Image("vue/image/volume_off.png");

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
     * Help mouse entered.
     *
     * @param event the event
     */
    @FXML
    void helpMouseEntered(MouseEvent event) {
        this.rescaleNode(this.helpIcon, 1.2);
        scene.setCursor(Cursor.HAND);
    }

    /**
     * Help mouse exited.
     *
     * @param event the event
     */
    @FXML
    void helpMouseExited(MouseEvent event) {
        this.rescaleNode(this.helpIcon,1);
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Play mouse entered.
     *
     * @param event the event
     */
    @FXML
    void playMouseEntered(MouseEvent event) {
        this.buttonFocusOn(this.playButton);
        scene.setCursor(Cursor.HAND);
    }

    /**
     * Play mouse exited.
     *
     * @param event the event
     */
    @FXML
    void playMouseExited(MouseEvent event) {
        this.buttonFocusOff(this.playButton);
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Quit mouse clicked.
     *
     * @param event the event
     */
    @FXML
    void quitMouseClicked(MouseEvent event) {
        scene.setCursor(Cursor.WAIT);
        Interpreter.interpretCommand(player, "quit");
    }

    /**
     * Quit mouse entered.
     *
     * @param event the event
     */
    @FXML
    void quitMouseEntered(MouseEvent event) {
        this.buttonFocusOn(this.quitButton);
        scene.setCursor(Cursor.HAND);
    }

    /**
     * Quit mouse exited.
     *
     * @param event the event
     */
    @FXML
    void quitMouseExited(MouseEvent event) {
        this.buttonFocusOff(this.quitButton);
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Sound mouse clicked.
     *
     * @param event the event
     */
    @FXML
    void soundMouseClicked(MouseEvent event) {
        this.changeVolumeIcon(this.volumeOff, this.volumeOn);
        this.isVolumeOn = !this.isVolumeOn;
    }

    /**
     * Sound mouse entered.
     *
     * @param event the event
     */
    @FXML
    void soundMouseEntered(MouseEvent event) {
        //this.changeVolumeIcon(this.volumeOff, this.volumeOn);
        this.rescaleNode(this.soundIcon,1.2);
        scene.setCursor(Cursor.HAND);
    }

    /**
     * Sound mouse exited.
     *
     * @param event the event
     */
    @FXML
    void soundMouseExited(MouseEvent event) {
        //this.changeVolumeIcon(this.volumeOn, this.volumeOff);
        this.rescaleNode(this.soundIcon,1);
        scene.setCursor(Cursor.DEFAULT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playButton.setBackground(this.setFocusBackground(false));
        this.quitButton.setBackground(this.setFocusBackground(false));
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

    private void rescaleNode(Node node, double newScale) {
        node.setScaleX(newScale);
        node.setScaleY(newScale);
    }

    private void changeVolumeIcon(Image imageIfVolumeOn, Image imageIfVolumeOff) {
        if (this.isVolumeOn)
            this.soundIcon.setImage(imageIfVolumeOn);
        else
            this.soundIcon.setImage(imageIfVolumeOff);
    }

    private void buttonFocusOn(Button button) {
        this.rescaleNode(button, 1.1);
        button.setBackground(this.setFocusBackground(true));
    }

    private void buttonFocusOff(Button button) {
        this.rescaleNode(button, 1);
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
