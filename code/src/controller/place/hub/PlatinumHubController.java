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

public class PlatinumHubController implements Initializable {

    private PlayerInfoController playerInfoController;
    private PlaceController placeController;
    private Player player;

    @FXML
    private ClickableImage hangmanIcon;

    @FXML
    private ClickableImage karaokeIcon;

    @FXML
    private ImageView padlockHangmanTowerIcon;

    @FXML
    private ImageView padlockQuestionsIcon;

    @FXML
    private ImageView padlockKaraokeIcon;

    @FXML
    public void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();
        CustomAlert alert;

        if (mouseEvent.getTarget().equals(this.hangmanIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(1).getPlace().getName(),
                    oldPlace.getExitList().get(1).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "view/design/image/hangman.gif",
                    "play",
                    "return to platinum hub"
            );
            Interpreter.interpretCommand(this.player, "go hangman");
        } else if (mouseEvent.getTarget().equals(this.karaokeIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(2).getPlace().getName(),
                    oldPlace.getExitList().get(2).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "view/design/image/karaoke.gif",
                    "play",
                    "return to platinum hub"
            );
            Interpreter.interpretCommand(this.player, "go karaoke");
        } else {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(3).getPlace().getName(),
                    oldPlace.getExitList().get(3).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "view/design/image/questions.gif",
                    "play",
                    "return to platinum hub"
            );
            Interpreter.interpretCommand(this.player, "go questions");
        }

        this.placeController.changePlace();

        if (!oldPlace.equals(this.player.getPlace())) {
            if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
                this.placeController.play();
            else {
                Interpreter.interpretCommand(this.player, "go platinum");
                this.placeController.changePlace();
            }
        }
    }

    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    @FXML
    public void padlockClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            if (mouseEvent.getTarget().equals(this.padlockHangmanTowerIcon))
                this.playerInfoController.unlockGame("hangman", Level.PLATINUM);
            else if (mouseEvent.getTarget().equals(this.padlockKaraokeIcon))
                this.playerInfoController.unlockGame("karaoke", Level.PLATINUM);
            else
                this.playerInfoController.unlockGame("questions", Level.PLATINUM);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip tooltipPadlock = new Tooltip("This game is lock!\nUse a copper key to unlock a game or right click on the padlock!");
        Tooltip.install(this.padlockHangmanTowerIcon, tooltipPadlock);
        Tooltip.install(this.padlockKaraokeIcon, tooltipPadlock);
        Tooltip.install(this.padlockQuestionsIcon, tooltipPadlock);

        this.padlockHangmanTowerIcon.setCursor(Cursor.CROSSHAIR);
        this.padlockKaraokeIcon.setCursor(Cursor.CROSSHAIR);
        this.padlockQuestionsIcon.setCursor(Cursor.CROSSHAIR);
    }

    public void setPlatinumHubExitList(List<Exit> platinumHubExitList) {
        for (Exit exit : platinumHubExitList)
            switch (exit.getPlace().getName()) {
                case "Hangman":
                    this.padlockHangmanTowerIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Karaoke":
                    this.padlockKaraokeIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Questions":
                    this.padlockQuestionsIcon.visibleProperty().bind(exit.isLockProperty());
            }
    }

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
