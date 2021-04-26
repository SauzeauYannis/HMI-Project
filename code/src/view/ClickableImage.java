package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class ClickableImage extends ImageView {

    private final StringProperty tooltipText = new SimpleStringProperty();

    public ClickableImage() {
        super();

        this.setCursor(Cursor.HAND);

        this.setOnMouseEntered(event -> this.rescale(1.2));
        this.setOnMouseExited(event -> this.rescale(1));

        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(tooltipText);
        Tooltip.install(this, tooltip);
    }

    public String getTooltipText() {
        return this.tooltipText.get();
    }

    public StringProperty tooltipTextProperty() {
        return this.tooltipText;
    }

    public void setTooltipText(String tooltipText) {
        this.tooltipText.set(tooltipText);
    }

    private void rescale(double v) {
        this.setScaleX(v);
        this.setScaleY(v);
    }
}
