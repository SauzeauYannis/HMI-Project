package controller.place.hub;

import controller.PlaceController;
import controller.PlayerInfoController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Level;
import model.character.Player;
import model.command.Interpreter;
import model.place.Place;
import model.place.exit.Exit;
import view.ClickableImage;
import view.CustomAlert;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Gold hub controller.
 */
public class GoldHubController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private PlayerInfoController playerInfoController;
    private PlaceController placeController;
    private Player player;

    @FXML
    private ClickableImage hanoiTowerIcon;

    @FXML
    private ClickableImage riddleIcon;

    @FXML
    private ImageView padlockHanoiTowerIcon;

    @FXML
    private ImageView padlockTicTacToeIcon;

    @FXML
    private ImageView padlockRiddleIcon;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip tooltipPadlock = new Tooltip("This game is lock!\nUse a gold key to unlock a game or right click on the padlock!");
        Tooltip.install(this.padlockHanoiTowerIcon, tooltipPadlock);
        Tooltip.install(this.padlockRiddleIcon, tooltipPadlock);
        Tooltip.install(this.padlockTicTacToeIcon, tooltipPadlock);

        this.padlockHanoiTowerIcon.setCursor(Cursor.CROSSHAIR);
        this.padlockRiddleIcon.setCursor(Cursor.CROSSHAIR);
        this.padlockTicTacToeIcon.setCursor(Cursor.CROSSHAIR);
    }

    /**
     * Icon mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();
        CustomAlert alert;

        if (mouseEvent.getTarget().equals(this.hanoiTowerIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(1).getPlace().getName(),
                    oldPlace.getExitList().get(1).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "view/design/image/hanoi_tower.gif",
                    "play",
                    "return to gold hub"
            );
            Interpreter.interpretCommand(this.player, "go hanoi");
        } else if (mouseEvent.getTarget().equals(this.riddleIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(2).getPlace().getName(),
                    oldPlace.getExitList().get(2).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "view/design/image/riddle.gif",
                    "play",
                    "return to gold hub"
            );
            Interpreter.interpretCommand(this.player, "go riddle");
        } else {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(3).getPlace().getName(),
                    oldPlace.getExitList().get(3).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "view/design/image/tic_tac_toe.gif",
                    "play",
                    "return to gold hub"
            );
            Interpreter.interpretCommand(this.player, "go tic");
        }

        this.placeController.changePlace();

        if (!oldPlace.equals(this.player.getPlace())) {
            if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
                this.placeController.play();
            else {
                Interpreter.interpretCommand(this.player, "go gold");
                this.placeController.changePlace();
            }
        }
    }

    /**
     * Go carnival.
     */
    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    /**
     * Padlock clicked.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void padlockClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            if (mouseEvent.getTarget().equals(this.padlockHanoiTowerIcon))
                this.playerInfoController.unlockGame("hanoi", Level.GOLD);
            else if (mouseEvent.getTarget().equals(this.padlockRiddleIcon))
                this.playerInfoController.unlockGame("riddle", Level.GOLD);
            else
                this.playerInfoController.unlockGame("tic", Level.GOLD);
        }
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets gold hub exit list.
     *
     * @param goldHubExitList the gold hub exit list
     */
    public void setGoldHubExitList(List<Exit> goldHubExitList) {
        for (Exit exit : goldHubExitList)
            switch (exit.getPlace().getName()) {
                case "Hanoi tower":
                    this.padlockHanoiTowerIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Riddle":
                    this.padlockRiddleIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Tic tac toe":
                    this.padlockTicTacToeIcon.visibleProperty().bind(exit.isLockProperty());
            }
    }

    /**
     * Sets player info controller.
     *
     * @param playerInfoController the player info controller
     */
    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
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
}
