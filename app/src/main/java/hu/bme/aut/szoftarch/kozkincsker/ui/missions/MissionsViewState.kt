package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission

sealed class MissionsViewState

data object Loading : MissionsViewState()

data class MissionsContent(
    var isListed: Boolean = false,
    var missions: List<Mission> = emptyList(),
    var uid: String? = ""
) : MissionsViewState()