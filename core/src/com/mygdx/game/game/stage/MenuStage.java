package com.mygdx.game.game.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Armen on 11/8/2017.
 */

public class MenuStage extends BaseStage {
    private static MenuStage instance;

    private MenuStage(Viewport viewport) {
        super(viewport);
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            app.exit();
        }
        return super.keyDown(keyCode);
    }

    public static Stage $() {
        return instance == null ? instance = new MenuStage(new ScreenViewport()) : instance;
    }
}
