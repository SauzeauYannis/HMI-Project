package controller.place.game.platinum;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private GameController gameController;
    private Player player;

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
    private void checkIconClicked() {
        String guess = this.letterTextField.getText();

        if (guess.length() > 0) {
            String letter = this.hangman.playRound(guess);

            if (letter != null) {
                Label letterLabel = new Label(letter);
                letterLabel.setFont(Font.font("Carlito", FontWeight.BOLD, 17));

                if (!this.notInWordLabel.isVisible())
                    this.notInWordLabel.setVisible(true);

                this.notInWordBox.getChildren().add(letterLabel);
            }

            if (this.hangman.isWordFind()) {
                this.hangman.win(this.player);
                this.replay(true);
            } else if (!this.hangman.canContinue()) {
                this.hangman.lose(this.player);
                this.replay(false);
            } else {
                this.hangman.startRound();
                this.letterTextField.clear();
            }
        }
    }

    @FXML
    private void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode((Node) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    private void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.defaultScaleNode((Node) mouseEvent.getTarget());
    }

    @FXML
    private void goPlatinum() {
        Interpreter.interpretCommand(this.player, "go platinum");
        this.gameController.changePlace();
    }

    @FXML
    private void textFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            this.checkIconClicked();
        else
            this.letterTextField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to platinum hub"));

        this.platinumHubIcon.setCursor(Cursor.HAND);
        this.letterTextField.setCursor(Cursor.TEXT);
    }

    public void setHangman(Hangman hangman) {
        this.hangman = hangman;

        this.line1.visibleProperty().bind(
                this.hangman.trialsLeftProperty().lessThanOrEqualTo(4)
        );

        this.line2.visibleProperty().bind(
                this.hangman.trialsLeftProperty().lessThanOrEqualTo(3)
        );

        this.line3.visibleProperty().bind(
                this.hangman.trialsLeftProperty().lessThanOrEqualTo(2)
        );

        this.line4.visibleProperty().bind(
                this.hangman.trialsLeftProperty().lessThanOrEqualTo(1)
        );

        this.line5.visibleProperty().bind(
                this.hangman.trialsLeftProperty().isEqualTo(0)
        );

        this.hangmanIcon.visibleProperty().bind(
                this.hangman.trialsLeftProperty().isEqualTo(0)
        );
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.notInWordLabel.setVisible(false);

        this.wordBox.getChildren().clear();
        this.notInWordBox.getChildren().clear();
        this.letterTextField.clear();

        this.hangman.start();

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

        this.hangman.startRound();
    }

    private void replay(boolean win) {
        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goPlatinum();
    }
}
