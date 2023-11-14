package hu.bme.aut.szoftarch.kozkincsker.ui.new_mission

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission

sealed class NewMissionViewState

object Loading : NewMissionViewState()

data class NewMissionContent(
    var mission: Mission = Mission(),
    var isLoading: Boolean = true
) : NewMissionViewState()
