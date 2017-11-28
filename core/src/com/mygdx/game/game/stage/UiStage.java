package com.mygdx.game.game.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.game.screen.MenuScreen;

/**
 * Created by Armen on 11/6/2017.
 */

public class UiStage extends BaseStage {
    private static UiStage instance;

    private UiStage(ScreenViewport viewport, Batch batch) {
        super(viewport,batch);
        Gdx.app.log("Uistage", "constructor");
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    public void act() {
        super.act();
    }

    public static Stage $() {
        return instance == null ? instance = new UiStage(new ScreenViewport(),new PolygonSpriteBatch(4000)) : instance;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
        }
        return super.keyDown(keyCode);
    }
}
