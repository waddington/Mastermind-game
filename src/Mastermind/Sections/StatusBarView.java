package Mastermind.Sections;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

// This is my class that creates the status bar at the bottom of the window
public class StatusBarView {
    // Stores references to these other models to retrieve data from them
    private ComputerGuessesView computerGuessesView;
    private UserCombinationView userCombinationView;

    // Everything is stored in a HBox and the bar consists of two labels and a region to separate them
    private HBox statusBarArea;
    private Label statusLabel;
    private Region spacer;
    private Label guessLabel;

    // I also store whether the game has been won, the combination has been set, and the guess number
    private boolean gameWon;
    private boolean combinationSet;
    private int guessNumber;

    // My constructor initializes the instance variables and calls a method to create the bar and then a method to updateData the information in the bar
    public StatusBarView(ComputerGuessesView computerGuessesView, UserCombinationView userCombinationView) {
        this.statusBarArea = new HBox();
        this.statusLabel = new Label();
        this.spacer = new Region();
        this.guessLabel = new Label();
        this.computerGuessesView = computerGuessesView;
        this.userCombinationView = userCombinationView;

        updateData();

        // Method to create the elements in the status bar
        createStatusBar();
        // Method to add information into the status bar
        updateStatusBar();
    }

    // Method to create the elements of the status bar
    private void createStatusBar() {
        // I format the HBox that is the status bar and add the labels and region to it
        this.statusBarArea.setPadding(new Insets(4,8,4,8));
        // I set the region to always grow as a means to ensure that the label on the right hand side stay on the far right of the status bar
        HBox.setHgrow(this.spacer, Priority.ALWAYS);
        this.statusBarArea.getChildren().addAll(this.statusLabel, this.spacer, this.guessLabel);
    }

    // Method to get an up-to-date version of data
    public void updateData() {
        // Retrieves up-to-date information from the relevant models
        this.gameWon = this.computerGuessesView.isGameWon();
        this.combinationSet = userCombinationView.isCombinationChosen();
        this.guessNumber = computerGuessesView.getGuessNumber();
    }

    // Method to add information into the status bar
    // Must pass up-to-date information to it
    // The model gets this information
    public String updateStatusBar() {

        // I updateData the instance variables
        updateData();

        // prefix of each area, these are here to be easily changed
        String preStatus = "Status: ";
        String preGuess = "Guess: ";

        // I set the initial text of the labels as a fallback if none of the later conditions are met
        String status = preStatus + "--";
        String guess = preGuess + "--";

        // Changing the status message depending on various conditions
        if (this.guessNumber == 0 && !this.combinationSet) {
            status = preStatus + "User to confirm combination.";
        }
        if (this.guessNumber == 0 && this.combinationSet) {
            status = preStatus + "Computer to make first guess.";
        }
        if (this.guessNumber > 0 && this.combinationSet) {
            status = preStatus + "Computer to make guess number " + (this.guessNumber+1) + ".";
            guess = preGuess + (this.guessNumber);
        }
        if (this.guessNumber >= 10 && !this.gameWon) {
            status = preStatus + "Game lost.";
            guess = preGuess + "--";
        }
        if (this.gameWon) {
            status = preStatus + "Game won.";
        }

        // Adding the text to the relevant labels in the status bar
        this.guessLabel.setText(guess);
        this.statusLabel.setText(status);

        // I return the status text as I will add this to the log area also
        return status;
    }

    // Method to get the HBox containing everything
    public HBox getView() {
        return this.statusBarArea;
    }
}
