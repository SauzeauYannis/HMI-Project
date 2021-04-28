package gypsysCarnival.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The Custom alert class.
 */
public class CustomAlert extends Alert {

    /*--------------------- Constructors -------------------------*/

    /**
     * Instantiates a new Custom alert.
     *
     * @param alertType   the alert type
     * @param title       the title
     * @param contentText the content text
     * @param iconPath    the icon path
     */
    public CustomAlert(AlertType alertType, String title, String contentText, String iconPath) {
        super(alertType);

        this.setTitle(title);
        this.setContentText(contentText);

        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(
                new Image(iconPath)
        );
    }

    /**
     * Instantiates a new Custom alert.
     *
     * @param alertType   the alert type
     * @param title       the title
     * @param headerText  the header text
     * @param contentText the content text
     * @param iconPath    the icon path
     */
    public CustomAlert(AlertType alertType, String title, String headerText, String contentText, String iconPath) {
        this(alertType, title, contentText, iconPath);

        this.setHeaderText(headerText);
    }

    /**
     * Instantiates a new Custom alert.
     *
     * @param alertType        the alert type
     * @param title            the title
     * @param headerText       the header text
     * @param contentText      the content text
     * @param iconPath         the icon path
     * @param okButtonText     the ok button text
     * @param cancelButtonText the cancel button text
     */
    public CustomAlert(AlertType alertType, String title, String headerText, String contentText, String iconPath, String okButtonText, String cancelButtonText) {
        this(alertType, title, headerText, contentText, iconPath);

        this.getButtonTypes().clear();
        this.getButtonTypes().add(new ButtonType(okButtonText, ButtonBar.ButtonData.OK_DONE));
        this.getButtonTypes().add(new ButtonType(cancelButtonText, ButtonBar.ButtonData.CANCEL_CLOSE));
    }
}
