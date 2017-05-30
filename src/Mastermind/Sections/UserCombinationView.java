package Mastermind.Sections;

import Mastermind.Controllers.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

// This is my class that creates the view for the section where the user will choose their combination
public class UserCombinationView {
    // I store a reference to the game controller as I need to call some of the methods in it on the button clicks
    private GameController gameController;
    private boolean combinationChosen;
    private int[] userCombination;
    // Everything is contained in a VBox
    private VBox userCombinationArea;
    private Color[] possibleColours;

    // My constructor initializes the instance variables
    public UserCombinationView(GameController gameController) {
        this.gameController = gameController;
        this.combinationChosen = false;
        this.userCombinationArea = new VBox();
        // I store the possible colours of the blocks in an array
        this.possibleColours = new Color[]{Color.HOTPINK, Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.BLUE};
        // The users combination will be stored an as array of numbers where each number represents a colour
        this.userCombination = new int[4];
        // I call a method which will create this view
        createCombinationArea();
    }

    // Function to return a colour based on an int input
    private Color numberToColour(int i) {
        return possibleColours[i%6];
    }

    // Method to create a block
    // I abstracted this method out from where I needed it as it is long and made the code harder to read
    private Rectangle shorthandRectangle(String id) {
        Rectangle rect = new Rectangle();
        rect.setArcWidth(5);
        rect.setArcHeight(5);
        rect.setSmooth(true);
        rect.setStrokeWidth(1);
        rect.setStroke(Color.BLACK);
        rect.setWidth(45);
        rect.setHeight(45);
        rect.setCursor(Cursor.CLOSED_HAND);
        rect.setFill(numberToColour(0));
        rect.setId(id);

        return rect;
    }

    // Method to create the area that the actual blocks are stored in
    private HBox createBlocksArea() {
        // All of the blocks are in a HBox, I create and format the HBox
        HBox combinationHolder = new HBox();
        combinationHolder.setSpacing(4);
        combinationHolder.setAlignment(Pos.CENTER);

        // A simple loop to create four blocks
        for (int i=0; i<4; i++) {
            // I use my method to create a rectangle
            Rectangle block = shorthandRectangle(Integer.toString(i));
            // Add functionality to the blocks
            block.setOnMouseClicked(mouseEvent -> {
                // Get the block that was clicked
                Rectangle src = (Rectangle) mouseEvent.getSource();
                // Get it's ID which relates to it's position in an array storing the users combination
                int num = Integer.parseInt(src.getId());
                // Update the correct index in the array storing the users combination to its current value plus one
                userCombination[num] = (userCombination[num]+1) % 6;
                // Update the colour of the block
                // I have a function that converts a number to one of the possible colours
                src.setFill(numberToColour(userCombination[num]));
            });
            // Add the blocks to their containing HBox
            combinationHolder.getChildren().add(block);
        }
        // Return the HBox containing the blocks
        return combinationHolder;
    }

    // My method to add the buttons to the view
    private Button[] createButtons() {
        // Creating the two buttons
        Button submitCombination = new Button("Confirm");
        Button getNextGuess = new Button("Guess");

        // Formatting the buttons
        submitCombination.setCursor(Cursor.CLOSED_HAND);
        submitCombination.setId("confirmButton");
        // Disable the "guess" button until their combination is confirmed
        getNextGuess.setDisable(true);
        // Return both buttons in an array of buttons
        return new Button[]{submitCombination, getNextGuess};
    }

    // My method to add the required functionality to the buttons
    private Button[] addButtonFunctionality(Button[] buttons, HBox combinationHolder) {
        // Adding functionality to the buttons
        // This is for the button that confirms the users combination
        // When the button is clicked...
        buttons[0].setOnAction(actionEvent -> {
            // Disable the blocks from changing colours
            combinationHolder.setDisable(true);
            // Change the button text to something more useful and disable it
            buttons[0].setText("Confirmed");
            buttons[0].setDisable(true);
            // Enable the button to get computer guesses
            buttons[1].setDisable(false);
            // Disable mode selection so that it can't be changed halfway through
            this.gameController.disableModeSelection();
            // Update the game state
            this.gameController.update();
            // Store that the user has chosen their combination
            combinationChosen = true;
        });
        // This is the button to get the next guess
        // When the button is clicked...
        buttons[1].setOnAction(actionEvent -> {
            // Update the game state
            this.gameController.update();

            // If the number of guesses is now above 10, disable the button
            if (this.gameController.computerGuessesView.getGuessNumber() >= 10 || this.gameController.computerGuessesView.isGameWon()) {
                buttons[1].setDisable(true);
            }
        });
        // Return the array of buttons
        return buttons;
    }

    // Method to create the area for the user to choose their combination
    private void createCombinationArea() {
        // Formatting the containing VBox
        this.userCombinationArea.setPadding(new Insets(8,8,8,16));
        this.userCombinationArea.setSpacing(12);
        this.userCombinationArea.setAlignment(Pos.CENTER);
        this.userCombinationArea.setPrefWidth(270);

        // Area title with separator underneath
        Text title = new Text("Your Combination");
        Separator separator = new Separator();

        // The actual blocks are in another HBox for easier spacing
        // I call my method to create these blocks
        HBox combinationHolder = createBlocksArea();

        // Call a method to create the buttons and then add their functionality
        Button[] gameButtons = createButtons();
        gameButtons = addButtonFunctionality(gameButtons, combinationHolder);

        // Add all the elements to the main VBox
        this.userCombinationArea.getChildren().addAll(title, separator, combinationHolder, gameButtons[0], gameButtons[1]);
    }

    // This is my method to convert the users combination from and int array to a string
    public String userCombinationAsString() {
        // I use a string builder
        StringBuilder theCombo = new StringBuilder();
        for (int i=0; i<4; i++) {
            // I loop through the array of the users combination and add each int to the string builder
            theCombo.append(userCombination[i]);
        }
        // I return the string builder as a string
        return theCombo.toString();
    }

    // Function to check if the combination is chosen
    public boolean isCombinationChosen() {
        return this.combinationChosen;
    }

    // Method to return the VBox that holds everything
    public VBox getView() {
        return this.userCombinationArea;
    }
}
