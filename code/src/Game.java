import controller.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.character.Player;
import model.place.Place;

import java.io.IOException;
import java.util.List;

/**
 * The type Game.
 */
public class Game extends Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        List<Place> placeList = Place.generateAllPlaces();
        Player player = new Player("Benjapied Tablenuit", placeList.get(0));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/start.fxml"));
        Parent root = loader.load();
        StartController startController = loader.getController();

        Scene scene = new Scene(root);

        startController.setPlayer(player);
        startController.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gypsy's Carnaval");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
