package com.mygdx.game.game.dialog;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import static com.badlogic.gdx.Gdx.app;

public class ExitConfirmDialog extends ConfirmDialog {
    private static ExitConfirmDialog instance;


    private ExitConfirmDialog(Stage stage) {
        super(stage);
        initUi();
        setDescription("Are you sure ?")
                .setPositiveButton("Yes", new ActorGestureListener(){
                    @Override
                    public void tap(InputEvent event, float x, float y, int count, int button) {
                        app.exit();
                    }
                })
                .setNegativeButton("No", new ActorGestureListener(){
                    @Override
                    public void tap(InputEvent event, float x, float y, int count, int button) {
                        hide();
                    }
                });
    }

    public static ExitConfirmDialog $(Stage stage) {
        return instance == null ? instance = new ExitConfirmDialog(stage) : instance;
    }
}
