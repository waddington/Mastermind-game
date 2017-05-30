package Mastermind.Sections;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// This is the class that contains/creates the view for the log area
public class GameLogView {
    // The entire section is in a VBox and the log area is a TextArea which is not editable
    private VBox gameLogArea;
    private TextArea logArea;

    // I initialize the instance variables and call a method to create everything
    public GameLogView() {
        this.gameLogArea = new VBox();
        this.logArea = new TextArea();

        createGameLogArea();
    }

    // This is the method that creates everything
    private void createGameLogArea() {
        // Format the containing VBox
        this.gameLogArea.setPadding(new Insets(8));
        this.gameLogArea.setSpacing(8);
        this.gameLogArea.setPrefWidth(200);
        // I set the TextArea to always grow
        VBox.setVgrow(this.logArea, Priority.ALWAYS);

        // Create section title and separator
        Text title = new Text("Log");
        Separator separator = new Separator();

        // Format the log area
        // I don't want the user to be able to edit the text, I also want the text to wrap nicely
        logArea.setFocusTraversable(false);
        logArea.setEditable(false);
        logArea.setWrapText(true);

        // Adding a listener to the log area so that it always scrolls down whenever text is appended
        // This just tries to scroll down as far as it can
        logArea.textProperty().addListener(ChangeListener -> logArea.setScrollTop(Double.MAX_VALUE));

        // I set the initial text in the log
        updateLogText("First guess can take a while. Please be patient.\r\n----------------");

        // Add everything to the containing VBox
        this.gameLogArea.getChildren().addAll(title, separator, logArea);
    }

    // This is the method that gets called to add text into the log area, it takes the text to add as a parameter and adds two carriage returns after it
    public void updateLogText(String toAdd) {
        this.logArea.appendText(toAdd+"\r\n\r\n");
    }

    // This is the method that returns the VBox containing the log area
    public VBox getView() {
        return this.gameLogArea;
    }
}
