import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vue.Start;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Start start = new Start();

        Scene scene = new Scene(start);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Gypsy's Carnaval");
        primaryStage.show();
    }
}
