package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.mygdx.game.game.MainController;
import com.mygdx.game.game.stage.UiStage;
import com.mygdx.game.util.Constants;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Armen on 10/23/2017.
 */

public class CameraManager {
    private final static String LOG_TAG = CameraManager.class.getName();
    private static CameraManager instance;


    Interpolation interpolation = Interpolation.pow3Out;
    public OrthographicCamera camera;
    private GestureDetector gestureDetector;
    private Stage stage;
    private final float shortDuration = .3f;


    private final float minZoomBound = 0.8f;
    private final float maxZoomBound = 1.8f;

    private final Actor cameraActor = new Actor();


    public void setGameStage(Stage stage) {
        this.stage = stage;
    }

    private CameraManager() {
        gestureDetector = new GestureDetector(new ZoomGestureHandler()) {
            @Override
            public boolean scrolled(int amount) {
                return true;
            }

        };

        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        camera.position.set(0, 0, 0); // ?????
        app.log(LOG_TAG, "ssss" + camera.viewportWidth + "    " + camera.viewportHeight);

        camera.update();
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }


    private class ZoomGestureHandler extends GestureDetector.GestureAdapter {
        public float initialZoom = maxZoomBound;
        private boolean isZoom;

        @Override
        public boolean longPress(float x, float y) {
            Vector3 touchPos = new Vector3(x, y, 0);
            stage.getCamera().unproject(touchPos);

            float alpha = MainController.getGameScreen().wordBg.getColor().a;
            app.log("asdasda", alpha + "");

            if ((int) alpha > 0)
                MainController.getGameScreen().wordBg.getColor().set(
                        MainController.getGameScreen().wordBg.getColor().r,
                        MainController.getGameScreen().wordBg.getColor().g,
                        MainController.getGameScreen().wordBg.getColor().b,
                        alpha - 0.25f
                );
            else {
                MainController.getGameScreen().wordBg.getColor().set(
                        MainController.getGameScreen().wordBg.getColor().r,
                        MainController.getGameScreen().wordBg.getColor().g,
                        MainController.getGameScreen().wordBg.getColor().b,
                        1f
                );
            }
            app.log(LOG_TAG, " alpha = " + alpha);

            return false;
        }


        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            if (pointer != 0) {
                return false;
            }
            app.log(LOG_TAG, "xxt= " + x + " y = " + y);
            stage.getRoot().clearActions();
            UiStage.$().setScrollFocus(null);
            initialZoom = ((OrthographicCamera) stage.getCamera()).zoom;

            isZoom = false;
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
//            app.log(LOG_TAG, "zoom");
            isZoom = true;

            float ratio = initialDistance / distance;
            float zoom = MathUtils.clamp(initialZoom * ratio, minZoomBound, maxZoomBound);


            ((OrthographicCamera) stage.getCamera()).zoom = zoom;
            app.log(LOG_TAG, " zoom  = " + zoom);
            float x = MathUtils.clamp(
                    stage.getCamera().position.x, stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f,
                    Gdx.graphics.getWidth() - stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f);
            float y = MathUtils.clamp(stage.getCamera().position.y,
                    stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f,
                    Gdx.graphics.getHeight() - stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f);

            stage.getCamera().position.x = x;
            stage.getCamera().position.y = y;


            return false;
        }

