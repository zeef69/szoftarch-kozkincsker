package hu.bme.aut.szoftarch.kozkincsker.ui.mission_start

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.MissionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import javax.inject.Inject

class MissionStartPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor, private val userInteractor: UserInteractor, private val missionInteractor: MissionInteractor
) {
    suspend fun onStartSession(session: Session, asModerator: Boolean): String? = withIOContext {
        return@withIOContext sessionInteractor.startSession(session, asModerator)
    }

    suspend fun getUser(): User? = withIOContext {
        return@withIOContext userInteractor.getCurrentUser()
    }

    suspend fun getDesigner(designerId: String?): User? = withIOContext {
        return@withIOContext userInteractor.getUserFromId(designerId)
    }

    suspend fun deleteFeedback(feedback: Feedback, missionId: String) = withIOContext {
        missionInteractor.deleteFeedback(feedback, missionId)
    }
}