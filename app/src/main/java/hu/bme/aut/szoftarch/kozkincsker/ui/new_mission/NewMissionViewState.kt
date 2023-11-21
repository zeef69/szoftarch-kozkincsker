package hu.bme.aut.szoftarch.kozkincsker.ui.new_mission

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class NewMissionViewState

object Loading : NewMissionViewState()

data class NewMissionContent(
    var designer: User,
    var mission: Mission = Mission(),
    var isLoading: Boolean = true
) : NewMissionViewState()
