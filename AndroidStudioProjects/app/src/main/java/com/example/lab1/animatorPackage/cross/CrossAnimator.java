package com.example.lab1.animatorPackage.cross;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

public class CrossAnimator {

    private int icon;
    private ImageView cross_view;
    private int fail_counter = 0;
    private boolean isAnimated = false;
    private final int CROSS_POPUP_DURATION = 250;

    public CrossAnimator(@DrawableRes int icon, ImageView cross_view){
        this.icon = icon;
        this.cross_view = cross_view;
    }

    public void start(){
        runAnimator(icon, cross_view);
    }

    private void runAnimator(int icon, ImageView view){
        if(view != null && !isAnimated){
            AnimatorSet set = new AnimatorSet();
            set.play(crossPopAnimator(cross_view));
            set.addListener(getAdapter(icon, cross_view));
            set.start();
        }
        cross_view.animate().start();
    }

    private AnimatorListenerAdapter getAdapter(@DrawableRes int icon, ImageView view){
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
                fail_counter++;
                isAnimated = false;
            }
        };
    }
    private AnimatorSet crossPopAnimator(View view){
        AnimatorSet set = new AnimatorSet();
        set.setDuration(CROSS_POPUP_DURATION).playTogether(
                ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1.4f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1.4f)
        );
        return set;
    }

    public ImageView getCross_view() {
        return cross_view;
    }

    public boolean isGameOver(){
        return (fail_counter<=2);
    }

    public void setCross_view(ImageView cross_view) {
        this.cross_view = cross_view;
    }

    public int getFail_counter() {
        return fail_counter;
    }

    public void setFail_counter(int fail_counter) {
        this.fail_counter = fail_counter;
    }
}
