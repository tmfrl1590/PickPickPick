package com.tmfrl.pickpickpick

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform