package Mastermind.Sections;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// This is the class that collates the user combination selection area, the computer guesses area, and the feedback area into a new BorderPane for an easier better layout
public class GamePlayAreaView {
    // The new BorderPane the other sections will go into, and the other sections
    private BorderPane root;
    private VBox userCombinationView;
    private VBox computerGuessesView;
    private VBox feedbackView;

    // My constructor stores the relevant sections and initializes the BorderPane
    public GamePlayAreaView(VBox userCombinationView, VBox computerGuessesView, VBox feedbackView) {
        this.root = new BorderPane();
        this.userCombinationView = userCombinationView;
        this.computerGuessesView = computerGuessesView;
        this.feedbackView = feedbackView;
    }

    // Method to return the BorderPane containing the other elements
    // This also adds the other elements to the BorderPane
    public BorderPane getView() {
        this.root.setLeft(userCombinationView);
        this.root.setCenter(computerGuessesView);
        this.root.setRight(feedbackView);
        // Returning the BorderPane
        return this.root;
    }
}
