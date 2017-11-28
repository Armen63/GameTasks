package com.mygdx.game.game.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.mygdx.game.game.screen.MenuScreen;
import com.mygdx.game.managers.CameraManager;
import com.mygdx.game.managers.SoundManager;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.util.Constants.MUSIC_GAME_SCREEN;


/**
 * Created by Armen on 10/23/2017.
 */

public class GameStage extends BaseStage {

    public GameStage() {
        super();
        CameraManager.getInstance().setGameStage(this);
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            ((Game) app.getApplicationListener()).setScreen(new MenuScreen());
            SoundManager.$().stopMusic(MUSIC_GAME_SCREEN);
        }
        return super.keyDown(keyCode);
    }
}
