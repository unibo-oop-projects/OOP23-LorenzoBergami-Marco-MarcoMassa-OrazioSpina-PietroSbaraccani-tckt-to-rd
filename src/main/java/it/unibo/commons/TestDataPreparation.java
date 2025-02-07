package it.unibo.commons;

import java.util.List;

import it.unibo.model.city.api.City;
import it.unibo.model.city.impl.CityImpl;
import it.unibo.model.gameprep.impl.GamePrep;
import it.unibo.model.route.api.Route;
import it.unibo.model.route.impl.RouteImpl;

import java.awt.Color;

/**
 * This class is used for preparing the test data.
 */
public final class TestDataPreparation {

    /**
     * 
     */
    private TestDataPreparation() {
    }

    /**
     * Call this method to get an instance of GamePrep for testing.
     * 
     * @return an instance of GamePrep.
     */
    public static GamePrep gamePrep() {
        final List<Pair<String, Color>> playerData = List.of(new Pair<String, Color>("Player1", Color.RED),
                new Pair<String, Color>("Player2", Color.BLUE), new Pair<String, Color>("Player3", Color.GREEN),
                new Pair<String, Color>("Player4", Color.YELLOW), new Pair<String, Color>("Player5", Color.BLACK),
                new Pair<String, Color>("Player6", Color.ORANGE));

        final City city1 = new CityImpl("Rome");
        final City city2 = new CityImpl("Milan");
        final City city3 = new CityImpl("Naples");

        final Route route1 = new RouteImpl(new EdgeData(city1, city2, 5), Color.RED, 0);
        final Route route2 = new RouteImpl(new EdgeData(city1, city3, 3), Color.BLACK, 1);
        final Route route3 = new RouteImpl(new EdgeData(city2, city3, 7), Color.GREEN, 2);

        final List<Route> routeData = List.of(route1, route2, route3);

        final GamePrep gamePrep = new GamePrep();
        gamePrep.prepGame(playerData, routeData);

        return gamePrep;
    }
}
