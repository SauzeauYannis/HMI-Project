package controleur;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;

public abstract class UtilsController {

    public static void rescaleNode(Scene scene, Node node, double newScale) {
        node.setScaleX(newScale);
        node.setScaleY(newScale);

        if (newScale == 1)
            scene.setCursor(Cursor.DEFAULT);
        else
            scene.setCursor(Cursor.HAND);
    }
}
