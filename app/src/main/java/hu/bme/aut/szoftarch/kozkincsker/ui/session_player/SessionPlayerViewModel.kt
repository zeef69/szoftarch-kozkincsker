package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import javax.inject.Inject

@HiltViewModel
class SessionPlayerViewModel @Inject constructor(
    private val sessionPlayerPresenter: SessionPlayerPresenter
) : RainbowCakeViewModel<SessionPlayerViewState>(Loading) {
    fun load(session: Session) = execute {
        Log.i("dolog", session.toString())
        val mission = sessionPlayerPresenter.getMissionFromSession(session)
        val designer = sessionPlayerPresenter.getDesignerFromMission(mission)
        viewState = SessionPlayerContent(
            session = session,
            mission = mission,
            designer = designer,
            loading = false
        )
    }
}