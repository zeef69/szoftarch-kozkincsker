package hu.bme.aut.szoftarch.kozkincsker.ui.main

import co.zsmb.rainbowcake.base.RainbowCakeViewModel

class MainViewModel : RainbowCakeViewModel<MainViewState>(
    Loading
) {
    fun setNav() = execute {
        viewState = MainContent(loading = true)
        viewState = MainContent(loading = false)
    }
}