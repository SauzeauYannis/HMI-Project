package controller;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public abstract class UtilsController {

    private static final Alert alertFinish = new Alert(Alert.AlertType.CONFIRMATION);

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

    public static Alert getAlertFinish() {
        alertFinish.setTitle("Game finished");
        alertFinish.setContentText("Do you want to replay?");
        return alertFinish;
    }

    public static double getMiddleWidth(ImageView imageView) {
        return imageView.getFitWidth() / 2;
    }

    public static double getMiddleHeight(ImageView imageView) {
        return imageView.getFitHeight() / 2;
    }

    public static double getTranslateCenterX(ImageView imageView) {
        return imageView.getTranslateX() + getMiddleWidth(imageView);
    }

    public static double getTranslateCenterY(ImageView imageView) {
        return imageView.getTranslateY() + getMiddleHeight(imageView);
    }
}
