package gypsysCarnival.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * The Scene gypsysCarnival.controller.
 */
public class SceneController {

    /*--------------------- Private members -------------------------*/

    private final HashMap<String, Pane> paneHashMap = new HashMap<>();
    private final Scene scene;

    /*--------------------- Constructor -------------------------*/

    /**
     * Instantiates a new Scene gypsysCarnival.controller.
     *
     * @param scene the scene
     */
    public SceneController(Scene scene) {
        this.scene = scene;
    }

    /*--------------------- Public methods -------------------------*/

    /**
     * Add pane.
     *
     * @param paneName the pane name
     * @param pane     the pane
     */
    public void addPane(String paneName, Pane pane) {
        this.paneHashMap.put(paneName, pane);
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets current pane.
     *
     * @param paneName the pane name
     */
    public void setCurrentPane(String paneName) {
        this.scene.setRoot(this.paneHashMap.get(paneName));
    }
}
