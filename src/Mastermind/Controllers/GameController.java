package Mastermind.Controllers;

import Mastermind.FiveGuess;
import Mastermind.Main;
import Mastermind.Naive;
import Mastermind.Sections.*;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameController {

    // I have a reference to the stage as I bind the width of the menu to it's width property
    // I also have a reference to my Main class so that I can create a new game
    private Stage stage;
    private Main main;
    // Variables holding the various models/views for each component
    // Some views don't have their own models as their data is from several other models
    private WinLoseModalView winLoseModalView;
    private HowToPlayView howToPlayView;
    // Choosing Naive/Clever
    public String mode;
    private FiveGuess fiveGuess;
    private Naive naive;
    // The different sections
    private MenuAreaView menuAreaView;
    private UserCombinationView userCombinationView;
    public ComputerGuessesView computerGuessesView;
    private ComputerFeedbackView computerFeedbackView;
    private GameLogView gameLogView;
    private StatusBarView statusBarView;
    // This below view collates several other views into a new BorderPane layout
    private GamePlayAreaView gamePlayAreaView;
    // This is the main view that will contain all the other views
    private GameControllerView gameControllerView;

    // My constructor calls all the setter methods for the models, and for the views if it has no model
    public GameController(Stage stage, Main main) {
        this.main = main;
        this.stage = stage;
        this.mode = "clever";
        setWinLoseModalView();
        setHowToPlayView();
        setGuessingModel();
        setMenuAreaView();
        setUserCombinationView();
        setComputerGuessesView();
        setComputerFeedbackView();
        setGameLogView();
        setStatusBarView();
        setGamePlayAreaView();
        setGameControllerView();
    }

    // ###################################################

    // This method creates the modal window that will be displayed when the game is won/lost
    // I create the modal now so that I can quickly display it, with updated data, when needed
    private void setWinLoseModalView() {
        this.winLoseModalView = new WinLoseModalView(this);
    }
    // This method calls a method in the view to display the modal
    // It takes a boolean value indicating whether the game was won/lost so that a relevant message is displayed
    private void displayWinLoseModal(boolean won) {
        this.winLoseModalView.display(won);
    }

    // ##################################################

    // This method creates the modal with instructions on how to play
    private void setHowToPlayView() {
        this.howToPlayView = new HowToPlayView();
    }
    // Method to display the modal containing the instructions
    public void displayHowToPlayModal() {
        this.howToPlayView.display();
    }

    // ###################################################

    // This method instantiates the class that contains all the logic to guess the users combination
    private void setGuessingModel() {
        this.fiveGuess = new FiveGuess();
        this.naive = new Naive();
    }
    // Method to get the next guess/trigger game won/lost modals
    private void updateGuessingData() {
        // Checking if the game has already been won or if this would be after the tenth guess
        if (!this.computerGuessesView.isGameWon() && this.computerGuessesView.getGuessNumber()<10) {
            // Increase the guess number
            this.computerGuessesView.incrementGuess();

            // Display and log the guess
            displayGuess(this.computerGuessesView.getCurrentGuess(), this.computerGuessesView.getGuessNumber());
            this.gameLogView.updateLogText("Computer guessed combination "+this.computerGuessesView.getCurrentGuess());

            // Get and display the feedback
            this.computerFeedbackView.calculateFeedback(this.userCombinationView.userCombinationAsString(), this.computerGuessesView.getCurrentGuess());
            displayFeedback(this.computerFeedbackView.getCurrentFeedback(), this.computerGuessesView.getGuessNumber());

            // Update whether the game has been won
            if (this.computerFeedbackView.getCurrentFeedback()[0] == 4) {
                this.computerGuessesView.setGameWon(true);
            }

            // If the game hasn't been won
            if (!this.computerGuessesView.isGameWon()) {
                if (this.computerGuessesView.mode == "clever") {
                    // This is part of Knuths five guess algorithm
                    // Remove from a list any combinations that would not result in the same feedback if it were the guess
                    // This is explained in more detail in the FiveGuess class
                    this.fiveGuess.combinationElimination(this.computerGuessesView.getCurrentGuess(), this.computerFeedbackView.getCurrentFeedback(), true);

                    // Get and set the next guess
                    // An explanation for how this works is in the FiveGuess class
                    this.computerGuessesView.setCurrentGuess(this.fiveGuess.getNextGuess());
                } else {
                    this.computerGuessesView.setCurrentGuess(this.naive.getNextGuess(this.computerGuessesView.getGuessNumber()));
                }
            }
        }

        // If the game has been won, display the modal
        if (this.computerGuessesView.isGameWon()) {
            displayWinLoseModal(true);
        }

        // If the game has not been won and there have been more than ten guess, display the modal
        if (this.computerGuessesView.getGuessNumber() >=10 && !this.computerGuessesView.isGameWon()) {
            displayWinLoseModal(false);
        }
    }

    // ######################################################

    // This method instantiates the menu bar and sets everything up
    private void setMenuAreaView() {
        this.menuAreaView = new MenuAreaView(stage, this);
    }
    // This method retrieves the menu bar that was created so that it can be added to the main view
    private MenuBar getMenuAreaView() {
        return this.menuAreaView.getView();
    }
    // Method to disable mode selection
    public void disableModeSelection() {
        this.menuAreaView.disableModeSelection();
        this.setGuessMode(this.mode);
    }

    // #####################################################

    // Method to create the model containing the data on the users combination and also to create the view for that section
    private void setUserCombinationView() {
        this.userCombinationView = new UserCombinationView(this);
    }
    // Method to get the VBox containing the section containing everything relating to the user choosing their combination
    private VBox getUserCombinationView() {
        return userCombinationView.getView();
    }

    // ######################################################

    // Method to create the model containing the data on the computers guesses and also to create the view for that section
    private void setComputerGuessesView() {
        this.computerGuessesView = new ComputerGuessesView();
    }
    // This method is called to display the computers guess
    // This calls a method in the view to display the guess

    public void setGuessMode(String mode) {
        this.computerGuessesView.setGuessMode(mode);
    }
    private void displayGuess(String guess, int guessNumber) {
        this.computerGuessesView.displayGuess(guess, guessNumber);
    }
    // Method to get the VBox containing the section that will display the computers guesses
    private VBox getComputerGuessesView() {
        return this.computerGuessesView.getView();
    }

    // #####################################################

    // Method to create the model containing the feedback of the current guess and to also create view
    private void setComputerFeedbackView() {
        this.computerFeedbackView = new ComputerFeedbackView(fiveGuess);
    }
    // Method to display the feedback for a guess
    // This method calls another method in the view to display the feedback
    private void displayFeedback(int[] feedback, int guessNumber) {
        this.computerFeedbackView.displayFeedback(feedback, guessNumber);
    }
    // Method to get the VBox containing the entire section that shows the feedback for the guess
    private VBox getComputerFeedbackView() {
        return this.computerFeedbackView.getView();
    }

    // #####################################################

    // This method creates the view of the log area for the game
    // This does not have a model as all it's data comes from other models
    private void setGameLogView() {
        this.gameLogView = new GameLogView();
    }
    // Method to get the VBox containing the section with the log area in it
    private VBox getGameLogArea() {
        return this.gameLogView.getView();
    }

    // #########################################################

    // Method to create the Model and create the view passing data straight from the model
    private void setStatusBarView() {
        this.statusBarView = new StatusBarView(computerGuessesView, userCombinationView);
    }
    // Method that is called to updateData the information in the status bar
    private void updateStatusBar() {
        this.statusBarView.updateData();
        String statusBarUpdates = this.statusBarView.updateStatusBar();

        if (this.computerGuessesView.getGuessNumber() > 0) {
            this.gameLogView.updateLogText(statusBarUpdates);
        }
    }
    // Method to get the HBox containing the status bar
    private HBox getStatusAreaView() {
        updateStatusBar();
        return this.statusBarView.getView();
    }

    // #########################################################

    // Method to get the inner BorderPane for the actual game-play area views
    // This method takes other views and collates them into a BorderPane
    // This view contains: user combination view, computer guesses view, guess feedback view
    private void setGamePlayAreaView() {
        this.gamePlayAreaView = new GamePlayAreaView(getUserCombinationView(), getComputerGuessesView(), getComputerFeedbackView());
    }
    // Method to get the BorderPane containing several views
    private BorderPane getGamePlayAreaView() {
        return this.gamePlayAreaView.getView();
    }

    // ###############################################################

    // Method to create the BorderPane used to display the game
    // I pass to it the different sections that will go in it
    private void setGameControllerView() {
        this.gameControllerView = new GameControllerView(getMenuAreaView(), getStatusAreaView(), getGamePlayAreaView(), getGameLogArea());
    }
    // Method to return the BorderPane used to display the game
    public BorderPane getGameControllerView() {
        return this.gameControllerView.getView();
    }
    // Function to updateData game state
    // This will get called from the two game-play buttons
    public void update() {
        // First check if the user has chosen their combination yet, if they haven't this gets called when they confirm it
        if (!this.userCombinationView.isCombinationChosen()) {
            // Add some text to the log indicating their choice
            this.gameLogView.updateLogText("The computer will use strategy: "+this.mode);
            this.gameLogView.updateLogText("You have chosen combination "+this.userCombinationView.userCombinationAsString());
        } else {
            // If the user has already chosen their combination then we want to proceed with guessing on each updateData
            updateGuessingData();
        }
        // We always want to updateData the status bar
        updateStatusBar();
    }

    // ################################################################

    // This method is called when the user wants a new game
    // It calls a method in the Main class
    public void resetGame() {
        this.main.resetNewGame(stage);
    }
}
