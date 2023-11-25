package hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator

import android.util.Log
import androidx.lifecycle.viewModelScope
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionModeratorViewModel @Inject constructor(
    private val sessionModeratorPresenter: SessionModeratorPresenter
) : RainbowCakeViewModel<SessionModeratorViewState>(Loading) {

    fun addListener(session: Session) = execute {
        Log.i("session", session.toString())
        val mission = sessionModeratorPresenter.getMissionFromSession(session)
        Log.i("mission", mission.toString())
        val designer = mission?.let { sessionModeratorPresenter.getDesignerFromMission(it) }
        Log.i("designer", designer.toString())
        viewModelScope.launch {
            sessionModeratorPresenter.addListener(session).collect {
                Log.i("dolog", it.toString())
                viewState = SessionModeratorContent(
                    isLoading = true
                )
                viewState = SessionModeratorContent(
                    session = session,
                    mission = mission,
                    designer = designer,
                    players = it.toMutableList(),
                    isLoading = false
                )
            }
        }
    }
}