package controleur;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public abstract class UtilsController {

    public static void rescaleNode(Scene scene, Node node, double newScale) {
        node.setScaleX(newScale);
        node.setScaleY(newScale);

        if (newScale == 1)
            scene.setCursor(Cursor.DEFAULT);
        else
            scene.setCursor(Cursor.HAND);
    }

    public static void changeOpacity(Scene scene, Node node, double newOpacity) {
        node.setOpacity(newOpacity);

        if (newOpacity == 1)
            rescaleNode(scene,node,1.2);
        else
            rescaleNode(scene,node,1);
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
