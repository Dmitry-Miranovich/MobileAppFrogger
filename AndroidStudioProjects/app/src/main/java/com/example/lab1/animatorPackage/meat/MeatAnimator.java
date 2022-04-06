package com.example.lab1.animatorPackage.meat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

public class MeatAnimator {
    private boolean isAnimate = false;
    private boolean isMeatDropped = false;

    private final int MEAT_MOVING_DURATION =2200;
    private final int MEAT_APPEARING_DURATION = 500;
    private final int MEAT_DISAPPEARING_DURATION = 50;
    private final int OBJECT_HIT_DISTANCE_Y = 100;
    private final int OBJECT_HIT_DISTANCE_X = 100;

    private float meatDefaultY = 0;

    private int satietyIndex = 0;

    public int icon = 0;
    public PointF frog_pointF;
    public ImageView meat_view;
    public TextView satietyView;

    public MeatAnimator(@DrawableRes int icon, ImageView meat_view, TextView satietyView) {
        this.meat_view = meat_view;
        this.satietyView = satietyView;
        this.icon = icon;
    }

    public void setFrogPointF(PointF pointF){
        frog_pointF = pointF;
    }
    public void start(){
        meatDropAnimation(icon, meat_view);
    }
    private void meatDropAnimation(@DrawableRes int icon, ImageView view){
        if(view != null){
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(
                    showAnimatorSet(view),
                    moveAnimatorSet(view),
                    endAnimatorSet(view)

            );
            set.addListener(getListener(view, icon));
            set.start();
        }
        view.animate().start();
    }

    private AnimatorSet showAnimatorSet(View view){
        AnimatorSet set = new AnimatorSet();
        set.setDuration(MEAT_APPEARING_DURATION).playTogether(
                ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1f)
        );
        return set;
    }
    private AnimatorSet moveAnimatorSet(View view){
        AnimatorSet set = new AnimatorSet();

        set.setDuration(MEAT_MOVING_DURATION).playTogether(
                ObjectAnimator.ofFloat(view, View.Y, frog_pointF.y+100)
        );
        return set;
    }
    private AnimatorSet endAnimatorSet(View view){
        AnimatorSet set = new AnimatorSet();
        set.setDuration(MEAT_DISAPPEARING_DURATION).playTogether(
                ObjectAnimator.ofFloat(view, View.ALPHA, 1f,0f),
                ObjectAnimator.ofFloat(view, View.SCALE_X, 1f,0.2f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.2f)
        );
        return set;
    }
    private AnimatorListenerAdapter getListener(ImageView view, final int icon){
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                super.onAnimationStart(animation, isReverse);
                isAnimate = true;
                view.setVisibility(View.VISIBLE);
                view.setImageResource(icon);
                view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                super.onAnimationEnd(animation, isReverse);
                isAnimate = false;
                changeSatiety(view);
            }
        };

    }
    private void changeSatietyParam(){
        satietyIndex++;
        String indexName = Integer.toString(satietyIndex);
        satietyView.setText(indexName);
    }
    private void changeSatiety(ImageView view){
        float m_x = view.getX();
        float m_y = view.getY();
        if(Math.abs(m_y- frog_pointF.y) <= OBJECT_HIT_DISTANCE_Y
                && Math.abs(m_x - frog_pointF.x) <= OBJECT_HIT_DISTANCE_X && !isAnimate){
            changeSatietyParam();
        }else{
            isMeatDropped = true;
        }
        view.setY(meatDefaultY);
    }
    public int getSatietyIndex() {
        return satietyIndex;
    }

    public void setSatietyIndex(int satietyIndex) {
        this.satietyIndex = satietyIndex;
    }

    public void setMeatDefaultY(float meatDefaultY) {
        this.meatDefaultY = meatDefaultY;
    }

    public boolean isMeatDropped() {
        return isMeatDropped;
    }

    public void setMeatDropped(boolean meatDropped) {
        isMeatDropped = meatDropped;
    }
}
