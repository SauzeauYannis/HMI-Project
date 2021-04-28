package gypsysCarnival.controller.place.game.platinum;

import gypsysCarnival.controller.PlaceController;
import gypsysCarnival.controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.game.platinum.Karaoke;

import java.net.URL;
import java.util.ResourceBundle;

public class KaraokeController implements Initializable {

    private Karaoke karaoke;
    private PlaceController placeController;
    private Player player;
    private Scene scene;

    // ================================
    //  ELEMENTS IN VIEW
    // ================================

    @FXML
    private ImageView platinumHubIcon;

    @FXML
    private Rectangle rectangleIndicator;

    @FXML
    private Label labelLyrics;

    @FXML
    private TextField textFieldProposition;

    @FXML
    private Label labelTrials;

    // ================================
    //  SETTERS
    // ================================

    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // ================================
    //  EVENTS ON ELEMENTS
    // ================================

    // SUBMIT BUTTON
    @FXML
    void submitMouseClicked() {
        this.karaoke.setGuess(this.textFieldProposition.getText());

        if (this.karaoke.continueGame()) {
            rectangleColorFalse();
            this.karaoke.reactionCommentary();
            this.karaoke.nextTrial();
            this.karaoke.eachTrialQuestion();
        } else {
            if (this.karaoke.getGuess().equals(this.karaoke.getWord())) {
                rectangleColorTrue();
            }
            replay(this.karaoke.end(this.player));
        }

        this.textFieldProposition.clear();
    }

    // PLATINUM ICON
    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void goPlatinum() {
        Interpreter.interpretCommand(this.player, "go platinum");
        this.placeController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to platinum hub"));
        Tooltip.install(this.textFieldProposition, new Tooltip("Type enter or press submit button to validate"));

        this.textFieldProposition.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                this.submitMouseClicked();
        });
    }

    // ================================
    //  ACTIONS ON GAME AND ELEMENTS
    // ================================

    // gypsysCarnival.Game
    public void reset() {
        this.karaoke = (Karaoke) this.player.getPlace();
        this.karaoke.start();
        this.resetInterface();
        this.karaoke.firstCommentary();
        this.karaoke.eachTrialQuestion();

        this.labelTrials.textProperty().unbind();
        this.labelTrials.textProperty().bind(Bindings.convert(this.karaoke.getLeftTrials()));
    }

    private void replay(boolean win) {
        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            reset();
        else
            goPlatinum();
    }

    // Interface
    private void resetInterface() {
        this.labelTrials.setText(""+this.karaoke.getLeftTrials()); // LEGIT ???
        this.labelLyrics.setText(""+this.karaoke.getSentence());
        this.textFieldProposition.setText("");
        this.rectangleIndicator.setFill(Color.web("#444444"));
    }

    private void rectangleColorTrue() {
        this.rectangleIndicator.setFill(Color.web("#87E990"));
    }

    private void rectangleColorFalse() {
        this.rectangleIndicator.setFill(Color.web("#D9603B"));
    }

}
