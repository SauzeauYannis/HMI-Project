package gypsysCarnival.controller.place.game.platinum;

import gypsysCarnival.controller.PlaceController;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.game.platinum.Karaoke;
import gypsysCarnival.view.CustomAlert;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Karaoke game controller.
 */
public class KaraokeController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private Karaoke karaoke;
    private PlaceController placeController;
    private Player player;

    @FXML
    private Rectangle rectangleIndicator;

    @FXML
    private Label labelLyrics;

    @FXML
    private TextField textFieldProposition;

    @FXML
    private Label labelTrials;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.textFieldProposition, new Tooltip("Type enter or press submit button to validate"));

        this.textFieldProposition.setCursor(Cursor.TEXT);
        this.textFieldProposition.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                this.submitMouseClicked();
        });
    }

    /**
     * Submit mouse clicked.
     */
    // SUBMIT BUTTON
    @FXML
    public void submitMouseClicked() {
        this.karaoke.setGuess(this.textFieldProposition.getText());
        this.karaoke.nextTrial();

        if (this.karaoke.continueGame()) {
            rectangleColorFalse();
            this.karaoke.reactionCommentary();
            this.karaoke.eachTrialQuestion();
        } else {
            if (this.karaoke.getGuess().equals(this.karaoke.getWord())) {
                rectangleColorTrue();
            }
            replay(this.karaoke.end(this.player));
        }

        this.textFieldProposition.clear();
    }

    /**
     * Reset.
     */
    public void reset() {
        this.karaoke.start();
        this.resetInterface();
        this.karaoke.firstCommentary();
        this.karaoke.eachTrialQuestion();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets place controller.
     *
     * @param placeController the place controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
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
     * Sets karaoke.
     *
     * @param karaoke the karaoke
     */
    public void setKaraoke(Karaoke karaoke) {
        this.karaoke = karaoke;
        this.labelTrials.textProperty().bind(Bindings.convert(this.karaoke.getLeftTrials()));
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Replay.
     *
     * @param win the win
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "gypsysCarnival/view/design/image/karaoke.gif",
                "replay",
                "return to platinum hub"
        );

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
        Interpreter.interpretCommand(this.player, "go platinum");
        this.placeController.changePlace();
        }
    }

    /**
     * Reset interface.
     */
    private void resetInterface() {
        this.labelLyrics.setText(this.karaoke.getSentence());
        this.textFieldProposition.clear();
        this.rectangleIndicator.setFill(Color.web("#BCBCBC"));
    }

    /**
     * Rectangle color true.
     */
    private void rectangleColorTrue() {
        this.rectangleIndicator.setFill(Color.web("#87E990"));
    }

    /**
     * Rectangle color false.
     */
    private void rectangleColorFalse() {
        this.rectangleIndicator.setFill(Color.web("#D9603B"));
    }
}
