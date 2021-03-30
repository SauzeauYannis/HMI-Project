package vue;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Start extends AnchorPane {

    /**
     * Creates an AnchorPane layout.
     */
    public Start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("start.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            System.err.println("Start constructor error");
            exception.printStackTrace();
        }
    }
}
