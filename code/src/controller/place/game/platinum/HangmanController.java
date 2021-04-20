package controller.place.game.platinum;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.platinum.Hangman;

import java.net.URL;
import java.util.ResourceBundle;

public class HangmanController implements Initializable {

    private Hangman hangman;
    private final ChangeListener<Number> numberChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            switch (newValue.intValue()) {
                case 4:
                    line1.setVisible(true);
                    break;
                case 3:
                    line2.setVisible(true);
                    break;
                case 2:
                    line3.setVisible(true);
                    break;
                case 1:
                    line4.setVisible(true);
                    break;
                case 0:
                    line5.setVisible(true);
                    hangmanIcon.setVisible(true);
                    hangman.lose(player);
                    replay(false);
            }
        }
    };

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView platinumHubIcon;

    @FXML
    private Line line1;

    @FXML
    private Line line2;

    @FXML
    private Line line3;

    @FXML
    private Line line4;

    @FXML
    private Line line5;

    @FXML
    private ImageView hangmanIcon;

    @FXML
    private HBox wordBox;

    @FXML
    private TextField letterTextField;

    @FXML
    private Label notInWordLabel;

    @FXML
    private VBox notInWordBox;

    @FXML
    void checkIconClicked() {
        String guess = this.letterTextField.getText();

        if (guess.length() > 0) {
            String letter = this.hangman.playRound(guess);

            if (letter != null && this.hangman.trialsLeftProperty().get() != Hangman.NB_TRY) {
                Label letterLabel = new Label(letter);
                letterLabel.setFont(Font.font("Carlito", FontWeight.BOLD, 17));

                if (!this.notInWordLabel.isVisible())
                    this.notInWordLabel.setVisible(true);

                this.notInWordBox.getChildren().add(letterLabel);
            } else if (this.hangman.isWordFind()) {
                    this.hangman.win(this.player);
                    this.replay(true);
                    return;
            }

            if (this.hangman.trialsLeftProperty().get() > 0) {
                this.hangman.startRound();
                this.letterTextField.clear();
            }
        }
    }

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
        this.gameController.changePlace();
    }

    @FXML
    void textFieldKeyPressed() {
        this.letterTextField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to platinum hub"));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void reset() {
        this.line1.setVisible(false);
        this.line2.setVisible(false);
        this.line3.setVisible(false);
        this.line4.setVisible(false);
        this.line5.setVisible(false);
        this.hangmanIcon.setVisible(false);
        this.notInWordLabel.setVisible(false);
        this.wordBox.getChildren().clear();
        this.notInWordBox.getChildren().clear();
        this.letterTextField.clear();

        this.hangman = (Hangman) this.player.getPlace();
        this.hangman.start();
        this.hangman.startRound();

        for (int i = 0; i < this.hangman.getWordToFind().get().length(); i++) {
            Label letter = new Label();
            letter.setFont(Font.font("Carlito", FontWeight.BOLD, 20));
            int finalI = i;
            letter.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(this.hangman.getWordToFind().get().charAt(finalI)),
                            this.hangman.getWordToFind()
                    )
            );

            this.wordBox.getChildren().add(letter);
        }

        this.hangman.trialsLeftProperty().removeListener(numberChangeListener);
        this.hangman.trialsLeftProperty().addListener(numberChangeListener);
    }

    private void replay(boolean win) {
        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goPlatinum();
    }
}
