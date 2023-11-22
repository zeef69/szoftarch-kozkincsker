package hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class SessionModeratorViewState

object Loading : SessionModeratorViewState()
data class SessionModeratorContent(
    var session : Session,
    var mission : Mission,
    var designer : User,
    var players : MutableList<User>,
    val loading : Boolean = false
) : SessionModeratorViewState()