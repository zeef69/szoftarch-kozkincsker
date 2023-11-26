package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import androidx.lifecycle.viewModelScope
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MissionsViewModel @Inject constructor(
    private val missionsPresenter: MissionsPresenter
) : RainbowCakeViewModel<MissionsViewState>(
    Loading
) {
    fun addListener() = execute {
        val id = missionsPresenter.getId()
        viewModelScope.launch {
            var missions: List<Mission> = emptyList()
            var sessions: List<Session> = emptyList()
            launch {
                missionsPresenter.addListener().collect() {
                    missions = it
                    viewState = MissionsContent(isListed = true)
                    viewState = MissionsContent(
                        missions = it,
                        sessions = sessions,
                        id = id,
                        isListed = false
                    )
                }
            }
            launch {
                missionsPresenter.getPlayingOrModeratedSessionsFromUser(id).collect {
                    sessions = it
                    viewState = MissionsContent(isListed = true)
                    viewState = MissionsContent(
                        missions = missions,
                        sessions = it,
                        id = id,
                        isListed = false
                    )
                }
            }
        }
    }

    fun deleteMission(mission: Mission) = execute {
        missionsPresenter.deleteMission(mission)
    }

    fun getDesigner(): User? = runBlocking {
        return@runBlocking missionsPresenter.getUser()
    }

    fun joinWithCode(code: String): Session? = runBlocking {
        return@runBlocking missionsPresenter.joinWithCode(code)
    }
    fun joinPrivateGame(code: String): Mission? = runBlocking {
        return@runBlocking missionsPresenter.joinPrivateGame(code)
    }
}