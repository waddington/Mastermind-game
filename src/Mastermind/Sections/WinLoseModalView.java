package Mastermind.Sections;

import Mastermind.Controllers.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

// My class to create the popup modal dialog that is displayed when the game is won/lost
public class WinLoseModalView {
    // I store a reference to the GameController class as I need to call one of the methods in it if the user wants a new game
    private GameController gameController;
    private Stage modalStage;
    private Text message;

    // My constructor sets the instance variables and calls a method to create the view
    public WinLoseModalView(GameController gameController) {
        this.gameController = gameController;
        this.modalStage = new Stage();
        this.message = new Text();
        createModal();
    }

    // My method to create the view
    private void createModal() {
        // I want the user to make an action within this popup before they are able to interact with the main game window
        modalStage.initModality(Modality.APPLICATION_MODAL);
        // An attempt to center the window on screen
        modalStage.centerOnScreen();

        // This window uses BorderPane to layout everything
        BorderPane root = new BorderPane();
        // Everything is also in a VBox for easier spacing
        // I create and format the VBox
        VBox holder = new VBox();
        holder.setAlignment(Pos.CENTER);
        holder.setPadding(new Insets(25));
        holder.setSpacing(30);

        // Add a close button
        Button closeBtn = new Button("Close");
        // When the button is clicked I close the window
        closeBtn.setOnAction(event -> modalStage.close());

        // New game button
        Button newGame = new Button("New Game");
        // When the button is clicked I call a method in GameController to reset the game
        newGame.setOnAction(event -> {
            this.modalStage.close();
            this.gameController.resetGame();
        });

        // I add everything to the VBox
        holder.getChildren().addAll(message, closeBtn, newGame);
        // I add the VBox to the center of the BorderPane
        root.setCenter(holder);

        // I create a scene with the BorderPane
        Scene scene = new Scene(root, 400, 350);

        // I add the scene to the window and disable users from resizing the window
        modalStage.setScene(scene);
        modalStage.setResizable(false);
    }

    // My method to display this window
    // It takes a boolean value which indicates whether the game has been won or lost so that it can change the message and title of the window
    public void display(boolean win) {
        // I set the message and title of the window
        String title = "Game Over";
        String message = "The game has been lost.";

        // If the game has been won I change the message and title
        if (win) {
            title += " - The game has been won!";
            message = "The computer has won the game!";
        }

        // I set the message and title in the window
        this.message.setText(message);
        this.modalStage.setTitle(title);
        // I display the window
        this.modalStage.show();
    }
}
