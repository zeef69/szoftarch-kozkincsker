package hu.bme.aut.szoftarch.kozkincsker.ui.mission_start

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import javax.inject.Inject

class MissionStartPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor
) {
    suspend fun onStartSession(session: Session, asModerator: Boolean): String? = withIOContext {
        return@withIOContext sessionInteractor.startSession(session, asModerator)
    }
}