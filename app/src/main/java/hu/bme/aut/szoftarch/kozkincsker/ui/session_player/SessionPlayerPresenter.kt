package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import javax.inject.Inject

class SessionPlayerPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor,
    private val userInteractor: UserInteractor
) {
    suspend fun getMissionFromSession(session: Session) : Mission? = withIOContext {
        return@withIOContext sessionInteractor.getMissionFromSession(session)
    }

    suspend fun getDesignerFromMission(mission: Mission): User? = withIOContext {
        return@withIOContext sessionInteractor.getDesignerFromMission(mission)
    }
    suspend fun getCurrentUser(): User? = withIOContext {
        userInteractor.getCurrentUser()
    }
}