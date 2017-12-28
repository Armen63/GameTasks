package com.mygdx.game.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.managers.SoundManager;

import static com.mygdx.game.util.Constants.MUSIC_DIALOG_BLT;

/**
 * Created by Armen on 11/9/2017.
 */

public abstract class BaseDialog extends Table implements GestureDetector.GestureListener {

    Group group;
    private Stage stage;
    private boolean isOpened = false;


    public BaseDialog() {
    }

    BaseDialog(Stage stage) {
        this(stage, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private BaseDialog(Stage stage, float width, float height, float x, float y, boolean isAnimate) {
        group = new Group();
        this.stage = stage;
    }

    private BaseDialog(Stage stage, float width, float height, float x, float y) {
        this(stage, width, height, x, y, false);
    }

    private BaseDialog(Stage stage, float width, float height) {
        this(stage, width, height, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    public BaseDialog(Stage stage, float width, float height, boolean isAnimated) {
        this(stage, width, height, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, isAnimated);
    }

    public void show() {
        if (!isOpened) {
            SoundManager.$().playMusic(MUSIC_DIALOG_BLT,false,1);
            stage.addActor(group);
            isOpened = true;
        }

    }

    void hide() {
        SoundManager.$().playMusic(MUSIC_DIALOG_BLT,false,1.f);
        isOpened = false;
        group.remove();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }
}
