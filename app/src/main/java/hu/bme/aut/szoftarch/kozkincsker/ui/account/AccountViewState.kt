package hu.bme.aut.szoftarch.kozkincsker.ui.account

import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class AccountViewState

data object Loading : AccountViewState()

data class AccountContent(
    var isLoading: Boolean = true,
    val userEmail: String = "",
    val user: User = User()
) : AccountViewState()