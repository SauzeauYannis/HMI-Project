package controller.place.game.copper;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import model.character.Player;
import model.command.Interpreter;
import model.place.game.QTE;

import java.net.URL;
import java.util.ResourceBundle;

public class QTEController implements Initializable {

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

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView copperHubIcon;

    @FXML
    private TextField punchTextField;

    @FXML
    private Label punchLabel;

    @FXML
    private Label roundLabel;

    @FXML
    private Label timeLabel;

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
        this.timerService.cancel();
        this.timerService.reset();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.copperHubIcon, new Tooltip("Go to copper hub"));
        Tooltip.install(this.punchTextField, new Tooltip("Type enter when you've finished"));

        this.punchTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (this.qte.winRound(this.punchTextField.getText(), this.qte.roundProperty().get(), Integer.parseInt(this.timerService.messageProperty().get()))) {
                    if (this.qte.roundProperty().get() < QTE.ROUND_NUMBER - 1) {
                        this.qte.nextRound();
                        this.punchLabel.textProperty().bind(
                                this.countService.messageProperty()
                        );
                        this.timerService.restart();
                        this.countService.start();
                    } else {
                        this.qte.finish(this.player);
                        this.replay();
                    }
                } else {
                    this.qte.lose(this.player);
                    this.replay();
                }
                this.punchTextField.clear();
            }
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
        this.qte = (QTE) player.getPlace();
        this.qte.start();

        this.timeLabel.textProperty().unbind();
        this.timeLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Time: " + this.timerService.messageProperty().get() + "/" + QTE.TIME[this.qte.roundProperty().get()] + "s",
                        this.timerService.messageProperty(), this.qte.roundProperty()
                )
        );
        this.timeLabel.textFillProperty().unbind();
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
        this.roundLabel.textProperty().unbind();
        this.roundLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Round: " + this.qte.roundProperty().add(1).get() + "/" + QTE.ROUND_NUMBER,
                        this.qte.roundProperty()
                )
        );
        this.punchLabel.textProperty().bind(
                this.countService.messageProperty()
        );

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

        this.timerService.start();
        this.countService.start();
    }

    private void replay() {
        if (UtilsController.getAlertFinish().showAndWait().orElse(null) == ButtonType.OK) {
            this.timerService.cancel();
            this.timerService.reset();
            this.reset();
        }
        else
            this.goCopper();
    }
}
