package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.domain.MissionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import javax.inject.Inject

class MissionsPresenter @Inject constructor(
    private val missionInteractor: MissionInteractor
) {
    suspend fun onListMissions() = withIOContext {
        missionInteractor.getMissionsOnce()
    }
}