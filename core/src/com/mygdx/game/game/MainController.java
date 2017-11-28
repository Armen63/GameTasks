package com.mygdx.game.game;

import com.badlogic.gdx.Screen;
import com.mygdx.game.game.screen.GameScreen;
import com.mygdx.game.util.Assets;

public class MainController extends BaseGame {
	private static final String LOG_TAG = MainController.class.getName();



	public static Screen currentScreen;

	@Override
	public void create() {
		Assets.$().finishLoading();
		setScreen(new GameScreen());
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