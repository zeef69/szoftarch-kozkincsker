package hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class SessionModeratorViewState

data object Loading : SessionModeratorViewState()
data class SessionModeratorContent(
    var session: Session? = null,
    var mission: Mission? = null,
    var designer: User? = null,
    var players: List<User> = emptyList(),
    val isLoading: Boolean = false
) : SessionModeratorViewState()