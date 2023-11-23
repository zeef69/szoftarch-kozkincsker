package hu.bme.aut.szoftarch.kozkincsker.ui.mission_start

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MissionStartViewModel @Inject constructor(private val missionStartPresenter: MissionStartPresenter) : RainbowCakeViewModel<MissionViewState>(
    Loading
) {

    fun setMission(mission: Mission) {
        viewState = MissionContent(false, mission)
    }

    fun onStartSession(session: Session, asModerator: Boolean): String? = runBlocking {
        return@runBlocking missionStartPresenter.onStartSession(session, asModerator)
    }
}