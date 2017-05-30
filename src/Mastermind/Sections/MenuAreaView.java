package Mastermind.Sections;

import Mastermind.Controllers.GameController;
import javafx.scene.control.*;
import javafx.stage.Stage;

// My class that creates the menu bar
public class MenuAreaView {
    // I have access to the GameController class so that I can trigger a new game from the menu
    private GameController gameController;
    // I have access to the stage so that I can bind the menus width to the stages width
    private Stage stage;
    private MenuBar menuBar;
    // Mode selection items
    private RadioMenuItem modeNaive;
    private RadioMenuItem modeClever;

    // My constructor sets the instance variables and calls another method to create the menu bar
    public MenuAreaView(Stage stage, GameController gameController) {
        this.gameController = gameController;
        this.stage = stage;
        this.menuBar = new MenuBar();
        addMenuItems();
    }

    // Method to create the menu
    private void addMenuItems() {
        // Set the width of the menu bar to the width of the stage
        this.menuBar.prefWidthProperty().bind(this.stage.widthProperty());

        // Create the menus
        Menu menuFile = new Menu("File");
        Menu menuMode = new Menu("Mode");
        Menu menuHelp = new Menu("Help");

        // Create the menu items
        MenuItem menuItemNewGame = new MenuItem("New Game");
        MenuItem menuItemClose = new MenuItem("Close");
        ToggleGroup modeGroup = new ToggleGroup();
        modeNaive = new RadioMenuItem("Naive");
        modeNaive.setToggleGroup(modeGroup);
        modeClever = new RadioMenuItem("Clever");
        modeClever.setToggleGroup(modeGroup);
        modeClever.setSelected(true);
        MenuItem menuItemHow = new MenuItem("How to play");

        // Add actions to menu items
        // These menu items call other methods that perform their action
        menuItemNewGame.setOnAction(event -> this.gameController.resetGame());
        menuItemClose.setOnAction(event -> stage.close());
        modeNaive.setOnAction(event -> this.gameController.mode = "naive");
        modeClever.setOnAction(event -> this.gameController.mode = "clever");
        menuItemHow.setOnAction(event -> this.gameController.displayHowToPlayModal());

        // Add items to menus
        menuFile.getItems().addAll(menuItemNewGame, menuItemClose);
        menuMode.getItems().addAll(modeNaive, modeClever);
        menuHelp.getItems().addAll(menuItemHow);

        // Add menus to menu bar
        this.menuBar.getMenus().addAll(menuFile, menuMode, menuHelp);
    }

    // Method to return the menu bar
    public MenuBar getView() {
        return this.menuBar;
    }

    // Method to disable the mode selection
    public void disableModeSelection() {
        modeNaive.setDisable(true);
        modeClever.setDisable(true);
    }
}
