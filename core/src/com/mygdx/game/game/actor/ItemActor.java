package com.mygdx.game.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.game.ItemData;
import com.mygdx.game.game.actor.animated.SpineActor;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

/**
 * Created by Armen on 12/27/2017.
 */

public class ItemActor extends Group {
    private int groupW = 450;
    private int groupY = 300;
    private Button closeBtn;
    private SpineActor actor;

    public ItemActor(int x, int y, ItemData itemData) {
        setPosition(x, y);
        setSize(groupW, groupY);
        initCloseBtn();
        initItem(itemData);
        initListeners();
    }

    private void initListeners() {
        addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                setPosition(getX() - getWidth() / 2 + x, getY() - getHeight() / 2 + y + 100);
            }
        });

        addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Gdx.app.log("item "," from tap");
                if (closeBtn.isVisible())
                    closeBtn.setVisible(false);
                else
                    closeBtn.setVisible(true);
                event.stop();
            }
        });
        closeBtn.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                remove();
            }
        });
    }

    private void initItem(ItemData itemData) {
        actor = new SpineActor("spineboy/" + itemData.id + "/", new TextureAtlas(Gdx.files.internal("spineboy/skeleton.atlas")), .5f);
        actor.setPosition(0,80);
        actor.getSkeleton().setSkin("level_3");
        actor.setAnimation(1, "stand", true);
        actor.addAction(Actions.scaleBy(1, 1, 0.9f, Interpolation.pow3));

        addActor(actor);
    }

    private void initCloseBtn() {
        closeBtn = new Button(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_CLOSE_SHOP));
        closeBtn.setPosition(370, 0);
        closeBtn.setSize(80, 80);
        closeBtn.align(Align.topRight);
        closeBtn.setVisible(false);

        addActor(closeBtn);
    }

    public Button getCloseBtn() {
        return closeBtn;
    }
}