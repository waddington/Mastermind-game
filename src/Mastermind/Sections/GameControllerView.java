package Mastermind.Sections;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// This is my class that collates all of the other sections into one BorderPane to be used in the scene
public class GameControllerView {
    private BorderPane root;
    private MenuBar menuBar;
    private HBox statusBar;
    private BorderPane gamePlayArea;
    private VBox gameLogArea;

    // My constructor takes all of the other sections as parameters
    public GameControllerView(MenuBar menuBar, HBox statusBar, BorderPane gamePlayArea, VBox gameLogArea) {
        this.root = new BorderPane();
        this.menuBar = menuBar;
        this.statusBar = statusBar;
        this.gamePlayArea = gamePlayArea;
        this.gameLogArea = gameLogArea;
    }

    // This method is called to get the BorderPane that contains everything
    // It adds the other sections into their place in the BorderPane
    public BorderPane getView() {
        this.root.setTop(menuBar);
        this.root.setBottom(statusBar);
        this.root.setCenter(gamePlayArea);
        this.root.setRight(gameLogArea);
        // Finally return the BorderPane
        return this.root;
    }
}
