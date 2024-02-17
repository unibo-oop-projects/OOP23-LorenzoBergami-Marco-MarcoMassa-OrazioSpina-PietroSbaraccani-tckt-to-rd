package it.unibo.controller.gamecontroller.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.commons.Pair;
import it.unibo.controller.gamecontroller.api.GameController;
import it.unibo.controller.gamecontroller.api.MainController;
import it.unibo.model.scorecalculator.api.ScoreCalculator;
import it.unibo.model.scorecalculator.impl.ScoreCalculatorImpl;
import it.unibo.view.MainView;

/**
 * Implementation of {@link GameController}.
 * It models the game controller that allows the view comunicate with the model
 */
public class GameControllerImpl implements GameController {

    final private MainController mainController;
    final private List<Pair<String, Color>> tempPlayers = new ArrayList<>();

    private MainView view;

    /**
     * Simple constructor of the controller of the game logic
     * 
     * @param mainController the main controller of the aplication
     */
    public GameControllerImpl(final MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {
        mainController.getTurnController().endTurn();
        view.refreshPlayerInterface();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Integer>> getScore() {
        final ScoreCalculator scoreCalculator = new ScoreCalculatorImpl();
        return scoreCalculator.getScoreBoard(mainController.getGameInstance().getPlayers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newGame() {
        view.launchPlayerSlect();
        tempPlayers.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addPlayer(Pair<String, Color> player) {
        if (tempPlayers.stream().anyMatch(
                x -> x.first().equals(player.first()) || x.second().equals(player.second()))
                || tempPlayers.size() >= 6) {
            return false;
        }
        tempPlayers.add(player);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canStart() {
        return tempPlayers.size() >= 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Color>> getTempPlayers() {
        return Collections.unmodifiableList(this.tempPlayers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        view.closeMainView();
        view.launchScoreBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addView(MainView view) {
        this.view = view;
    }
}
