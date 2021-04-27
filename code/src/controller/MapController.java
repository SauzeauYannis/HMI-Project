package controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import model.place.Place;
import model.place.exit.Exit;

import java.util.List;

/**
 * The Map controller.
 */
public class MapController {

    /*--------------------- Private members -------------------------*/

    @FXML
    private ImageView sparklingPadlockIcon;

    @FXML
    private ImageView findPadlockIcon;

    @FXML
    private ImageView qtePadlockIcon;

    @FXML
    private ImageView rockPadlockIcon;

    @FXML
    private ImageView hanoiPadlockIcon;

    @FXML
    private ImageView riddlePadlockIcon;

    @FXML
    private ImageView ticPadlockIcon;

    @FXML
    private ImageView hangmanPadlockIcon;

    @FXML
    private ImageView karaokePadlockIcon;

    @FXML
    private ImageView questionsPadlockIcon;

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets place list.
     *
     * @param placeList the place list
     */
    public void setPlaceList(List<Place> placeList) {
        List<Exit> carnivalExitList = placeList.get(0).getExitList();

        this.sparklingPadlockIcon.visibleProperty().bind(
                carnivalExitList.get(5).isLockProperty()
        );

        List<Exit> copperExitList = carnivalExitList.get(0).getPlace().getExitList();

        this.findPadlockIcon.visibleProperty().bind(
                copperExitList.get(1).isLockProperty()
        );
        this.qtePadlockIcon.visibleProperty().bind(
                copperExitList.get(2).isLockProperty()
        );
        this.rockPadlockIcon.visibleProperty().bind(
                copperExitList.get(3).isLockProperty()
        );

        List<Exit> goldExitList = carnivalExitList.get(1).getPlace().getExitList();

        this.hanoiPadlockIcon.visibleProperty().bind(
                goldExitList.get(1).isLockProperty()
        );
        this.riddlePadlockIcon.visibleProperty().bind(
                goldExitList.get(2).isLockProperty()
        );
        this.ticPadlockIcon.visibleProperty().bind(
                goldExitList.get(3).isLockProperty()
        );

        List<Exit> platinumExitList = carnivalExitList.get(2).getPlace().getExitList();

        this.hangmanPadlockIcon.visibleProperty().bind(
                platinumExitList.get(1).isLockProperty()
        );
        this.karaokePadlockIcon.visibleProperty().bind(
                platinumExitList.get(2).isLockProperty()
        );
        this.questionsPadlockIcon.visibleProperty().bind(
                platinumExitList.get(3).isLockProperty()
        );
    }
}
