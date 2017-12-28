package com.mygdx.game.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.game.dialog.ExitConfirmDialog;
import com.mygdx.game.game.stage.MenuStage;
import com.mygdx.game.managers.SoundManager;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.util.Constants.MUSIC_MENU_SCREEN;

/**
 * Created by Armen on 11/8/2017.
 */

public class MenuScreen extends AbstractBaseScreen {
    public Image imgMenuBg;
    private Table table;
    private TextButton btnStart;
    private TextButton btnExit;

    @Override
    public void show() {
        SoundManager.$().playMusic(MUSIC_MENU_SCREEN, true, 0.8f);
        createBackground();
        initFonts();
        createProjection();
        setInputProcessor();
    }

    private void initFonts() {
    }

    private void createBackground() {
        imgMenuBg = new Image((Texture) Assets.$().get(Constants.IMAGE_MENU_BG));
        MenuStage.$().addActor(imgMenuBg);
        imgMenuBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        imgMenuBg.setPosition(0, 0);
    }


    private void createProjection() {
        table = new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.align(Align.center);
        btnStart = new TextButton("Start", new TextButton.TextButtonStyle(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_BUTTON_BG), null, null, Assets.$().defaultFont));
        btnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        table.add(btnStart)
                .size(Value.percentWidth(.22f, table), Value.percentHeight(.2f, table))
                .align(Align.center)
                .padTop(Value.percentHeight(.45f, table))
                .row();

        btnExit = new TextButton("Exit", new TextButton.TextButtonStyle(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_BUTTON_BG), null, null, Assets.$().defaultFont));
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ExitConfirmDialog.$(MenuStage.$()).show();
            }
        });
        table.add(btnExit)
                .size(Value.percentWidth(.11f, table), Value.percentHeight(.1f, table))
                .padTop(Value.percentHeight(.25f, table))
                .right()
                .expandX();


        MenuStage.$().addActor(table);
    }

    private void setInputProcessor() {
        Gdx.input.setInputProcessor(
                new InputMultiplexer(
                        MenuStage.$()
                )
        );

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MenuStage.$().getViewport().apply();
        MenuStage.$().getViewport().update(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        MenuStage.$().act(delta);
        MenuStage.$().draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
