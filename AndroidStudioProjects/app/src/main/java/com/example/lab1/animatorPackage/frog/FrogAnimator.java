package com.example.lab1.animatorPackage.frog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.DrawableRes;

import com.example.lab1.enums.ObjectPosition;

public class FrogAnimator {

    private final int FROG_RELOCATION_SPEED = 200;
    private final int FROG_RELOCATION_POSITION = 300;
    private boolean isAnimated = false;
    private int icon;
    private ImageView frog_view;
    private ObjectPosition position = ObjectPosition.MIDDLE;
    private PointF frog_pointF = null;

    public FrogAnimator(@DrawableRes int icon, ImageView frog_view){
        this.icon = icon;
        this.frog_view = frog_view;
        setFrogPointF(new PointF(frog_view.getX(), frog_view.getY()));
    }

    private AnimatorListenerAdapter getListener (ImageView view, final int icon){
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                super.onAnimationStart(animation, isReverse);
                isAnimated = true;
                view.setVisibility(View.VISIBLE);
                view.setImageResource(icon);
                view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                super.onAnimationEnd(animation, isReverse);
                isAnimated = false;
                setFrogPointF(new PointF(view.getX(), view.getY()));
            }
        };
    }
    public void runFrogAnimatorLeft(){
        if(frog_view != null && !isAnimated && position != ObjectPosition.LEFT){
            AnimatorSet set = new AnimatorSet();
            set.play(frogMovementLeft(frog_view));
            set.addListener(getListener(frog_view, icon));
            position = (position == ObjectPosition.MIDDLE)? ObjectPosition.LEFT:ObjectPosition.MIDDLE;
            setFrogPointF(new PointF(frog_view.getX()-FROG_RELOCATION_POSITION, frog_view.getY()));
            set.start();
        }
        frog_view.animate().start();
    }
    public void runFrogAnimatorRight(){
        if(frog_view != null && !isAnimated && position != ObjectPosition.RIGHT){
            AnimatorSet set = new AnimatorSet();
            set.play(frogMovementRight(frog_view));
            set.addListener(getListener(frog_view, icon));
            position = (position == ObjectPosition.MIDDLE)? ObjectPosition.RIGHT:ObjectPosition.MIDDLE;
            setFrogPointF(new PointF(frog_view.getX()+FROG_RELOCATION_POSITION, frog_view.getY()));
            set.start();
        }
        frog_view.animate().start();
    }
    private AnimatorSet frogMovementLeft(View view){
    AnimatorSet set = new AnimatorSet();
    set.setDuration(FROG_RELOCATION_SPEED).playTogether(
            ObjectAnimator.ofFloat(view, View.X, view.getX(), view.getX() -FROG_RELOCATION_POSITION)
    );
    return set;
    }

    private AnimatorSet frogMovementRight(View view){
        AnimatorSet set = new AnimatorSet();
        set.setDuration(FROG_RELOCATION_SPEED).playTogether(
                ObjectAnimator.ofFloat(view, View.X, view.getX(), view.getX() + FROG_RELOCATION_POSITION)
        );
        return set;
    }
    public PointF getFrogPointF(){
        return frog_pointF;
    }
    public void setFrogPointF(PointF pointF){
        frog_pointF = pointF;
    }
}
