package com.solitelab.footballmatchschedule

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class SimpleIdlingResource : IdlingResource {
    private var resourceCallback : IdlingResource.ResourceCallback? = null
    private var mIsIdleNow : AtomicBoolean = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    fun setIdleState(isIdleNow : Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && resourceCallback != null) {
            resourceCallback?.onTransitionToIdle()
        }
    }

}