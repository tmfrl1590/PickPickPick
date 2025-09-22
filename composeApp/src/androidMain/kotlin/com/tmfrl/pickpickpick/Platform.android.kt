package com.tmfrl.pickpickpick

import android.os.Build
import androidx.activity.ComponentActivity

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual class ContextFactory(private val activity: ComponentActivity) {
    actual fun getContext(): Any = activity.baseContext
    actual fun getApplication(): Any = activity.application
    actual fun getActivity(): Any = activity
}

actual fun getPlatform(): Platform {
    TODO("Not yet implemented")
}