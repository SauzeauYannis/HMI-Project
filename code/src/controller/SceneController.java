package controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {

    private final HashMap<String, Pane> paneHashMap = new HashMap<>();
    private final Scene scene;

    public SceneController(Scene scene) {
        this.scene = scene;
    }

    public void addPane(String paneName, Pane pane) {
        this.paneHashMap.put(paneName, pane);
    }

    public void setCurrentPane(String paneName) {
        this.scene.setRoot(this.paneHashMap.get(paneName));
    }
}
