package gypsysCarnival.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import gypsysCarnival.model.Level;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.Game;
import gypsysCarnival.view.ClickableImage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Player info gypsysCarnival.controller.
 */
public class PlayerInfoController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private Player player;

    @FXML
    private VBox progress_bar;

    @FXML
    private VBox bar;

    @FXML
    private Label gameFinishedLabel;

    @FXML
    private ImageView playerIcon;

    // --- ICONS
    @FXML
    private ImageView padlockIcon;

    @FXML
    private ProgressBar healthProgressBar;

    @FXML
    private ClickableImage platinumKeyIcon;

    @FXML
    private ClickableImage goldKeyIcon;

    @FXML
    private ClickableImage copperKeyIcon;

    @FXML
    private ClickableImage chocolateEclairIcon;

    @FXML
    private ClickableImage cottonCandyIcon;

    @FXML
    private ClickableImage appleCandyIcon;

    // --- QUANTITY
    @FXML
    private Label platinumKeyQuantity;

    @FXML
    private Label goldKeyQuantity;

    @FXML
    private Label copperKeyQuantity;

    @FXML
    private Label chocolateEclairQuantity;

    @FXML
    private Label cottonCandyQuantity;

    @FXML
    private Label appleCandyQuantity;

    @FXML
    public Label coinQuantity;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip tooltipHealth = new Tooltip();
        tooltipHealth.textProperty().bind(
                Bindings.format("%.0f/100 calories", this.healthProgressBar.progressProperty().multiply(100))
        );
        Tooltip.install(this.healthProgressBar, tooltipHealth);

        this.setBindOpacity(this.copperKeyIcon, this.copperKeyQuantity);
        this.setBindOpacity(this.goldKeyIcon, this.goldKeyQuantity);
        this.setBindOpacity(this.platinumKeyIcon, this.platinumKeyQuantity);
        this.setBindOpacity(this.appleCandyIcon, this.appleCandyQuantity);
        this.setBindOpacity(this.cottonCandyIcon, this.cottonCandyQuantity);
        this.setBindOpacity(this.chocolateEclairIcon, this.chocolateEclairQuantity);
    }

    /**
     * Item mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void itemMouseClicked(MouseEvent mouseEvent) {
        ImageView icon = (ImageView) mouseEvent.getTarget();

        if (mouseEvent.getButton() == MouseButton.PRIMARY)
            lookItem(icon);
        else if (mouseEvent.getButton() == MouseButton.SECONDARY)
            useItem(icon);
    }

    /**
     * Unlock game.
     *
     * @param gameName the game name
     * @param level    the level
     */
    public void unlockGame(String gameName, Level level) {
        int oldInventorySize = this.player.getItems().size();
        Interpreter.interpretCommand(this.player, "unlock " + gameName);
        if (this.player.getItems().size() < oldInventorySize) {
            switch (level) {
                case COPPER:
                    this.removeItem(this.copperKeyIcon.getId());
                    break;
                case GOLD:
                    this.removeItem(this.goldKeyIcon.getId());
                    break;
                case PLATINUM:
                    this.removeItem(this.platinumKeyIcon.getId());
                    break;
            }
        }
    }

    /**
     * Add item.
     *
     * @param id the id
     */
    public void addItem(String id) {
        this.updateItem(id, +1);
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.coinQuantity.textProperty().bind(
                player.getMoney().asString()
        );
        this.healthProgressBar.progressProperty().bind(
                player.getHealth().divide(100F)
        );
        this.playerIcon.opacityProperty().bind(
                player.getHealth().divide(100F)
        );
        player.getGamesFinished().addListener((observable, oldValue, newValue) -> {
            this.gameFinishedLabel.setText(newValue.intValue() + "/" + Game.NB_GAMES);
            this.bar.setPrefHeight(newValue.doubleValue() * (this.progress_bar.getHeight() / 9F));
            if (newValue.intValue() == 9) {
                this.padlockIcon.setImage(
                        new Image("gypsysCarnival/view/design/image/unlock.png")
                );
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You finished all games!");
                alert.setContentText("Congratulations, you can now enter the sparkling caravan!");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(
                        new Image("gypsysCarnival/view/design/image/clapping.png")
                );
                alert.showAndWait();
            }
        });
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Look item.
     *
     * @param icon the icon
     */
    private void lookItem(ImageView icon) {
        if (icon.equals(copperKeyIcon))
            Interpreter.interpretCommand(this.player, "look copper");
        else if (icon.equals(goldKeyIcon))
            Interpreter.interpretCommand(this.player, "look gold");
        else if (icon.equals(platinumKeyIcon))
            Interpreter.interpretCommand(this.player, "look platinum");
        else if (icon.equals(appleCandyIcon))
            Interpreter.interpretCommand(this.player, "look apple");
        else if (icon.equals(cottonCandyIcon))
            Interpreter.interpretCommand(this.player, "look cotton");
        else
            Interpreter.interpretCommand(this.player, "look chocolate");
    }

    /**
     * Use item.
     *
     * @param icon the icon
     */
    private void useItem(ImageView icon) {
        int oldInventorySize = this.player.getItems().size();

        if (icon.equals(copperKeyIcon))
            Interpreter.interpretCommand(this.player, "use copper");
        else if (icon.equals(goldKeyIcon))
            Interpreter.interpretCommand(this.player, "use gold");
        else if (icon.equals(platinumKeyIcon))
            Interpreter.interpretCommand(this.player, "use platinum");
        else if (icon.equals(appleCandyIcon))
            Interpreter.interpretCommand(this.player, "use apple");
        else if (icon.equals(cottonCandyIcon))
            Interpreter.interpretCommand(this.player, "use cotton");
        else
            Interpreter.interpretCommand(this.player, "use chocolate");

        if (this.player.getItems().size() < oldInventorySize)
            this.removeItem(icon.getId());
    }

    /**
     * Remove item.
     *
     * @param id the id
     */
    private void removeItem(String id) {
        this.updateItem(id, -1);
    }

    /**
     * Update item.
     *
     * @param id       the id
     * @param addValue the add value
     */
    private void updateItem(String id, int addValue) {
        if (copperKeyIcon.getId().equals(id))
            this.updateItemQuantity(copperKeyQuantity, addValue);
        else if (goldKeyIcon.getId().equals(id))
            this.updateItemQuantity(goldKeyQuantity, addValue);
        else if (platinumKeyIcon.getId().equals(id))
            this.updateItemQuantity(platinumKeyQuantity, addValue);
        else if (appleCandyIcon.getId().equals(id))
            this.updateItemQuantity(appleCandyQuantity, addValue);
        else if (cottonCandyIcon.getId().equals(id))
            this.updateItemQuantity(cottonCandyQuantity, addValue);
        else
            this.updateItemQuantity(chocolateEclairQuantity, addValue);
    }

    /**
     * Update item quantity.
     *
     * @param keyQuantity the key quantity
     * @param addValue    the add value
     */
    private void updateItemQuantity(Label keyQuantity, int addValue) {
        keyQuantity.setText(String.valueOf(
                Integer.parseInt(keyQuantity.getText()) + addValue
        ));
    }

    /**
     * Sets bind opacity.
     *
     * @param icon     the icon
     * @param quantity the quantity
     */
    private void setBindOpacity(ImageView icon, Label quantity) {
        icon.opacityProperty().bind(
                Bindings.when(quantity.textProperty().isEqualTo("0"))
                        .then(0.25)
                        .otherwise(1)
        );
    }
}
