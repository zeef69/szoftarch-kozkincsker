package hu.bme.aut.szoftarch.kozkincsker.ui.new_mission

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import javax.inject.Inject
@HiltViewModel
class NewMissionViewModel
@Inject constructor(
    private val newMissionPresenter: NewMissionPresenter
)
    : RainbowCakeViewModel<NewMissionViewState>(Loading){

    fun load(mission: Mission) = execute {
        viewState = NewMissionContent(
                    mission = mission,
                    isLoading = false
                )

        }

    fun uploadMission(newMission: Mission) = execute{
        newMissionPresenter.uploadMission(newMission)
    }
}