        @Override
        public void pinchStop() {
            app.log(LOG_TAG, "pinch stop");
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
//            /app.log(LOG_TAG, "pan");
            if (isZoom) {
                return false;
            }
            float deltaXX = (.5f * deltaX * Gdx.graphics.getWidth() * ((OrthographicCamera) stage.getCamera()).zoom / stage.getViewport().getScreenWidth());
            float deltaYY = (.5f * deltaY * Gdx.graphics.getHeight() * ((OrthographicCamera) stage.getCamera()).zoom / stage.getViewport().getScreenHeight());

            stage.getCamera().position.add(-deltaXX, deltaYY, 0);
            stage.getCamera().position.x = MathUtils.clamp(stage.getCamera().position.x, stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f, Gdx.graphics.getWidth() - stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f);
            stage.getCamera().position.y = MathUtils.clamp(stage.getCamera().position.y, stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f, Gdx.graphics.getHeight() - stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f);

            return true;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
//            app.log(LOG_TAG, "pan stop");
            if (isZoom)
                return false;
            float xx;
            float yy;

            float tmp = stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f;
            if (stage.getCamera().position.x < tmp) {
                xx = tmp;
            } else {
                tmp = Gdx.graphics.getWidth() - stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f;
                if (stage.getCamera().position.x > tmp) {
                    xx = tmp;
                } else {
                    xx = stage.getCamera().position.x;
                }
            }

            tmp = stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f;
            if (stage.getCamera().position.y < tmp) {
                yy = tmp;
            } else {
                tmp = Gdx.graphics.getHeight() - stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f;
                if (stage.getCamera().position.y > tmp) {
                    yy = tmp;
                } else {
                    yy = stage.getCamera().position.y;
                }
            }

            setCameraPosition(xx, yy, shortDuration, interpolation);
            return true;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
//            app.log(LOG_TAG, "fling ");
            if (isZoom)
                return false;
            float duration = 0.9f; //Vector2.len2(velocityX, velocityY);
            float coeff = .9f;

            float deltaXX = velocityX * duration * coeff * stage.getWidth() * ((OrthographicCamera) stage.getCamera()).zoom / stage.getViewport().getScreenWidth();
            float deltaYY = velocityY * duration * coeff * stage.getHeight() * ((OrthographicCamera) stage.getCamera()).zoom / stage.getViewport().getScreenHeight();
            float x = stage.getCamera().position.x - deltaXX;
            float y = stage.getCamera().position.y + deltaYY;

            x = MathUtils.clamp(x, stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f, Gdx.graphics.getWidth() - stage.getCamera().viewportWidth * ((OrthographicCamera) stage.getCamera()).zoom * .5f);
            y = MathUtils.clamp(y, stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f, Gdx.graphics.getHeight() - stage.getCamera().viewportHeight * ((OrthographicCamera) stage.getCamera()).zoom * .5f);

            stage.getRoot().clearActions();
            setCameraPosition(x, y, duration, interpolation);
            return true;
        }
    }


    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            stage.getCamera().translate(-30, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            stage.getCamera().translate(30, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            stage.getCamera().translate(0, 30, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            stage.getCamera().translate(0, -30, 0);
        }

    }

    public static CameraManager getInstance() {
        return instance == null ? instance = new CameraManager() : instance;
    }

    private void setCameraPosition(Float x, Float y, float duration, Interpolation interpolation) {
        float currentZoom = ((OrthographicCamera)stage.getCamera()).zoom;
        boolean isUpdatable = false;
        cameraActor.setScale(currentZoom);
        if(cameraActor.getScaleX() != currentZoom) {
            ScaleToAction action = Actions.scaleTo(currentZoom , currentZoom , duration, interpolation);
            action.setTarget(cameraActor);
            stage.getRoot().addAction(action);
            isUpdatable = true;
        }

        stage.getRoot().clearActions();
        app.log(LOG_TAG, "camera position  = " + camera.position.x + " x = " + x + " havayi = ");


        cameraActor.setX(stage.getCamera().position.x);
        cameraActor.setY(stage.getCamera().position.y);


        if (x != null && y != null) {

            if (cameraActor.getX() != x || cameraActor.getY() != y) {
                MoveToAction moveToAction = Actions.moveTo(x, y, duration, interpolation);
                moveToAction.setTarget(cameraActor);
                stage.getRoot().addAction(moveToAction);

                isUpdatable = true;
            }
        }
        if (isUpdatable) {
            final Action updateAction = new Action() {
                @Override
                public boolean act(float delta) {
                    ((OrthographicCamera)stage.getCamera()).zoom = cameraActor.getScaleX();
                    stage.getCamera().position.x = cameraActor.getX();
                    stage.getCamera().position.y = cameraActor.getY();
                    return false;
                }
            };

            stage.getRoot().addAction(updateAction);

            stage.getRoot().addAction(Actions.delay(duration, Actions.run(new Runnable() {
                @Override
                public void run() {
                    stage.getRoot().removeAction(updateAction);
                }
            })));
        }
    }
}
