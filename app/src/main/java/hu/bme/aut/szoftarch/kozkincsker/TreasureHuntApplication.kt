package hu.bme.aut.szoftarch.kozkincsker

import android.app.Application
import co.zsmb.rainbowcake.config.rainbowCake
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class TreasureHuntApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        rainbowCake {
            isDebug = BuildConfig.DEBUG
        }
    }
}