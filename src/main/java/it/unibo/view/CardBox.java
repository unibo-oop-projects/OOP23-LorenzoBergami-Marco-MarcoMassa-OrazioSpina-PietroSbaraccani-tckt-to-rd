package it.unibo.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.controller.gamecontroller.api.MainController;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.awt.Color;
import javafx.scene.text.Text;

/**
 * The class that represents the box of the cards of the player, extends
 * {@link VBox}.
 * 
 * It contains all the cards of the player.
 */
public class CardBox extends VBox {

    private static final double FITHEIGHT = 50;
    private static final double FITWIDTH = 50 * 1.56;

    private final Map<Color, String> colorImageMap = new LinkedHashMap<>();

    private final VBox verticalBox = new VBox();

    /**
     * Creates the box of player's card.
     * 
     * @param controller the mainController of the game.
     */
    public CardBox(final MainController controller) {

        colorImageMap.put(Color.BLACK, "img/Cards/BlackCard.png");
        colorImageMap.put(Color.BLUE, "img/Cards/BlueCard.png");
        colorImageMap.put(Color.GREEN, "img/Cards/GreenCard.png");
        colorImageMap.put(Color.RED, "img/Cards/RedCard.png");
        colorImageMap.put(Color.WHITE, "img/Cards/WhiteCard.png");
        colorImageMap.put(Color.YELLOW, "img/Cards/YellowCard.png");
        colorImageMap.put(Color.ORANGE, "img/Cards/OrangeCard.png");
        colorImageMap.put(Color.MAGENTA, "img/Cards/PurpleCard.png");
        colorImageMap.put(Color.DARK_GRAY, "img/Cards/JollyCard.png");

        final var temp = new ArrayList<>(this.colorImageMap.entrySet());

        for (int i = 0; i < temp.size(); i += 2) {
            final HBox cardBox = new HBox();
            final ImageView card1 = new ImageView(
                    new Image(ClassLoader.getSystemResourceAsStream(temp.get(i).getValue())));

            final VBox cardBox1 = new VBox(card1,
                    new Text(controller.getTurnController().getCurrentPlayer().getTrainCards()
                            .get(temp.get(i).getKey())
                            .toString()));

            card1.setFitHeight(FITHEIGHT);
            card1.setFitWidth(FITWIDTH);
            cardBox.getChildren().add(cardBox1);

            if (i != temp.size() - 1) {
                final ImageView card2 = new ImageView(
                        new Image(ClassLoader.getSystemResourceAsStream(temp.get(i + 1).getValue())));
                final VBox cardBox2 = new VBox(card2,
                        new Text(controller.getTurnController().getCurrentPlayer().getTrainCards()
                                .get(temp.get(i + 1).getKey())
                                .toString()));
                card2.setFitHeight(FITHEIGHT);
                card2.setFitWidth(FITWIDTH);
                cardBox.getChildren().add(cardBox2);
            }
            cardBox.setSpacing(1);
            cardBox.setPadding(new Insets(1));
            verticalBox.getChildren().add(cardBox);
        }
    }

    /**
     * Adds the content to the box.
     */
    public void initialize() {
        this.getChildren().add(this.verticalBox);
    }
}
