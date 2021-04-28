package gypsysCarnival.controller.place.game.copper;

import gypsysCarnival.controller.PlaceController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.game.copper.RockPaperScissors;
import gypsysCarnival.view.CustomAlert;

/**
 * The Rock paper scissors game gypsysCarnival.controller.
 */
public class RockPaperScissorsController {

    /*--------------------- Private members -------------------------*/

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

    /*--------------------- Public methods -------------------------*/

    /**
     * Icon mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
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

    /**
     * Icon mouse entered.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconMouseEntered(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled())
            ((Node) mouseEvent.getTarget()).setOpacity(1);
    }

    /**
     * Icon mouse exited.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconMouseExited(MouseEvent mouseEvent) {
        if (this.nextTurnButton.isDisabled())
            ((Node) mouseEvent.getTarget()).setOpacity(0.25);
    }

    /**
     * New turn.
     */
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

    /**
     * Reset.
     */
    public void reset() {
        this.rockPaperScissors.start();
        this.newTurn();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets rock paper scissors gypsysCarnival.model.
     *
     * @param rockPaperScissors the rock paper scissors
     */
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

    /**
     * Sets place gypsysCarnival.controller.
     *
     * @param placeController the place gypsysCarnival.controller
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
     * @see #iconMouseClicked(MouseEvent)
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "gypsysCarnival/view/design/image/rock_paper_scissors.gif",
                "replay",
                "return to copper hub"
        );

        this.rockPaperScissors.finish();

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go copper");
            this.placeController.changePlace();
        }
    }
}
