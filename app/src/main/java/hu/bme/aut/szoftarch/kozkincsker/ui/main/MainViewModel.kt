package hu.bme.aut.szoftarch.kozkincsker.ui.main

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class MainViewModel : RainbowCakeViewModel<MainViewState>(
    Loading
) {

    fun load() = execute {
        viewState = MainContent(loading = true)
        viewState = MainContent(loading = false)
    }
}