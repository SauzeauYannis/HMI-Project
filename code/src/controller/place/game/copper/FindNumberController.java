package controller.place.game.copper;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private Scene scene;

    @FXML
    private ImageView copperHubIcon;

    @FXML
    private TextField numberField;

    @FXML
    private Label attemptLabel;

    @FXML
    private VBox historyBox;

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

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
            if (number >= 0 && number <= FindNumber.MAX_NUMBER && this.findNumber.attemptProperty().get() != 10)
                addHistory(chosenNumber, number);
        } else
            this.findNumber.mustBeNumber();
        this.numberField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.numberField, new Tooltip("Type enter or press submit button to validate"));
        Tooltip.install(this.copperHubIcon, new Tooltip("Go to copper hub"));

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

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void reset() {
        this.historyBox.getChildren().clear();
        this.findNumber = (FindNumber) player.getPlace();
        this.attemptLabel.setText("Attempts left: " + this.findNumber.attemptProperty().get() + "!");
        this.findNumber.attemptProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                attemptLabel.setText("Attempts left: " + newValue.intValue() + "!");
                if (newValue.intValue() == 0) {
                    findNumber.finish();
                    findNumber.attemptProperty().removeListener(this);
                    if (UtilsController.getAlertFinish().showAndWait().orElse(null) == ButtonType.OK)
                        reset();
                    else
                        goCopper();
                }
            }
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
