package it.unibo.controller.readercontroller.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.unibo.commons.Pair;
import it.unibo.model.city.api.City;
import it.unibo.model.city.impl.CityImpl;

/**
 * This class models a ReaderController for reading Cities.json file.
 */
public final class CityReaderController extends AbstractReaderController<List<City>> {

    private static final String CITY_FILE_PATH = "configuration/EuropeConfiguration/Cities.json";

    private final int cityRadius;
    private final int mapHeight;
    private final int mapWidth;
    private final List<City> cities;

    /**
     * Initializes the CityReaderController.
     */
    public CityReaderController() {
        super(CITY_FILE_PATH);
        this.cities = new LinkedList<>();
        final var controller = new MapReaderController();
        this.cityRadius = controller.getCityRadius();
        this.mapHeight = controller.getMapHeight();
        this.mapWidth = controller.getMapWidth();
    }

    /**
     * @return the result of the reading operation
     */
    @Override
    public List<City> read() {
        final JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(
                    ClassLoader.getSystemResourceAsStream(CITY_FILE_PATH),
                    StandardCharsets.UTF_8);
            final JSONArray array = (JSONArray) parser.parse(inputStreamReader);
            for (final Object elem : array) {
                obj = (JSONObject) elem;
                final int id = Integer.parseInt(obj.get("id").toString());
                final double x = (double) Integer.parseInt(obj.get("x").toString()) / this.mapWidth;
                final double y = (double) Integer.parseInt(obj.get("y").toString()) / this.mapHeight;
                final String name = obj.get("name").toString();
                final var city = new CityImpl(name, id, new Pair<Double, Double>(x, y),
                        (double) this.cityRadius / this.mapWidth);
                cities.add(city);
            }
            inputStreamReader.close();
        } catch (IOException e) {
            Logger.getLogger(CityReaderController.class.getName()).fine("Exception in file path operations");
        } catch (ParseException e1) {
            Logger.getLogger(CityReaderController.class.getName()).fine("Exception in file parsing operations");
        }
        return List.copyOf(this.cities);
    }

}
