package controller.place.game.copper;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.copper.FindNumber;

import java.net.URL;
import java.util.ResourceBundle;

public class FindNumberController implements Initializable {

    private final Image plusImage = new Image("view/design/image/plus.png");
    private final Image lessImage = new Image("view/design/image/minus.png");

    private FindNumber findNumber;
    private GameController gameController;
    private Player player;

    @FXML
    private ImageView copperHubIcon;

    @FXML
    private TextField numberField;

    @FXML
    private Label attemptLabel;

    @FXML
    private VBox historyBox;

    @FXML
    private void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode((Node) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    private void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.defaultScaleNode((Node) mouseEvent.getTarget());
    }

    @FXML
    private void goCopper() {
        Interpreter.interpretCommand(this.player, "go copper");
        this.gameController.changePlace();
    }

    @FXML
    void numberFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            this.playTurn();
    }

    @FXML
    private void submitMouseClicked() {
        this.playTurn();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.numberField, new Tooltip("Type enter or press submit button to validate"));
        Tooltip.install(this.copperHubIcon, new Tooltip("Go to copper hub"));

        this.copperHubIcon.setCursor(Cursor.HAND);
        this.numberField.setCursor(Cursor.TEXT);

        this.numberField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }

    public void setFindNumber(FindNumber findNumber) {
        this.findNumber = findNumber;

        this.attemptLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Attempts left: " + this.findNumber.attemptProperty().get() + "!",
                        this.findNumber.attemptProperty()
                )
        );
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.historyBox.getChildren().clear();
        this.findNumber.start();
    }

    private void playTurn() {
        String chosenNumber = this.numberField.getText();

        if (!chosenNumber.equals("")) {
            int number = Integer.parseInt(chosenNumber);

            if (this.findNumber.playOneTurn(this.player, number)) {
                if (this.findNumber.canContinue())
                    this.addHistory(chosenNumber, number);
                else
                    this.replay(this.findNumber.isWin());
            }
        } else
            this.findNumber.mustBeNumber();

        this.numberField.clear();
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

    private void replay(boolean win) {
        this.findNumber.finish();
        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goCopper();
    }
}
