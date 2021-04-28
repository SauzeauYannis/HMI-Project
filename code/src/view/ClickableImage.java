package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

/**
 * The Clickable image class.
 */
public class ClickableImage extends ImageView {

    /*--------------------- Private members -------------------------*/

    private final StringProperty tooltipText = new SimpleStringProperty();

    /*--------------------- Constructor -------------------------*/

    /**
     * Instantiates a new Clickable image.
     */
    public ClickableImage() {
        super();

        this.setCursor(Cursor.HAND);

        this.setOnMouseEntered(event -> this.rescale(1.2));
        this.setOnMouseExited(event -> this.rescale(1));

        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(tooltipText);
        Tooltip.install(this, tooltip);
    }

    /*----------------------- Getters and setters --------------------------------*/

    /**
     * Gets tooltip text.
     *
     * @return the tooltip text
     */
    public String getTooltipText() {
        return this.tooltipText.get();
    }

    /**
     * Tooltip text property string property.
     *
     * @return the string property
     */
    public StringProperty tooltipTextProperty() {
        return this.tooltipText;
    }

    /**
     * Sets tooltip text.
     *
     * @param tooltipText the tooltip text
     */
    public void setTooltipText(String tooltipText) {
        this.tooltipText.set(tooltipText);
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Rescale.
     *
     * @param v the v
     */
    private void rescale(double v) {
        this.setScaleX(v);
        this.setScaleY(v);
    }
}
