package Mastermind;

import Mastermind.Controllers.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // The method required for JavaFX applications
    @Override
    public void start(Stage stage) throws Exception {
        // I call a different method to actually start everything off so that I can call it to create when a user wants a new game
        createGame(stage);
    }

    private void createGame(Stage stage) {
        // GameController is the class that controls everything
        GameController gameController = new GameController(stage, this);
        // Calling a method that returns a BorderPane and setting the size to 800x500
        Scene scene = new Scene(gameController.getGameControllerView(), 800, 550);
        // Setting the title of the window and adding the scene to the stage
        stage.setTitle("Mastermind");
        stage.setScene(scene);
        // I don't want users to be able to resize the window as the layout will not look as good
        stage.setResizable(false);
        // Displaying the main window
        stage.show();
    }

    // When a user wants a new game this method gets called which calls the createGame() method
    public void resetNewGame(Stage stage) {
        // Reusing the same stage
        createGame(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
