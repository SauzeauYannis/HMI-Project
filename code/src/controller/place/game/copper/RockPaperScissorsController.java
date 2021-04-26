package controller.place.game.copper;

import controller.PlaceController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.copper.RockPaperScissors;

public class RockPaperScissorsController {

    private RockPaperScissors rockPaperScissors;
    private PlaceController placeController;
    private Player player;

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
    public void iconMouseClicked(MouseEvent mouseEvent) {
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

            if (this.rockPaperScissors.isPlayerWin()) {
                this.rockPaperScissors.win(this.player);
                this.replay(true);
            } else if (this.rockPaperScissors.isNPCWin()) {
                this.rockPaperScissors.lose(this.player);
                this.replay(false);
            }
        } else
            System.out.println("You need to click on the next turn button");
    }

    @FXML
    public void iconMouseEntered(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled())
            ((Node) mouseEvent.getTarget()).setOpacity(1);
    }

    @FXML
    public void iconMouseExited(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled())
            ((Node) mouseEvent.getTarget()).setOpacity(0.25);
    }

    @FXML
    public void newTurn() {
        this.rockIcon.setOpacity(0.25);
        this.paperIcon.setOpacity(0.25);
        this.scissorsIcon.setOpacity(0.25);
        this.rockNPCIcon.setOpacity(0.25);
        this.paperNPCIcon.setOpacity(0.25);
        this.scissorsNPCIcon.setOpacity(0.25);

        this.nextTurnButton.setDisable(true);
    }

    public void setRockPaperScissors(RockPaperScissors rockPaperScissors) {
        this.rockPaperScissors = rockPaperScissors;

        this.playerPointLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> player.getName() + ": " + this.rockPaperScissors.playerPointProperty().get() + " point(s)",
                        this.rockPaperScissors.playerPointProperty()
                )
        );

        this.npcPointLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> this.rockPaperScissors.getNpc().getName() + ": " + this.rockPaperScissors.NPCPointProperty().get() + " point(s)",
                        this.rockPaperScissors.NPCPointProperty()
                )
        );
    }

    public void setGameController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.rockPaperScissors.start();
        this.newTurn();
    }

    private void replay(boolean win) {
        this.rockPaperScissors.finish();

        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go copper");
            this.placeController.changePlace();
        }
    }
}
