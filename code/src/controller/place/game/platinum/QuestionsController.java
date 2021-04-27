package controller.place.game.platinum;

import controller.PlaceController;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.platinum.Questions;
import view.CustomAlert;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Questions game controller.
 */
public class QuestionsController implements Initializable {

    /*--------------------- Private members -------------------------*/

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
    private Polygon currentPolygon;

    private Questions questions;
    private PlaceController placeController;
    private Player player;

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

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.answer1Polygon.setCursor(Cursor.HAND);
        this.answer2Polygon.setCursor(Cursor.HAND);
        this.answer3Polygon.setCursor(Cursor.HAND);
        this.answer4Polygon.setCursor(Cursor.HAND);
        this.validatePolygon.setCursor(Cursor.HAND);
    }

    /**
     * Answer mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void answerMouseClicked(MouseEvent mouseEvent) {
        this.resetCurrentPolygonColor();
        if (mouseEvent.getTarget().equals(this.answer1Label) || mouseEvent.getTarget().equals(this.answer1Polygon))
            this.selectPolygon(this.answer1Polygon);
        else if (mouseEvent.getTarget().equals(this.answer2Label) || mouseEvent.getTarget().equals(this.answer2Polygon))
            this.selectPolygon(this.answer2Polygon);
        else if (mouseEvent.getTarget().equals(this.answer3Label) || mouseEvent.getTarget().equals(this.answer3Polygon))
            this.selectPolygon(this.answer3Polygon);
        else
            this.selectPolygon(this.answer4Polygon);
    }

    /**
     * Validate mouse clicked.
     */
    @FXML
    public void validateMouseClicked() {
        if (this.currentPolygon != null) {
            if (this.currentPolygon.equals(this.answer1Polygon)) {
                if (this.answer1Label.getText().equals(Questions.EASY)) {
                    this.winCurrentPolygonColor();
                    this.chooseEasy();
                } else if (this.answer1Label.getText().equals(Questions.YES)) {
                    this.winCurrentPolygonColor();
                    this.winGame();
                } else
                    this.checkAnswer(1);
            } else if (this.currentPolygon.equals(this.answer2Polygon)) {
                if (this.answer2Label.getText().equals(Questions.DIFFICULT)) {
                    this.winCurrentPolygonColor();
                    this.chooseDifficult();
                } else if (this.answer2Label.getText().equals(Questions.NO)) {
                    this.winCurrentPolygonColor();
                    this.nextRound();
                } else
                    this.checkAnswer(2);
            } else if (this.currentPolygon.equals(this.answer3Polygon)) {
                this.checkAnswer(3);
            } else {
                this.checkAnswer(4);
            }
            this.waitService.start();
        }
    }

    /**
     * Polygon mouse entered.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void polygonMouseEntered(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget().equals(this.answer1Label) || mouseEvent.getTarget().equals(this.answer1Polygon)) {
            this.answer1Polygon.setScaleX(1.05);
            this.answer1Polygon.setScaleY(1.05);
        } else if (mouseEvent.getTarget().equals(this.answer2Label) || mouseEvent.getTarget().equals(this.answer2Polygon)) {
            this.answer2Polygon.setScaleX(1.05);
            this.answer2Polygon.setScaleY(1.05);
        } else if (mouseEvent.getTarget().equals(this.answer3Label) || mouseEvent.getTarget().equals(this.answer3Polygon)) {
            this.answer3Polygon.setScaleX(1.05);
            this.answer3Polygon.setScaleY(1.05);
        } else if (mouseEvent.getTarget().equals(this.answer4Label) || mouseEvent.getTarget().equals(this.answer4Polygon)) {
            this.answer4Polygon.setScaleX(1.05);
            this.answer4Polygon.setScaleY(1.05);
        } else {
            this.validatePolygon.setScaleX(1.05);
            this.validatePolygon.setScaleY(1.05);
        }
    }

    /**
     * Polygon mouse exited.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void polygonMouseExited(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget().equals(this.answer1Label) || mouseEvent.getTarget().equals(this.answer1Polygon)) {
            this.answer1Polygon.setScaleX(1);
            this.answer1Polygon.setScaleY(1);
        } else if (mouseEvent.getTarget().equals(this.answer2Label) || mouseEvent.getTarget().equals(this.answer2Polygon)) {
            this.answer2Polygon.setScaleX(1);
            this.answer2Polygon.setScaleY(1);
        } else if (mouseEvent.getTarget().equals(this.answer3Label) || mouseEvent.getTarget().equals(this.answer3Polygon)) {
            this.answer3Polygon.setScaleX(1);
            this.answer3Polygon.setScaleY(1);
        } else if (mouseEvent.getTarget().equals(this.answer4Label) || mouseEvent.getTarget().equals(this.answer4Polygon)) {
            this.answer4Polygon.setScaleX(1);
            this.answer4Polygon.setScaleY(1);
        } else {
            this.validatePolygon.setScaleX(1);
            this.validatePolygon.setScaleY(1);
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        this.resetInterface();

        this.questions.start();

        this.questionLabel.setText("Choose the difficulty");
        this.answer1Label.setText(Questions.EASY);
        this.answer2Label.setText(Questions.DIFFICULT);
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets questions game.
     *
     * @param questions the questions
     */
    public void setQuestions(Questions questions) {
        this.questions = questions;

        this.roundLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Round: " + this.questions.roundProperty().get() + "/" + Questions.NB_ROUND,
                        this.questions.roundProperty()
                )
        );

        this.jackpotLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Jackpot: " + this.questions.jackpotProperty().get() + " coins",
                        this.questions.jackpotProperty()
                )
        );
    }

    /**
     * Sets place controller.
     *
     * @param placeController the place controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /*----------------------- Private methods --------------------------------*/

    /**
     * Check answer.
     *
     * @param answer the number of the answer
     * @see #validateMouseClicked()
     */
    private void checkAnswer(int answer) {
        if (this.questions.isCorrectAnswer(answer)) {
            this.winCurrentPolygonColor();
            if (this.questions.isTheLastRound())
                this.winGame();
            else
                this.askForStop();
        } else {
            this.loseCurrentPolygonColor();
            this.loseGame();
        }
    }

    /**
     * Choose easy level.
     *
     * @see #validateMouseClicked()
     */
    private void chooseEasy() {
        this.waitService.setOnSucceeded(event -> {
            this.questions.chooseEasyQuestion();
            this.generateNextQuestion();
            this.currentPolygon = null;
            this.waitService.reset();
        });
    }

    /**
     * Choose difficult level.
     *
     * @see #validateMouseClicked()
     */
    private void chooseDifficult() {
        this.waitService.setOnSucceeded(event -> {
            this.questions.chooseDifficultQuestion();
            this.generateNextQuestion();
            this.currentPolygon = null;
            this.waitService.reset();
        });
    }

    /**
     * Generate next question.
     */
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

    /**
     * Next round.
     *
     * @see #validateMouseClicked()
     */
    private void nextRound() {
        this.waitService.setOnSucceeded(event -> {
            this.generateNextQuestion();
            this.currentPolygon = null;
            this.waitService.reset();
        });
    }

    /**
     * Ask for stop.
     *
     * @see #checkAnswer(int)
     */
    private void askForStop() {
        this.waitService.setOnSucceeded(event -> {
            this.resetInterface();
            this.questions.npcAskForNextTurn();

            this.questionLabel.setText("Do you want to stop and cash your jackpot?");
            this.answer1Label.setText(Questions.YES);
            this.answer2Label.setText(Questions.NO);

            this.currentPolygon = null;
            this.waitService.reset();
        });
    }

    /**
     * Win game.
     *
     * @see #validateMouseClicked()
     * @see #checkAnswer(int)
     */
    private void winGame() {
        this.waitService.setOnSucceeded(event -> {
            this.questions.winJackpot(this.player);
            this.questions.endGame();
            this.replay(true);
            this.waitService.reset();
        });
    }

    /**
     * Lose game.
     *
     * @see #checkAnswer(int)
     */
    private void loseGame() {
        this.waitService.setOnSucceeded(event -> {
            this.questions.lose(this.player);
            this.questions.endGame();
            this.replay(false);
            this.waitService.reset();
        });
    }

    /**
     * Reset interface.
     *
     * @see #generateNextQuestion()
     * @see #reset()
     * @see #askForStop()
     */
    private void resetInterface() {
        this.resetCurrentPolygonColor();
        this.questionLabel.setText("");
        this.answer1Label.setText("");
        this.answer2Label.setText("");
        this.answer3Label.setText("");
        this.answer4Label.setText("");
    }

    /**
     * Selected current polygon color.
     *
     * @param polygon the selected polygon
     * @see #answerMouseClicked(MouseEvent)
     */
    private void selectPolygon(Polygon polygon) {
        polygon.setFill(Color.web("#fb9a11"));
        this.currentPolygon = polygon;
    }

    /**
     * Reset current polygon color.
     *
     * @see #answerMouseClicked(MouseEvent)
     * @see #resetInterface()
     */
    private void resetCurrentPolygonColor() {
        if (this.currentPolygon != null)
            this.currentPolygon.setFill(Color.web("#371a5b"));
    }

    /**
     * Win current polygon color.
     *
     * @see #validateMouseClicked()
     * @see #checkAnswer(int)
     */
    private void winCurrentPolygonColor() {
        this.currentPolygon.setFill(Color.web("#36c13d"));
    }

    /**
     * Lose current polygon color.
     *
     * @see #checkAnswer(int)
     */
    private void loseCurrentPolygonColor() {
        this.currentPolygon.setFill(Color.web("#ff3232"));
    }

    /**
     * Ask to replay the game.
     *
     * @param win true if the player won the game.
     * @see #winGame()
     * @see #loseGame()
     */
    private void replay(boolean win) {
        CustomAlert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                this.player.getPlace().getName() + " - Finished",
                win ? "You win!" : "You lose!",
                "Do you want to replay?",
                "view/design/image/questions.gif",
                "replay",
                "return to platinum hub"
        );

        if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
            this.reset();
        else {
            Interpreter.interpretCommand(this.player, "go platinum");
            this.placeController.changePlace();
        }
    }
}
