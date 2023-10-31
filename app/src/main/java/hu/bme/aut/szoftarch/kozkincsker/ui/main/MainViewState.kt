package hu.bme.aut.szoftarch.kozkincsker.ui.main

sealed class MainViewState

data object Loading : MainViewState()

data class MainContent(
    var prediction: String = "",
    val loading: Boolean = false
) : MainViewState()