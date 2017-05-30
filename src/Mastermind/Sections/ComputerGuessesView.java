package Mastermind.Sections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

// This is the class that creates the entire section pertaining to the computers guesses
public class ComputerGuessesView {
    public String mode;
    private int guessNumber;
    private boolean gameWon;
    private String currentGuess;

    // Everything in this section is in a VBox
    private VBox computerGuessArea;
    private Color[] possibleColours;
    // I store the blocks in a list so that they can easily be referenced later
    private List<Rectangle> blocks;

    // My constructor initializes the VBox and other instance variables
    public ComputerGuessesView() {
        this.guessNumber = 0;
        this.gameWon = false;
        this.currentGuess = "0011";
        this.computerGuessArea = new VBox();
        // TODO: Create "ColourConverter" class that can be used to get a colour from a number and vice-versa
        this.possibleColours = new Color[]{Color.HOTPINK, Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.BLUE};
        this.blocks = new ArrayList<>();
        // I call a method to create the entire section
        createGuessArea();
    }

    // Function to return a colour based on an int input
    private Color numberToColour(int i) {
        // I use mod here in case the number provided is larger than the size of the list
        return possibleColours[i%6];
    }

    // Method to create a block
    // I abstracted this method out from where I needed it as it is long and made the code harder to read
    // This method takes an string which I will assign as an ID to the rectangle
    private Rectangle shorthandRectangle(String id) {
        // I create and format the rectangle
        Rectangle rect = new Rectangle();
        rect.setArcWidth(5);
        rect.setArcHeight(5);
        rect.setSmooth(true);
        rect.setStrokeWidth(1);
        rect.setStroke(Color.BLACK);
        rect.setWidth(36);
        rect.setHeight(36);
        rect.setFill(Color.DODGERBLUE);
        rect.setId(id);
        // I return the rectangle
        return rect;
    }

    // Method to create the area with the blocks in that will show the computers guess
    private VBox getBlockHolder() {
        // Create and format containing VBox
        VBox blockHolder = new VBox();
        blockHolder.setAlignment(Pos.TOP_CENTER);
        blockHolder.setPadding(new Insets(8));
        blockHolder.setSpacing(6);

        // Create the blocks
        // Outer loop is for each of the ten rows
        // The inner loop is for the blocks in each row
        for (int i=0; i<10; i++) {
            HBox guessRow = new HBox();
            guessRow.setSpacing(4);
            guessRow.setAlignment(Pos.CENTER);

            // Inner loop for each block
            for (int j=0; j<4; j++) {
                // I construct an ID out of the row/column that this rectangle is in
                String id = i+":"+j;
                Rectangle block = shorthandRectangle(id);
                // The block is not visible as we don't want it shown until the guess that requires it
                block.setVisible(false);
                // I add the block to the list for easy referencing later and then add it to the row
                blocks.add(block);
                guessRow.getChildren().add(block);
            }
            // Add row to containing VBox
            blockHolder.getChildren().add(guessRow);
        }
        // Return the VBox containing all the blocks
        return blockHolder;
    }

    // Method to create the entire section
    private void createGuessArea() {
        // Format the containing VBox
        computerGuessArea.setPadding(new Insets(8));
        computerGuessArea.setSpacing(8);
        computerGuessArea.setAlignment(Pos.CENTER);
        computerGuessArea.setPrefWidth(200);

        // Add section title and separator
        Text title = new Text("Computer Guesses");
        Separator separator = new Separator();

        // Create the holder of the blocks containing the computers guess
        VBox blockHolder = getBlockHolder();
        // I set the area containing the blocks to have always grow within the VBox
        VBox.setVgrow(blockHolder, Priority.ALWAYS);


        // Add everything to the containing VBox
        this.computerGuessArea.getChildren().addAll(title, separator, blockHolder);
    }

    // This returns the number of the guess that the computer is currently on
    public int getGuessNumber() {
        return this.guessNumber;
    }

    // This updates the state of whether the game has been won or not
    public void setGameWon(Boolean won) {
        this.gameWon = won;
    }

    // This returns a boolean indicating whether the game has been won or not
    public boolean isGameWon() {
        return this.gameWon;
    }

    // This is used to set the current guess
    public void setCurrentGuess(String g) {
        this.currentGuess = g;
    }

    // This returns a string containing the current guess
    public String getCurrentGuess() {
        return this.currentGuess;
    }

    // This increments the guess number
    public void incrementGuess() {
        this.guessNumber++;
    }

    // This is my method to display the computers guess
    // It takes the guess and guess number as parameters
    public void displayGuess(String guess, int guessNumber) {
        // ItThis calculates the index of the first block in the row for the relevant guess
        int startIndex = (guessNumber - 1) * 4;
        // This is a counter to keep track of which character in the guess we need
        int j = 0;
        for (int i=startIndex; i<startIndex+4; i++) {
            // I get the relevant block from the list
            Rectangle block = blocks.get(i);
            // I set the blocks fill to the colour as defined in the guess
            // I use charAt to get the number for the colour and then pass this to my method that returns a colour based on a number
            block.setFill(numberToColour(guess.charAt(j++)));
            // Finally I set the block to be visible so that we can see it
            block.setVisible(true);
        }
    }

    // Method to get the entire section
    public VBox getView() {
        return this.computerGuessArea;
    }

    public void setGuessMode(String mode) {
        this.mode = mode;
    }
}
