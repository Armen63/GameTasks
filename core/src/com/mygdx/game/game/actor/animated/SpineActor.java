package com.mygdx.game.game.actor.animated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonBounds;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.esotericsoftware.spine.utils.SkeletonActor;

import static com.badlogic.gdx.utils.Align.bottom;
import static com.badlogic.gdx.utils.Align.left;
import static com.badlogic.gdx.utils.Align.right;
import static com.badlogic.gdx.utils.Align.top;

/**
 * Created by nelliminasyan on 10/8/15.
 */
public class SpineActor extends SkeletonActor {
    public static final String SKELETON_FILE_NAME = "skeleton.json";
    protected static final float ANIMATION_MIX_TIME = 0.4f;
    protected BoundingBoxAttachment hit;
    protected Skeleton skeleton;
    protected SkeletonData skeletonData;
    protected SkeletonBounds skeletonBounds;
    protected AnimationState state;
    protected AnimationStateData stateData;
    final Vector2 tmp = new Vector2();

    private String jsonPath;

    public SpineActor() {
    }

    public SpineActor(String jsonPath, TextureAtlas atlas, float scale) {
        this.jsonPath = jsonPath;
        SkeletonJson skeletonJson = new SkeletonJson(atlas);
        skeletonJson.setScale(scale);
        skeletonData = getSkeletonData(jsonPath + SKELETON_FILE_NAME, skeletonJson);


        init(skeletonData);
        initSomeData();

    }

    public SkeletonData getSkeletonData(String path, SkeletonJson json) {
        if (skeletonData == null) {
            try {
                skeletonData = json.readSkeletonData(Gdx.files.internal(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return skeletonData;
    }

    public SpineActor(SkeletonData skeletonData) {
        this.skeletonData = skeletonData;
        init(this.skeletonData);

        initSomeData();

    }

    private void init(SkeletonData skeletonData_) {

        skeleton = new Skeleton(skeletonData_); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        skeletonBounds = new SkeletonBounds(); // Convenience class to do hit detection with bounding boxes.
        stateData = new AnimationStateData(skeletonData_); // Defines mixing (crossfading) between animations.
        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).

        if (getRenderer() == null) {
            setRenderer(new SkeletonRenderer());
        }
        setSkeleton(skeleton);
        setAnimationState(state);
    }

    protected void initSomeData(){
        super.setScale(skeleton.getRootBone().getScaleX(), skeleton.getRootBone().getScaleY());
        super.setRotation(skeleton.getRootBone().getRotation());
        super.setPosition(skeleton.getX(), skeleton.getY());

        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, true);
    }

    protected void setToSetupPose() {
        skeleton.setToSetupPose();
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() != Touchable.enabled) return null;

        tmp.set(x, y);

        localToStageCoordinates(tmp);
        skeletonBounds.update(skeleton, false); // Update SkeletonBounds with current skeleton bounding box positions.
        hit = skeletonBounds.containsPoint(tmp.x, tmp.y); // Check if inside a bounding box.
        if (hit != null) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        skeleton.getRootBone().setScale(scaleX, scaleY);
        super.setScale(scaleX, scaleY);
    }

    @Override
    public void setScale(float scaleXY) {
        skeleton.getRootBone().setScale(scaleXY);
        super.setScale(scaleXY);
    }

    @Override
    public void setScaleX(float scaleX) {
        skeleton.getRootBone().setScaleX(scaleX);
        super.setScaleX(scaleX);
    }

    @Override
    public void setScaleY(float scaleY) {
        skeleton.getRootBone().setScaleY(scaleY);
        super.setScaleY(scaleY);
    }

    @Override
    public void scaleBy(float scale) {
        skeleton.getRootBone().setScale(skeleton.getRootBone().getScaleX() + scale, skeleton.getRootBone().getScaleY() + scale);
        super.scaleBy(scale);
    }

    @Override
    public void scaleBy(float scaleX, float scaleY) {
        skeleton.getRootBone().setScale(skeleton.getRootBone().getScaleX() + scaleX, skeleton.getRootBone().getScaleY() + scaleY);
        super.scaleBy(scaleX, scaleY);
    }

    @Override
    public void setRotation(float degrees) {
        skeleton.getRootBone().setRotation(degrees);
        super.setRotation(degrees);
    }

    @Override
    public void rotateBy(float amountInDegrees) {
        skeleton.getRootBone().setRotation(skeleton.getRootBone().getRotation() + amountInDegrees);
        super.rotateBy(amountInDegrees);
    }


    @Override
    public void setWidth(float width) {
        float scaleX = width / getWidth();
        setScaleX(scaleX * getScaleX());
        super.setWidth(width);
    }

    @Override
    public float getWidth() {
        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, true);
        return skeletonBounds.getWidth();
    }

    @Override
    public void setHeight(float height) {
        float scaleY = height / getHeight();
        setScaleY(scaleY * getScaleY());
        super.setHeight(height);
    }

