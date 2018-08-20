package com.udacity.gradle.builditbigger.idlingResource;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback mCallback;

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    public void setIdlingResource(boolean isIdle){
        mIsIdleNow.set(isIdle);
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }
}
