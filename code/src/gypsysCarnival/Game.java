package gypsysCarnival;

import gypsysCarnival.controller.HelpController;
import gypsysCarnival.controller.MainController;
import gypsysCarnival.controller.SceneController;
import gypsysCarnival.controller.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.place.Place;

import java.io.IOException;
import java.util.List;

/**
 * The gypsysCarnival.Game class application.
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

    /**
     * Start the application..
     *
     * @param primaryStage the primary stage
     * @throws IOException the io exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        List<Place> placeList = Place.generateAllPlaces();

        FXMLLoader startLoader = new FXMLLoader(getClass().getResource("view/start.fxml"));
        Pane startPane = startLoader.load();
        StartController startController = startLoader.getController();

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("view/main.fxml"));
        Pane mainPane = mainLoader.load();
        MainController mainController = mainLoader.getController();

        FXMLLoader helpLoader = new FXMLLoader(getClass().getResource("view/help.fxml"));
        Pane helpPane = helpLoader.load();
        HelpController helpController = helpLoader.getController();

        Scene scene = new Scene(startPane);

        SceneController sceneController = new SceneController(scene);
        sceneController.addPane("start", startPane);
        sceneController.addPane("main", mainPane);
        sceneController.addPane("help", helpPane);

        startController.setSceneController(sceneController);

        mainController.setSceneController(sceneController);
        mainController.setPlaceList(placeList);
        mainController.setPlayer(new Player("Benjapied Tablenuit", placeList.get(0)));
        mainController.setScene(scene);

        helpController.setSceneController(sceneController);

        sceneController.setCurrentPane("start");

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("gypsysCarnival/view/design/image/carnival.png"));
        primaryStage.setTitle("Gypsy's Carnival");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
