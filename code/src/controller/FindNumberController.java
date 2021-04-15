package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.FindNumber;

import java.net.URL;
import java.util.ResourceBundle;

public class FindNumberController implements Initializable {

    private final Image plusImage = new Image("view/image/plus.png");
    private final Image lessImage = new Image("view/image/minus.png");

    private FindNumber findNumber;

    private GameController gameController;
    private Player player;

    @FXML
    private TextField numberField;

    @FXML
    private Label attemptLabel;

    @FXML
    private VBox historyBox;

    @FXML
    void goCopper() {
        Interpreter.interpretCommand(this.player, "go copper");
        this.gameController.changePlace();
    }

    @FXML
    void submitMouseClicked() {
        String chosenNumber = this.numberField.getText();
        if (!chosenNumber.equals("")) {
            int number = Integer.parseInt(chosenNumber);
            this.findNumber.playOneTurn(this.player, number);
            addHistory(chosenNumber, number);
        }
        else
            this.findNumber.mustBeNumber();
        this.numberField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.numberField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        this.numberField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                this.submitMouseClicked();
        });
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.historyBox.getChildren().clear();

        this.findNumber = (FindNumber) this.player.getPlace();
        this.attemptLabel.setText("Attempts left: " + this.findNumber.attemptProperty().get() + "!");
        this.findNumber.attemptProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0)
                this.goCopper();
            else
                this.attemptLabel.setText("Attempts left: " + newValue.intValue() + "!");
        });
        this.findNumber.start();
    }

    private void addHistory(String chosenNumber, int number) {
        HBox hBox = new HBox(20, new Label(chosenNumber));
        if (number < this.findNumber.getRand()) {
            ImageView plusIcon = new ImageView(this.plusImage);
            plusIcon.setFitHeight(20);
            plusIcon.setFitWidth(20);
            hBox.getChildren().add(plusIcon);
        } else {
            ImageView lessIcon = new ImageView(this.lessImage);
            lessIcon.setFitHeight(20);
            lessIcon.setFitWidth(20);
            hBox.getChildren().add(lessIcon);
        }

        this.historyBox.getChildren().add(hBox);
    }
}
