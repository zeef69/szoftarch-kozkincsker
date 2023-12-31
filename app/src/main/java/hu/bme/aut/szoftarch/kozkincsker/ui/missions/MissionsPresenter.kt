package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.MissionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MissionsPresenter @Inject constructor(
    private val missionInteractor: MissionInteractor,
    private val userInteractor: UserInteractor,
    private val sessionInteractor: SessionInteractor
) {
    suspend fun addListener(): Flow<List<Mission>> = withIOContext {
        missionInteractor.getMissionsListener()
    }

    suspend fun getId(): String? = withIOContext {
        return@withIOContext userInteractor.getCurrentUser()?.id
    }

    suspend fun getUser(): User? = withIOContext {
        userInteractor.getCurrentUser()
    }

    suspend fun deleteMission(mission: Mission) = withIOContext {
        missionInteractor.deleteMission(mission)
    }

    suspend fun joinWithCode(code: String): Session? = withIOContext {
        return@withIOContext sessionInteractor.joinWithCode(code)
    }

    suspend fun joinPrivateGame(code: String): Mission? = withIOContext {
        return@withIOContext missionInteractor.joinPrivateGame(code)
    }

    suspend fun getPlayingOrModeratedSessionsFromUser(id: String?): Flow<List<Session>> = withIOContext {
        sessionInteractor.getPlayingOrModeratedSessionsFromUser(id)
    }
}