package com.mygdx.game.game.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.game.actor.animated.SpineActor;
import com.mygdx.game.game.screen.MenuScreen;
import com.mygdx.game.managers.CameraManager;
import com.mygdx.game.managers.SoundManager;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.util.Constants.MUSIC_GAME_SCREEN;


/**
 * Created by Armen on 10/23/2017.
 */

public class GameStage extends BaseStage implements InputProcessor {

    float groupPosX;
    float groupPosY;
    int groupW = 450;
    int groupY = 300;
    Button closeBtn;
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



    public void addSpineBoy(int x, int y, int id) {
        closeBtn = new Button(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_CLOSE_SHOP));
        Group mainGroup = new Group();
        mainGroup.setPosition(x, y);
        mainGroup.setSize(groupW, groupY);
        mainGroup.addActor(closeBtn);

        closeBtn.setPosition(370, 0);
        closeBtn.setSize(80, 80);
        closeBtn.align(Align.topRight);
        closeBtn.setVisible(false);

        SpineActor actor = new SpineActor("spineboy/" + id + "/", new TextureAtlas(Gdx.files.internal("spineboy/skeleton.atlas")), 1);
        actor.setPosition(0, 80);
        actor.setSize(0, 0);
        actor.getSkeleton().setSkin("level_3");
        actor.setAnimation(1, "stand", true);
        actor.addAction(Actions.scaleBy(1, 1, 0.9f, Interpolation.pow3));

        mainGroup.addActor(actor);
        addActor(mainGroup);

        mainGroup.clearListeners();
        mainGroup.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                mainGroup.setPosition(mainGroup.getX() - mainGroup.getWidth() / 2 + x, mainGroup.getY() - mainGroup.getHeight() / 2 + y + 100);
                groupPosX = mainGroup.getX();
                groupPosY = mainGroup.getY();

            }
        });
        mainGroup.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (closeBtn.isVisible())
                    closeBtn.setVisible(false);
                else
                    closeBtn.setVisible(true);
            }
        });
        closeBtn.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                mainGroup.remove();
            }
        });

        mainGroup.debug();
    }


//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        if(screenX < groupPosX || screenX > groupPosX + groupW|| screenY< groupPosY || screenY > groupPosY + groupY )
//            closeBtn.setVisible(false);
//        return false;
//    }


}
