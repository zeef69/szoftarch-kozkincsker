package hu.bme.aut.szoftarch.kozkincsker.ui.task

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.domain.TaskSolutionInteractor
import javax.inject.Inject

class TaskPresenter @Inject constructor(
    private val taskSolutionInteractor: TaskSolutionInteractor
) {
    suspend fun setTaskSolution(solution : TaskSolution): String? = withIOContext {
        return@withIOContext taskSolutionInteractor.setTaskSolution(solution)
    }
}