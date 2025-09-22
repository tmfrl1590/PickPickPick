package com.tmfrl.pickpickpick

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect class ContextFactory {
    fun getContext(): Any
    fun getApplication(): Any
    fun getActivity(): Any
}