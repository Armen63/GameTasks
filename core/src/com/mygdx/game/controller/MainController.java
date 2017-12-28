package com.mygdx.game.controller;

import com.badlogic.gdx.Screen;
import com.mygdx.game.game.BaseGame;
import com.mygdx.game.game.screen.GameScreen;
import com.mygdx.game.game.screen.MenuScreen;
import com.mygdx.game.util.Assets;

public class MainController extends BaseGame {
	private static final String LOG_TAG = MainController.class.getName();

	private static Screen currentScreen;

	@Override
	public void create() {
		Assets.$().finishLoading();
		setScreen(new MenuScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		Assets.$().dispose();
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		currentScreen = screen;
	}

	public static GameScreen getGameScreen() {
		return (GameScreen) currentScreen;
	}
}
