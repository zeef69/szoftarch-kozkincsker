package hu.bme.aut.szoftarch.kozkincsker.ui.new_mission

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.domain.MissionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import javax.inject.Inject

class NewMissionPresenter
@Inject constructor(
    private val missionInteractor: MissionInteractor
) {
    suspend fun uploadMission(newMission: Mission): String? = withIOContext {
        return@withIOContext missionInteractor.uploadMission(newMission)
    }

    suspend fun updateMission(editedMission: Mission): Unit = withIOContext {
        missionInteractor.editMission(editedMission)
    }
}