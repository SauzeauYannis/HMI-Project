package gypsysCarnival.view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * The type Custom button.
 */
public class CustomButton extends Button {

    /*--------------------- Constructor -------------------------*/

    /**
     * Instantiates a new Custom button.
     */
    public CustomButton() {
        super();

        this.setCursor(Cursor.HAND);
        this.setBackground(this.setFocusBackground(false));

        this.setOnMouseEntered(event -> this.buttonFocusOn());
        this.setOnMouseExited(event -> this.buttonFocusOff());
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Button focus on.
     */
    private void buttonFocusOn() {
        this.setScaleX(1.1);
        this.setScaleY(1.1);
        this.setBackground(this.setFocusBackground(true));
    }

    /**
     * Button focus off.
     */
    private void buttonFocusOff() {
        this.setScaleX(1);
        this.setScaleY(1);
        this.setBackground(this.setFocusBackground(false));
    }

    /**
     * Sets focus background.
     *
     * @param isFocus the is focus
     * @return the focus background
     */
    private Background setFocusBackground(boolean isFocus) {
        Color color;

        if (isFocus)
            color = Color.rgb(100, 100, 255);
        else
            color = Color.rgb(140, 140, 255);

        return new Background(
                new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)
        );
    }
}
