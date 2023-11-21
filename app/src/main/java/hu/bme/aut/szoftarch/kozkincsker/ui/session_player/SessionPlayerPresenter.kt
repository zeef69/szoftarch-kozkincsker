package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import javax.inject.Inject

class SessionPlayerPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor
) {
 suspend fun getMissionFromSession(session: Session) : Mission {
     return sessionInteractor.getMissionFromSession(session)
 }

    fun getDesignerFromMission(mission: Mission): User {
        return sessionInteractor.getDesignerFromMission(mission)
    }
}