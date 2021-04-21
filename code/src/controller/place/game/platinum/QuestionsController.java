package controller.place.game.platinum;

import controller.GameController;
import controller.UtilsController;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.platinum.Questions;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionsController implements Initializable {

    private Questions questions;

    private Polygon currentPolygon;
    private final Service<Void> waitService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                    Thread.sleep(1000);
                    return null;
                }
            };
        }
    };

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView platinumHubIcon;

    @FXML
    private Label questionLabel;

    @FXML
    private Polygon answer1Polygon;

    @FXML
    private Label answer1Label;

    @FXML
    private Polygon answer2Polygon;

    @FXML
    private Label answer2Label;

    @FXML
    private Polygon answer3Polygon;

    @FXML
    private Label answer3Label;

    @FXML
    private Polygon answer4Polygon;

    @FXML
    private Label answer4Label;

    @FXML
    private Label roundLabel;

    @FXML
    private Label jackpotLabel;

    @FXML
    private Polygon validatePolygon;

    @FXML
    void answer1MouseEntered() {
        UtilsController.rescaleNode(this.scene, this.answer1Polygon, 1.05);
    }

    @FXML
    void answer1MouseExited() {
        UtilsController.rescaleNode(this.scene, this.answer1Polygon, 1);
    }

    @FXML
    void answer1MouseClicked() {
        this.resetCurrentPolygonColor();
        this.answer1Polygon.setFill(Color.web("#fb9a11"));
        this.currentPolygon = answer1Polygon;
    }

    @FXML
    void answer2MouseEntered() {
        UtilsController.rescaleNode(this.scene, this.answer2Polygon, 1.05);
    }

    @FXML
    void answer2MouseExited() {
        UtilsController.rescaleNode(this.scene, this.answer2Polygon, 1);
    }

    @FXML
    void answer2MouseClicked() {
        this.resetCurrentPolygonColor();
        this.answer2Polygon.setFill(Color.web("#fb9a11"));
        this.currentPolygon = answer2Polygon;
    }

    @FXML
    void answer3MouseEntered() {
        UtilsController.rescaleNode(this.scene, this.answer3Polygon, 1.05);
    }

    @FXML
    void answer3MouseExited() {
        UtilsController.rescaleNode(this.scene, this.answer3Polygon, 1);
    }

    @FXML
    void answer3MouseClicked() {
        this.resetCurrentPolygonColor();
        this.answer3Polygon.setFill(Color.web("#fb9a11"));
        this.currentPolygon = answer3Polygon;
    }

    @FXML
    void answer4MouseEntered() {
        UtilsController.rescaleNode(this.scene, this.answer4Polygon, 1.05);
    }

    @FXML
    void answer4MouseExited() {
        UtilsController.rescaleNode(this.scene, this.answer4Polygon, 1);
    }

    @FXML
    void answer4MouseClicked() {
        this.resetCurrentPolygonColor();
        this.answer4Polygon.setFill(Color.web("#fb9a11"));
        this.currentPolygon = answer4Polygon;
    }

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (Node) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (Node) mouseEvent.getTarget(), 1);
    }

    @FXML
    void goPlatinum() {
        Interpreter.interpretCommand(this.player, "go platinum");
        this.gameController.changePlace();
    }

    @FXML
    void validateMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.validatePolygon, 1.05);
    }

    @FXML
    void validateMouseExited() {
        UtilsController.rescaleNode(this.scene, this.validatePolygon, 1);
    }

    @FXML
    void validateMouseClicked() {
        if (this.currentPolygon != null) {
            if (this.currentPolygon.equals(this.answer1Polygon)) {
                if (this.answer1Label.getText().equals(Questions.EASY)) {
                    this.winCurrentPolygonColor();

                    this.waitService.setOnSucceeded(event -> {
                        this.questions.chooseEasyQuestion();
                        this.generateNextQuestion();
                        this.currentPolygon = null;
                        this.waitService.reset();
                    });

                    this.waitService.start();
                } else
                    this.checkAnswer(1);
            } else if (this.currentPolygon.equals(this.answer2Polygon)) {
                if (this.answer2Label.getText().equals(Questions.DIFFICULT)) {
                    this.winCurrentPolygonColor();

                    this.waitService.setOnSucceeded(event -> {
                        this.questions.chooseDifficultQuestion();
                        this.generateNextQuestion();
                        this.currentPolygon = null;
                        this.waitService.reset();
                    });

                    this.waitService.start();
                } else
                    this.checkAnswer(2);
            }  else if (this.currentPolygon.equals(this.answer3Polygon)) {
                this.checkAnswer(3);
            } else {
                this.checkAnswer(4);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(this.platinumHubIcon, new Tooltip("Go to platinum hub"));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void reset() {
        this.resetInterface();

        this.questions = (Questions) this.player.getPlace();
        this.questions.start();

        this.roundLabel.textProperty().unbind();
        this.roundLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Round: " + this.questions.roundProperty().get() + "/" + Questions.NB_ROUND,
                        this.questions.roundProperty()
                )
        );

        this.jackpotLabel.textProperty().unbind();
        this.jackpotLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Jackpot: " + this.questions.jackpotProperty().get() + " coins",
                        this.questions.jackpotProperty()
                )
        );

        this.questionLabel.setText("Choose the difficulty");
        this.answer1Label.setText(Questions.EASY);
        this.answer2Label.setText(Questions.DIFFICULT);
    }

    private void checkAnswer(int answer) {
        if (this.questions.isCorrectAnswer(answer)) {
            this.winCurrentPolygonColor();

            this.waitService.setOnSucceeded(event -> {
                this.generateNextQuestion();
                this.currentPolygon = null;
                this.waitService.reset();
            });

            this.waitService.start();
        }
        else {
            this.loseCurrentPolygonColor();

            this.waitService.setOnSucceeded(event -> {
                this.questions.lose(this.player);
                this.replay(false);
                this.waitService.reset();
            });

            this.waitService.start();
        }
    }

    private void generateNextQuestion() {
        this.resetInterface();
        this.questions.nextQuestion();
        String[] question = this.questions.getShuffleQuestion();
        this.answer1Label.setText(question[0]);
        this.answer2Label.setText(question[1]);
        this.answer3Label.setText(question[2]);
        this.answer4Label.setText(question[3]);
        this.questionLabel.setText(question[4]);
    }

    private void resetInterface() {
        this.resetCurrentPolygonColor();
        this.questionLabel.setText("");
        this.answer1Label.setText("");
        this.answer2Label.setText("");
        this.answer3Label.setText("");
        this.answer4Label.setText("");
    }

    private void resetCurrentPolygonColor() {
        if (this.currentPolygon != null)
            this.currentPolygon.setFill(Color.web("#371a5b"));
    }

    private void winCurrentPolygonColor() {
        this.currentPolygon.setFill(Color.web("#36c13d"));
    }

    private void loseCurrentPolygonColor() {
        this.currentPolygon.setFill(Color.web("#ff3232"));
    }

    private void replay(boolean win) {
        if (UtilsController.getAlertFinish(win).showAndWait().orElse(null) == ButtonType.OK)
            this.reset();
        else
            this.goPlatinum();
    }
}
