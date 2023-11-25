package hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionModeratorPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor
) {
    suspend fun getMissionFromSession(session: Session) : Mission? = withIOContext {
        return@withIOContext sessionInteractor.getMissionFromSession(session)
    }

    suspend fun getDesignerFromMission(mission: Mission): User? = withIOContext {
        return@withIOContext sessionInteractor.getDesignerFromMission(mission)
    }

    suspend fun addListener(session: Session): Flow<List<User>> = withIOContext {
        sessionInteractor.getPlayersFromSessionListener(session)
    }
}