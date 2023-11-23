package hu.bme.aut.szoftarch.kozkincsker.ui.new_mission

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import javax.inject.Inject
@HiltViewModel
class NewMissionViewModel
@Inject constructor(
    private val newMissionPresenter: NewMissionPresenter
)
    : RainbowCakeViewModel<NewMissionViewState>(Loading){

    fun load(mission: Mission, designer: User) = execute {
        viewState = NewMissionContent(
                    designer = designer,
                    mission = mission,
                    isLoading = false
                )

        }

    fun uploadMission(newMission: Mission) = execute{
        newMissionPresenter.uploadMission(newMission)
    }
    fun updateMission(editedMission: Mission) = execute{
        newMissionPresenter.updateMission(editedMission)
    }
}