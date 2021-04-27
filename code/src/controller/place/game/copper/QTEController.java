package controller.place.game.copper;

import controller.PlaceController;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.copper.QTE;
import view.CustomAlert;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Qte controller.
 */
public class QTEController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private final Service<Void> timerService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(3000);
                    int second = 0;
                    for (int i = 0; i < 60; i++) {
                        updateMessage(String.valueOf(second));
                        Thread.sleep(1000);
                        second++;
                    }
                    return null;
                }
            };
        }
    };
    private final Service<Void> countService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 3; i > 0; i--) {
                        updateMessage(String.valueOf(i));
                        Thread.sleep(1000);
                    }
                    return null;
                }
            };
        }
    };

    private QTE qte;
    private PlaceController placeController;
    private Player player;

    @FXML
    private TextField punchTextField;

    @FXML
    private Label punchLabel;

    @FXML
    private Label roundLabel;

    @FXML
    private Label timeLabel;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.punchTextField, new Tooltip("Type enter when you've finished"));

        this.punchTextField.setCursor(Cursor.TEXT);

        this.countService.stateProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case FAILED:
                case CANCELLED:
                case SUCCEEDED:
                    this.punchLabel.textProperty().unbind();
                    this.punchLabel.setText(QTE.PUNCHLINE[this.qte.roundProperty().get()]);
                    this.countService.reset();
            }
        });
    }

    /**
     * Punch text field key pressed.
     *
     * @param keyEvent the key event
     */
    @FXML
    public void punchTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (this.qte.winRound(this.punchTextField.getText(), this.qte.roundProperty().get(), Integer.parseInt(this.timerService.messageProperty().get()))) {
                if (this.qte.roundProperty().get() < QTE.ROUND_NUMBER - 1) {
                    this.qte.nextRound();
                    this.punchLabel.textProperty().bind(this.countService.messageProperty());
                    this.timerService.restart();
                    this.countService.start();
                } else {
                    this.qte.finish(this.player);
                    this.replay(true);
                }
            } else {
                this.qte.lose(this.player);
                this.replay(false);
            }
            this.punchTextField.clear();
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        this.qte.start();
        this.punchLabel.textProperty().bind(this.countService.messageProperty());
        this.timerService.start();
        this.countService.start();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets QTE model.
     *
     * @param qte the qte
     */
    public void setQte(QTE qte) {
        this.qte = qte;

        this.timeLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Time: " + this.timerService.messageProperty().get() + "/" + QTE.TIME[this.qte.roundProperty().get()] + "s",
                        this.timerService.messageProperty(), this.qte.roundProperty()
                )
        );

        this.timeLabel.textFillProperty().bind(
                Bindings.when(
                        Bindings.createBooleanBinding(
                                () -> {
                                    try {
                                        return Integer.parseInt(this.timerService.messageProperty().get()) < QTE.TIME[this.qte.roundProperty().get()];
                                    } catch (NumberFormatException numberFormatException) {
                                        return true;
                                    }
                                },
                                this.timerService.messageProperty(), this.qte.roundProperty()
                        ))
                        .then(Color.valueOf("green"))
                        .otherwise(Color.valueOf("red"))
        );

        this.roundLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Round: " + this.qte.roundProperty().add(1).get() + "/" + QTE.ROUND_NUMBER,
                        this.qte.roundProperty()
                )
        );
    }

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

    /*----------------------- Private methods --------------------------------*/

    /**
     * Ask to replay the game.
     *
     * @param win true if the player won the game.
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "view/design/image/qte.gif",
                "replay",
                "return to copper hub"
        );

        this.timerService.cancel();
        this.timerService.reset();

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go copper");
            this.placeController.changePlace();
        }
    }
}
