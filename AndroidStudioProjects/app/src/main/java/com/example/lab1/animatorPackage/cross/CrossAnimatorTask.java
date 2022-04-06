package com.example.lab1.animatorPackage.cross;

public class CrossAnimatorTask implements Runnable{

    private CrossAnimator anim;
    private CrossResource res;

    public CrossAnimatorTask(CrossAnimator anim, CrossResource res){
        this.anim = anim;
        this.res = res;
    }

    @Override
    public void run() {
        anim.setCross_view(res.getCross_view());
        anim.start();
    }
}
