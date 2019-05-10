package com.solitelab.footballmatchschedule

object EspressoIdlingResource {
    val idlingResource = SimpleIdlingResource()

    fun setIdleState(idleState : Boolean) {
        idlingResource.setIdleState(idleState)
    }
}