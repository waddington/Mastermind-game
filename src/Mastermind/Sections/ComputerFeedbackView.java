package Mastermind.Sections;

import Mastermind.FiveGuess;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

// This is the view that displays the feedback for each guess
public class ComputerFeedbackView {
    // The entire section is in a VBox and I store all the black/white pegs in a relevant list
    private FiveGuess fiveGuess;
    private int[] currentFeedback;

    private VBox feedbackArea;
    private List<Circle> blackBlocks;
    private List<Circle> whiteBlocks;

    // My constructor initializes the instance variables and calls a method to create/format the area
    public ComputerFeedbackView(FiveGuess fiveGuess) {
        this.fiveGuess = fiveGuess;
        this.feedbackArea = new VBox();
        this.blackBlocks = new ArrayList<>();
        this.whiteBlocks = new ArrayList<>();
        createFeedbackArea();
    }

    // Method to create a block
    // I abstracted this method out from where I needed it as it is long and made the code harder to read
    // This takes an string as a parameter which will be used for the shapes ID so that I could target it in CSS if I wanted to
    private Circle shorthandCircle(String id) {
        // Create and format the circle
        Circle circ = new Circle();
        circ.setRadius(5);
        circ.setStroke(Color.BLACK);
        circ.setStrokeWidth(1);
        circ.setStrokeType(StrokeType.INSIDE);
        circ.setSmooth(true);
        circ.setId(id);
        // The circle is set to visible as we want it hidden unless need
        circ.setVisible(false);
        // I return the circle
        return circ;
    }

    // This is a separate method as it is relatively long and could be argued to be it's own thing
    // The VBox that this method returns is the VBox that contains the actual feedback blocks, this doesn't include the title/separator
    private VBox getBlockHolder() {
        // Create and format the containing VBox
        VBox blockHolder = new VBox();
        blockHolder.setPadding(new Insets(8));
        blockHolder.setSpacing(6);
        blockHolder.setAlignment(Pos.TOP_CENTER);


        // Create the feedback blocks
        // Outer loop is for each of the ten rows
        // The inner loop is for each row in each row of guesses (black and white blocks are on separate rows)
        // Inner inner loop is for each block in each row
        for (int i=0; i<10; i++) {
            // Create and format the HBox that will hold the Feedback
            HBox groupRow = new HBox();
            groupRow.setAlignment(Pos.CENTER_LEFT);

            // Create and format the VBox to space the HBox's containing the black/white circles
            VBox groupRowV = new VBox();
            groupRowV.setAlignment(Pos.CENTER_LEFT);
            groupRowV.setPadding(new Insets(6,0,7,0));
            groupRowV.setSpacing(4);

            // Inner loop for the separate rows for black/white feedback
            for (int j=0; j<2; j++) {
                // Create and format the HBox that will hold each colours' pegs
                HBox feedbackRow = new HBox();
                feedbackRow.setSpacing(4);
                feedbackRow.setAlignment(Pos.TOP_LEFT);

                // Inner inner loop for each feedback block
                for (int k=0; k<4; k++) {
                    // I create an ID out of which row/column the block is in
                    String id = i+":"+j+":"+k;
                    Circle block = shorthandCircle(id);
                    if (j == 0) {
                        // Blocks on the first row are black
                        block.setFill(Color.BLACK);
                        // Add the block to the relevant list so that I can easily reference it later
                        blackBlocks.add(block);
                    }
                    if (j == 1) {
                        // Blocks on the second row are white
                        block.setFill(Color.WHITE);
                        // Add the block to the relevant list so that I can easily reference it later
                        whiteBlocks.add(block);
                    }
                    // Add the block to the row
                    feedbackRow.getChildren().add(block);
                }

                // Add each row to the containing VBox
                groupRowV.getChildren().add(feedbackRow);
            }

            // Add the VBox to its containing HBox
            groupRow.getChildren().add(groupRowV);
            // Add the HBox to it's main containing VBox
            blockHolder.getChildren().add(groupRow);
        }
        // Return the VBox containing the blocks for the feedback
        return blockHolder;
    }

    // This is the method that creates everything
    private void createFeedbackArea() {
        // Format the containing VBox
        feedbackArea.setPadding(new Insets(8));
        feedbackArea.setSpacing(8);
        feedbackArea.setAlignment(Pos.CENTER);
        feedbackArea.setPrefWidth(90);

        // Add section title and separator
        Text title = new Text("Feedback");
        Separator separator = new Separator();

        // Get the VBox that contains all the feedback circles
        VBox blockHolder = getBlockHolder();
        // Setting the VBox that contains the blocks to have priority over the other Nodes so that it grows to full height
        VBox.setVgrow(blockHolder, Priority.ALWAYS);


        // Add everything to the containing VBox
        this.feedbackArea.getChildren().addAll(title, separator, blockHolder);
    }

    // This calculates the feedback between two strings using a method in the FiveGuess class
    public void calculateFeedback(String a, String b) {
        this.currentFeedback = fiveGuess.getFeedback(a, b);
    }

    // This returns the currently stored feedback
    public int[] getCurrentFeedback() {
        return this.currentFeedback;
    }

    // This is the method that gets called to display the feedback of a guess
    // It takes the feedback and the guess number as parameters
    public void displayFeedback(int[] feedback, int guessNumber) {
        // The start index is calculated from the guess number as is the first block on the row for the relevant guess
        int startIndex = (guessNumber - 1) * 4;

        // Display black blocks
        // Loop through how many blocks to display
        for (int i=startIndex; i<startIndex+feedback[0]; i++) {
            // Get that block from the list of blocks and set it to visible
            blackBlocks.get(i).setVisible(true);
        }
        // Display white blocks
        // Loop through how many blocks to display
        for (int i=startIndex; i<startIndex+feedback[1]; i++) {
            // Get that block from the list of blocks and set it to visible
            whiteBlocks.get(i).setVisible(true);
        }
    }

    // This method returns the VBox containing this view
    public VBox getView() {
        return this.feedbackArea;
    }
}
