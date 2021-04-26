package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CustomAlert extends Alert {

    public CustomAlert(AlertType alertType, String title, String contentText, String iconPath) {
        super(alertType);

        this.setTitle(title);
        this.setContentText(contentText);

        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(
                new Image(iconPath)
        );
    }

    public CustomAlert(AlertType alertType, String title, String headerText, String contentText, String iconPath) {
        this(alertType, title, contentText, iconPath);

        this.setHeaderText(headerText);
    }

    public CustomAlert(AlertType alertType, String title, String headerText, String contentText, String iconPath, String okButtonText, String cancelButtonText) {
        this(alertType, title, contentText, headerText, iconPath);

        this.getButtonTypes().clear();
        this.getButtonTypes().add(new ButtonType(okButtonText, ButtonBar.ButtonData.OK_DONE));
        this.getButtonTypes().add(new ButtonType(cancelButtonText, ButtonBar.ButtonData.CANCEL_CLOSE));
    }
}
