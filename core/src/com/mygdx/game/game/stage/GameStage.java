package com.mygdx.game.game.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.util.data.ItemData;
import com.mygdx.game.game.actor.ItemActor;
import com.mygdx.game.game.screen.MenuScreen;
import com.mygdx.game.managers.CameraManager;
import com.mygdx.game.managers.SoundManager;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.util.Constants.MUSIC_GAME_SCREEN;


/**
 * Created by Armen on 10/23/2017.
 */

public class GameStage extends BaseStage {
    Array<ItemActor> listOfItems = new Array<>();

    public GameStage() {
        super();
        CameraManager.$().setGameStage(this);
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


    public void addSpineBoy(int x, int y, ItemData data) {
        ItemActor actor = new ItemActor(x, y, data);
        actor.debugAll();
        listOfItems.add(actor);
        addActor(actor);
    }

    public void hideAllItemsCloseButtons() {
        for (ItemActor item : listOfItems) {
            item.getCloseBtn().setVisible(false);

        }
    }

}
