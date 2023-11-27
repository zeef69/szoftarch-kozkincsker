package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.TaskSolutionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionPlayerPresenter @Inject constructor(
    private val sessionInteractor: SessionInteractor,
    private val userInteractor: UserInteractor,
    private val taskSolutionInteractor: TaskSolutionInteractor
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

    suspend fun addListener(sessionId: String, playerId: String): Flow<List<TaskSolution>> = withIOContext {
        taskSolutionInteractor.getTaskSolutionsListener(sessionId, playerId)
    }
}