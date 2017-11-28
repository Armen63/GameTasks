package com.mygdx.game.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.managers.CameraManager;

import static com.mygdx.game.util.Constants.VIEWPORT_HEIGHT;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;

/**
 * Created by Armen on 11/6/2017.
 */

public class BaseStage extends Stage {
    public BaseStage() {
        super(new FitViewport(
                VIEWPORT_WIDTH,
                VIEWPORT_HEIGHT,
                CameraManager.getInstance().camera
        ));
    }

    public BaseStage(Viewport viewport) {
        super(viewport);
    }

    public BaseStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
    }

}
