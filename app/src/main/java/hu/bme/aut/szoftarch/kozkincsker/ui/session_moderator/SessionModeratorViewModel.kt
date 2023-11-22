package hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import javax.inject.Inject

@HiltViewModel
class SessionModeratorViewModel @Inject constructor(
    private val sessionModeratorPresenter: SessionModeratorPresenter
) : RainbowCakeViewModel<SessionModeratorViewState>(Loading) {
    fun load(session: Session) = execute {

        val mission = sessionModeratorPresenter.getMissionFromSession(session)
        val designer = sessionModeratorPresenter.getDesignerFromMission(mission)
        val players = sessionModeratorPresenter.getPlayersFromSession(session)

        viewState = SessionModeratorContent(
            session = session,
            mission = mission,
            designer = designer,
            players = players,
            loading = false)
    }
}