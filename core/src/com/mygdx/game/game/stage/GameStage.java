package com.mygdx.game.game.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.game.actor.animated.SpineActor;
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

    public void addSpineBoy(int x, int y/*, float width, float height*/) {
        SpineActor actor = new SpineActor("spineboy/", new TextureAtlas(Gdx.files.internal("spineboy/skeleton.atlas")), 1);
        addActor(actor);
        actor.setSize(0, 0);
        actor.getSkeleton().setSkin("level_3");
        actor.setAnimation(1, "stand", true);
        actor.setPosition(x, y);
        actor.addAction(Actions.scaleBy(1, 1, 0.9f, Interpolation.pow3));

        actor.clearListeners();
        actor.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                actor.setPosition(actor.getX() - actor.getWidth() / 2 + x, actor.getY() - actor.getHeight() / 2 + y);
            }
        });
    }

}
