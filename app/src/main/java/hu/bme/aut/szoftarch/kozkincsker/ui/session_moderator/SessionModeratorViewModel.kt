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
        val mission = sessionModeratorPresenter.getMissionFromSession(session)
        val designer = sessionModeratorPresenter.getDesignerFromMission(mission)
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