    @Override
    public float getHeight() {
        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, true);
        return skeletonBounds.getHeight();
    }

    @Override
    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
        super.setSize(width, height);
    }

    public void setSize_(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void sizeBy(float size) {
        if (size != 0) {
            setSize(getWidth() + size, getHeight() + size);
        }
        super.sizeBy(size);
    }

    @Override
    public void sizeBy(float width, float height) {
        if (width != 0 || height != 0) {
            setSize(getWidth() + width, getHeight() + height);
        }
        super.sizeBy(width, height);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        if ((alignment & right) != 0)
            x -= getWidth();
        else if ((alignment & left) == 0) //
            x -= getWidth() / 2;

        if ((alignment & top) != 0)
            y -= getHeight();
        else if ((alignment & bottom) == 0) //
            y -= getHeight() / 2;

        if (getX() != x || getY() != y) {
            setX(x);
            setY(y);
            positionChanged();
        }
    }

    @Override
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    public void setX(float x) {
        if (getX() != x) {
            skeleton.setX(x);
            super.setX(x);
        }
    }

    @Override
    public float getX(int alignment) {
        float x = getX();
        if ((alignment & right) != 0)
            x += getWidth();
        else if ((alignment & left) == 0) //
            x += getWidth() / 2;
        return x;
    }

    @Override
    public void setY(float y) {
        if (getY() != y) {
            skeleton.setY(y);
            super.setY(y);
        }
    }

    @Override
    public float getY(int alignment) {
        float y = getY();
        if ((alignment & top) != 0)
            y += getHeight();
        else if ((alignment & bottom) == 0) //
            y += getHeight() / 2;
        return y;
    }

    @Override
    public void moveBy(float x, float y) {
        if (x != 0 || y != 0) {
            setX(getX() + x);
            setY(getY() + y);
        }
    }

    @Override
    public void setOrigin(int alignment) {
        // ignore
    }

    @Override
    public void setOrigin(float originX, float originY) {
        // ignore
    }

    @Override
    public void setOriginX(float originX) {
        // ignore
    }

    @Override
    public void setOriginY(float originY) {
        // ignore
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        if (getX() != x || getY() != y) {
            setX(x);
            setY(y);
            positionChanged();
        }
        if (getWidth() != width || getHeight() != height) {
            setWidth(width);
            setHeight(height);
            sizeChanged();
        }
        super.setBounds(x, y, width, height);
    }


    public String getSlotAttachmentName(String slotName) {
        return skeleton.findSlot(slotName).getAttachment().getName();
    }

    public void updateWorldransform() {
        skeleton.updateWorldTransform();
    }

    public AnimationState.TrackEntry setAnimation(int trackIndex, String animationName, boolean loop) {
        return state.setAnimation(trackIndex, animationName, loop);
    }

    public void setSequenceOfAnimations(final AnimationData... animations){
        if(animations.length == 0){
            return ;
        }
        state.setAnimation(animations[0].trackIndex,animations[0].animationName,animations[0].loop).setListener(new AnimationState.AnimationStateListener() {
            @Override
            public void start(AnimationState.TrackEntry entry) {

            }

            @Override
            public void interrupt(AnimationState.TrackEntry entry) {

            }

            @Override
            public void end(AnimationState.TrackEntry entry) {

            }

            @Override
            public void dispose(AnimationState.TrackEntry entry) {

            }

            @Override
            public void complete(AnimationState.TrackEntry entry) {
                if(animations.length > 1){
                    AnimationData[] array = new AnimationData[animations.length - 1];
                    for (int i = 0; i < array.length;) {
                        array[i] = animations[++i];
                    }
                    setSequenceOfAnimations(array);
                }
            }

            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {

            }
        });

    }

    public static class AnimationData{
        int trackIndex;
        String animationName;
        boolean loop;

        public AnimationData(int trackIndex, String animationName, boolean loop){
            this.trackIndex=trackIndex;
            this.animationName = animationName;
            this.loop = loop;
        }

    }

    public void setColor(float r, float g, float b) {
        for (Slot slot : getSkeleton().getSlots()) {
            if (slot.getAttachment() instanceof RegionAttachment) {
                slot.getColor().r = r;
                slot.getColor().g = g;
                slot.getColor().b = b;
            }
        }
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        for (Slot slot : getSkeleton().getSlots()) {
            if (slot.getAttachment() instanceof RegionAttachment) {
                slot.getColor().r = r;
                slot.getColor().g = g;
                slot.getColor().b = b;
                slot.getColor().a = a;
            }
        }
    }

    public void setOpacity(float a) {
        for (Slot slot : getSkeleton().getSlots()) {
            if (slot.getAttachment() instanceof RegionAttachment) {
                slot.getColor().a = a;
            }
        }
    }

    public float getOpacity() {
        for (Slot slot : getSkeleton().getSlots()) {
            if (slot.getAttachment() instanceof RegionAttachment) {
                return slot.getColor().a;
            }
        }
        return 0;
    }

    @Override
    public Color getColor() {
        for (Slot slot : getSkeleton().getSlots()) {
            if (slot.getAttachment() instanceof RegionAttachment) {
                return new Color(slot.getColor().r, slot.getColor().g, slot.getColor().b, slot.getColor().a);
            }
        }
        return new Color(1, 1, 1, 1);
    }
}
