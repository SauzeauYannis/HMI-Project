package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private AnchorPane root;

    @FXML
    private TabPane centerTabs;

    @FXML
    private Label todoGame;

    @FXML
    private Label todoMap;

    @FXML
    private VBox rightVBox;

    @FXML
    private ImageView helpIcon;

    @FXML
    private ImageView soundIcon;

    @FXML
    private ImageView quitIcon;

    @FXML
    private AnchorPane bottomPane;

    @FXML
    private Label todoText;

}
