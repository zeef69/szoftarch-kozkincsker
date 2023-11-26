package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_list

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.domain.ImageInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.SessionInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.TaskSolutionInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ModeratorTaskSolutionListPresenter@Inject constructor(
    private val taskSolutionInteractor: TaskSolutionInteractor,
    private val sessionInteractor: SessionInteractor
){
    suspend fun addListener(sessionId: String, playerId: String): Flow<List<TaskSolution>> = withIOContext {
        taskSolutionInteractor.getTaskSolutionsListener(sessionId, playerId)
    }

    suspend fun getMission(sessionId: String): Mission? = withIOContext {
        return@withIOContext sessionInteractor.getMissionFromSessionId(sessionId)
    }
}