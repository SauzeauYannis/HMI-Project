package controller.place.game.copper;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.copper.RockPaperScissors;

import java.net.URL;
import java.util.ResourceBundle;

public class RockPaperScissorsController implements Initializable {

    private RockPaperScissors rockPaperScissors;

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView copperHubIcon;

    @FXML
    private Label playerPointLabel;

    @FXML
    private Label npcPointLabel;

    @FXML
    private ImageView rockIcon;

    @FXML
    private ImageView paperIcon;

    @FXML
    private ImageView scissorsIcon;

    @FXML
    private ImageView rockNPCIcon;

    @FXML
    private ImageView paperNPCIcon;

    @FXML
    private ImageView scissorsNPCIcon;

    @FXML
    private Button nextTurnButton;

    @FXML
    void copperHubIconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void copperHubIconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void iconMouseClicked(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled()) {
            int npcTurn = this.rockPaperScissors.getNPCTurn();

            switch (npcTurn) {
                case 0:
                    this.rockNPCIcon.setOpacity(1);
                    break;
                case 1:
                    this.paperNPCIcon.setOpacity(1);
                    break;
                case 2:
                    this.scissorsNPCIcon.setOpacity(1);
            }

            this.nextTurnButton.setDisable(false);

            if (mouseEvent.getTarget().equals(this.rockIcon))
                this.rockPaperScissors.checkWinner(RockPaperScissors.ROSHAMBO[0], npcTurn);
            else if (mouseEvent.getTarget().equals(this.paperIcon))
                this.rockPaperScissors.checkWinner(RockPaperScissors.ROSHAMBO[1], npcTurn);
            else
                this.rockPaperScissors.checkWinner(RockPaperScissors.ROSHAMBO[2], npcTurn);
        } else System.out.println("You need to click on the next turn button");
    }

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled())
            UtilsController.changeOpacity(this.scene, (ImageView) mouseEvent.getTarget(), 1, true);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled())
            UtilsController.changeOpacity(this.scene, (ImageView) mouseEvent.getTarget(), 0.25, false);
    }

    @FXML
    void newTurn() {
        this.rockIcon.setOpacity(0.25);
        this.paperIcon.setOpacity(0.25);
        this.scissorsIcon.setOpacity(0.25);
        this.rockNPCIcon.setOpacity(0.25);
        this.paperNPCIcon.setOpacity(0.25);
        this.scissorsNPCIcon.setOpacity(0.25);

        this.nextTurnButton.setDisable(true);
    }

    @FXML
    void goCopper() {
        Interpreter.interpretCommand(this.player, "go copper");
        this.gameController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.copperHubIcon, new Tooltip("Go to copper hub"));
        Tooltip.install(this.rockIcon, new Tooltip("Click to play rock"));
        Tooltip.install(this.paperIcon, new Tooltip("Click to play paper"));
        Tooltip.install(this.scissorsIcon, new Tooltip("Click to play scissors"));
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
        this.newTurn();

        this.rockPaperScissors = (RockPaperScissors) this.player.getPlace();
        this.rockPaperScissors.start();

        this.playerPointLabel.setText(player.getName() + ": " + this.rockPaperScissors.playerPointProperty().get() + " point(s)");
        this.npcPointLabel.setText(player.getName() + ": " + this.rockPaperScissors.NPCPointProperty().get() + " point(s)");

        this.rockPaperScissors.playerPointProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                playerPointLabel.setText(player.getName() + ": " + newValue.intValue() + " point(s)");
                if (newValue.intValue() == RockPaperScissors.POINT_TO_WIN) {
                    hasWin(true);
                    rockPaperScissors.playerPointProperty().removeListener(this);
                }
            }
        });

        this.rockPaperScissors.NPCPointProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                npcPointLabel.setText(rockPaperScissors.getNpc().getName() + ": " + newValue.intValue() + " point(s)");
                if (newValue.intValue() == RockPaperScissors.POINT_TO_WIN) {
                    hasWin(false);
                    rockPaperScissors.NPCPointProperty().removeListener(this);
                }
            }
        });
    }

    private void hasWin(boolean win) {
        this.rockPaperScissors.finish();

        if (win)
            this.rockPaperScissors.win(this.player);
        else
            this.rockPaperScissors.lose(this.player);

        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goCopper();
    }
}
