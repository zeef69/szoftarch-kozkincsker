package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import hu.bme.aut.szoftarch.kozkincsker.data.model.Session

sealed class SessionPlayerViewState

object Loading : SessionPlayerViewState()

data class SessionPlayerContent(
    var session : Session,
    val loading : Boolean = false
) : SessionPlayerViewState()