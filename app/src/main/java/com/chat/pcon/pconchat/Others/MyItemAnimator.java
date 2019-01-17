package com.chat.pcon.pconchat.Others;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.chat.pcon.pconchat.R;

public class MyItemAnimator extends SimpleItemAnimator {
    Context context;
    public MyItemAnimator(Context context){
        this.context = context;
    }
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
        return false;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.chat_send_anim);
        viewHolder.itemView.startAnimation(animation);
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder viewHolder, int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1, int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(@NonNull RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
