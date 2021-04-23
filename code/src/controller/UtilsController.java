package controller;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

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

    // TODO: 23-Apr-21 Enlever cette fonction
    public static void rescaleNode(Scene scene, Node node, double newScale) {
        node.setScaleX(newScale);
        node.setScaleY(newScale);

        if (newScale == 1)
            scene.setCursor(Cursor.DEFAULT);
        else
            scene.setCursor(Cursor.HAND);
    }

    public static void changeOpacity(Scene scene, Node node, double newOpacity, boolean isCursorHand) {
        node.setOpacity(newOpacity);

        if (isCursorHand)
            scene.setCursor(Cursor.HAND);
        else
            scene.setCursor(Cursor.DEFAULT);
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

    public static double getMiddleWidth(ImageView imageView) {
        return imageView.getFitWidth() / 2;
    }

    public static double getMiddleHeight(ImageView imageView) {
        return imageView.getFitHeight() / 2;
    }
}
