package hu.bme.aut.szoftarch.kozkincsker.ui.login

sealed class LoginViewState

data object Loading : LoginViewState()

data class LoginContent(
    var isLoading: Boolean = true
) : LoginViewState()