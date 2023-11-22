package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class SessionPlayerViewState

object Loading : SessionPlayerViewState()

data class SessionPlayerContent(
    var session : Session,
    var mission : Mission,
    var designer : User,
    val loading : Boolean = true
) : SessionPlayerViewState()