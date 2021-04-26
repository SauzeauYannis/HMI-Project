package controller;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

// TODO: 26-Apr-21 Virer cette classe
public abstract class UtilsController {

    public static final int DEFAULT_SCALE = 1;
    private static final Alert ALERT_FINISH = new Alert(Alert.AlertType.CONFIRMATION);

    public static void rescaleNode(Node node, double newScale) {
        node.setScaleX(newScale);
        node.setScaleY(newScale);
    }

    public static void defaultScaleNode(Node node) {
        rescaleNode(node, DEFAULT_SCALE);
    }

    public static void rescaleNode(Scene scene, Node node, double newScale) {
        node.setScaleX(newScale);
        node.setScaleY(newScale);

        if (newScale == 1)
            scene.setCursor(Cursor.DEFAULT);
        else
            scene.setCursor(Cursor.HAND);
    }

    public static Alert getAlertFinish(boolean win) {
        ALERT_FINISH.setTitle("Game finished");
        ALERT_FINISH.setContentText("Do you want to replay?");

        if (win)
            ALERT_FINISH.setHeaderText("You win!");
        else
            ALERT_FINISH.setHeaderText("You lose!");

        return ALERT_FINISH;
    }
}
