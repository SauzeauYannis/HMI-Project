package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import model.character.Player;
import model.command.Interpreter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Start controller.
 */
public class StartController implements Initializable {

    private AnchorPane mainPane;
    private MainController mainController;

    private Player player;
    private Scene scene;

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    /**
     * Play mouse clicked.
     */
    public void playMouseClicked() {
        scene.setRoot(this.mainPane);
        this.mainController.setScene(scene);
    }

    /**
     * Play mouse entered.
     */
    @FXML
    void playMouseEntered() {
        this.buttonFocusOn(this.playButton);
    }

    /**
     * Play mouse exited.
     */
    @FXML
    void playMouseExited() {
        this.buttonFocusOff(this.playButton);
    }

    /**
     * Quit mouse clicked.
     */
    @FXML
    void quitMouseClicked() {
        scene.setCursor(Cursor.WAIT);
        Interpreter.interpretCommand(this.player, "quit");
    }

    /**
     * Quit mouse entered.
     */
    @FXML
    void quitMouseEntered() {
        this.buttonFocusOn(this.quitButton);
    }

    /**
     * Quit mouse exited.
     */
    @FXML
    void quitMouseExited() {
        this.buttonFocusOff(this.quitButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
            this.mainPane = loader.load();
            this.mainController = loader.getController();

            this.playButton.setBackground(this.setFocusBackground(false));
            this.quitButton.setBackground(this.setFocusBackground(false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.mainController.setPlayer(player);
    }

    /**
     * Sets scene.
     *
     * @param scene the scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
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
