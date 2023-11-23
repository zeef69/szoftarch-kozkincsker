package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session

sealed class MissionsViewState

data object Loading : MissionsViewState()

data class MissionsContent(
    var isListed: Boolean = false,
    var missions: List<Mission> = emptyList(),
    var sessions: List<Session> = emptyList(),
    var id: String? = ""
) : MissionsViewState()