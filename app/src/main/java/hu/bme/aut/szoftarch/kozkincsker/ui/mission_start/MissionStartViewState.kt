package hu.bme.aut.szoftarch.kozkincsker.ui.mission_start

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class MissionViewState

data object Loading : MissionViewState()

data class MissionContent(
    var isLoading: Boolean = true,
    var mission: Mission? = null,
    var actualUser: User? = null,
    var designer: User? = null,
) : MissionViewState()