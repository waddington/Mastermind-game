package Mastermind.Sections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

// This is the class that is the modal dialog showing instructions on how to play
public class HowToPlayView {
    // The stage that everything gets added to
    private Stage stage;

    // My constructor initializes the stage then calls another method to create everything inside it
    public HowToPlayView() {
        this.stage = new Stage();
        createView();
    }

    // Method to create everything in this view
    private void createView() {
        // Creating a BorderPane for everything to go into
        BorderPane root = new BorderPane();

        // Creating a VBox for the title and separator
        // I add some formatting to this VBox
        VBox topArea = new VBox();
        topArea.setAlignment(Pos.TOP_CENTER);
        topArea.setPadding(new Insets(10));
        topArea.setSpacing(8);

        // A text element which is the title
        // And a separator because it looks good
        Text title = new Text("How to play");
        Separator separator = new Separator();
        // Adding these elements to the VBox
        topArea.getChildren().addAll(title, separator);
        // Adding the relevant VBox to the relevant area of the BorderPane
        root.setTop(topArea);

        // A new VBox for where the actual instructions will be
        // I format it slightly
        VBox instructionsArea = new VBox();
        instructionsArea.setPadding(new Insets(20));
        instructionsArea.setSpacing(15);
        instructionsArea.setAlignment(Pos.TOP_CENTER);

        // Text elements for each of the instruction steps
        Text step0 = new Text("0. Choose computer strategy from the \"Mode\" menu.");
        Text step1 = new Text("1. Click on the coloured blocks on the left to change their colour.");
        Text step2 = new Text("2. Click \"Confirm\" to confirm your selection.");
        Text step3 = new Text("3. Click \"Guess\" to get the computers first guess. Feedback will be shown to the right of the computers guess.");
        Text step4 = new Text("4. Click \"Next Guess\" until the game is over.");

        // Setting the text wrapping width of each text element
        step0.setWrappingWidth(400);
        step1.setWrappingWidth(400);
        step2.setWrappingWidth(400);
        step3.setWrappingWidth(400);
        step4.setWrappingWidth(400);

        // I create a button to close the modal dialog
        Button closeBtn = new Button("Close");
        // I use setOnAction() with a lambda expression to close the window when it is clicked
        closeBtn.setOnAction(event -> stage.close());

        // I add all the text elements and the button to the VBox and then I add the VBox to the center of the BorderPane
        instructionsArea.getChildren().addAll(step0, step1, step2, step3, step4, closeBtn);
        root.setCenter(instructionsArea);

        // I set the size of the window
        Scene scene = new Scene(root, 500, 300);
        // I format the stage so that you have to deal with this window before you can continue with the game
        // You have to close this window to continue
        stage.initModality(Modality.APPLICATION_MODAL);
        // Adding the scene and title to the stage
        stage.setScene(scene);
        stage.setTitle("How to play");
        // An attempt to center the new window on the screen, however this method doesn't actually perfectly center things it uses different values
        stage.centerOnScreen();
        // I don't want the window to be resizable
        stage.setResizable(false);
    }

    // Method to show the modal dialog, this just calls the show() method on the stage
    public void display() {
        this.stage.show();
    }
}
