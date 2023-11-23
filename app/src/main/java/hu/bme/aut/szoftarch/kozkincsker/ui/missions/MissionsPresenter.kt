package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.domain.AuthInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.MissionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MissionsPresenter @Inject constructor(
    private val missionInteractor: MissionInteractor, private val authInteractor: AuthInteractor, private val sessionInteractor: SessionInteractor
) {
    suspend fun addListener(): Flow<List<Mission>> = withIOContext {
        missionInteractor.getMissionsListener()
    }

    suspend fun getUid(): String? = withIOContext {
        authInteractor.getCurrentUser()
    }

    suspend fun getId(): String? = withIOContext {
        authInteractor.getCurrentUserId()
    }

    suspend fun deleteMission(mission: Mission) = withIOContext {
        missionInteractor.deleteMission(mission)
    }

    suspend fun joinWithCode(code: String): Session? = withIOContext {
        return@withIOContext sessionInteractor.joinWithCode(code)
    }

    suspend fun getSessionsFromUser(userId: String): List<Session> = withIOContext {
        sessionInteractor.getSessionsFromUser(userId)
    }
}