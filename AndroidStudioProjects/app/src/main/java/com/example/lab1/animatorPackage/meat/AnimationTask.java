package com.example.lab1.animatorPackage.meat;

import java.util.concurrent.Semaphore;

public class AnimationTask implements Runnable{

    public Semaphore sem;
    private MeatAnimator anim;
    private MeatResource res;
    public AnimationTask(MeatAnimator anim, MeatResource res){
        this.anim = anim;
        this.res = res;
    }

    @Override
    public void run(){
        anim.meat_view = res.getView();
        anim.start();

    }

}
