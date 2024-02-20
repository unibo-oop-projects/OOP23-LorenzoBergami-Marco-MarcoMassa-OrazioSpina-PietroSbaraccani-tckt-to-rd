package it.unibo.view;

import java.util.Set;
import java.util.HashSet;
import it.unibo.controller.gamecontroller.api.MainController;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main stage of the view, extends {@link Stage}.
 */
public class MainStage extends Stage {

    private static final double SCENE_SCALE = 0.9;
    private static final double PANE_SCALE = 0.8;

    private PlayerInterface playerInterface;
    final private Screen screen = Screen.getPrimary();
    final private Rectangle2D bounds = screen.getVisualBounds();
    final private BorderPane root;
    final private Scene scene;
    final private Pane pane;
    private Set<Shape> shapeSet = new HashSet<>();
    private Set<Button> highlightedCitySet = new HashSet<>();

    /**
     * Constructor for the main stage.
     * 
     * @param controller the main controller of the game
     */
    public MainStage(final MainController controller) {

        this.root = new BorderPane();
        this.scene = new Scene(this.root, this.bounds.getWidth() * SCENE_SCALE, this.bounds.getHeight() * SCENE_SCALE);
        this.pane = new Pane();
        this.playerInterface = new PlayerInterface(controller,
                this.scene.getWidth() - this.pane.getMaxWidth(),
                this.scene.getHeight() - this.pane.getMaxWidth());

        final Image image = new Image("/img/Maps/europeMapLabeled.jpg");

        this.root.setCenter(this.pane);

        this.root.setRight(this.playerInterface);

        this.pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, false, false))));

        this.pane.setMaxWidth(this.scene.getWidth() * PANE_SCALE);
        this.pane.setMaxHeight(this.scene.getWidth() * PANE_SCALE * (image.getHeight() / image.getWidth()));
        this.pane.setMinWidth(this.scene.getWidth() * PANE_SCALE);
        this.pane.setMinHeight(this.scene.getWidth() * PANE_SCALE * (image.getHeight() / image.getWidth()));

        this.shapeSet = new ShapeSetter(controller)
                .getShapes(this.pane.getMaxWidth(), this.pane.getMaxHeight());
        for (final var shape : shapeSet) {
            pane.getChildren().add(shape);
        }

        this.setScene(this.scene);
        this.setResizable(false);
    }

    /**
     * A method to refresh the player interface.
     * 
     * @param controller the main controller of the game
     */
    public void refreshPlayerInterface(final MainController controller) {
        this.playerInterface = new PlayerInterface(controller,
                this.scene.getWidth() - this.pane.getMaxWidth(),
                this.scene.getHeight() - this.pane.getMaxWidth());
        this.root.setRight(this.playerInterface);
    }

    /**
     * A method to refresh the shapes of the carriages on the map.
     * 
     * @param controller the main controller of the game
     */
    public void refreshShapes(final MainController controller) {
        this.pane.getChildren().clear();
        this.shapeSet = new ShapeSetter(controller)
                .getShapes(this.pane.getMaxWidth(), this.pane.getMaxHeight());
        for (final var shape : this.shapeSet) {
            pane.getChildren().add(shape);
        }

        this.refreshHighLightedCities(controller);

        this.root.setCenter(this.pane);
    }

    private void refreshHighLightedCities(final MainController controller) {
        this.highlightedCitySet = new HighlightedCitySetter(controller)
                .getCities(this.pane.getMaxWidth(), this.pane.getMaxHeight());
        for (var city : this.highlightedCitySet) {
            pane.getChildren().add(city);
        }
    }
}
