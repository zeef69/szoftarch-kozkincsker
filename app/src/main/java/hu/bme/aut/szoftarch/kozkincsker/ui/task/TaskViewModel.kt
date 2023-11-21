package hu.bme.aut.szoftarch.kozkincsker.ui.task

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val taskPresenter: TaskPresenter
) : RainbowCakeViewModel<TaskViewState>(Loading) {
    fun load(task: Task) = execute {
        viewState = TaskContent(
            task = task,
            loading = false)
    }

    suspend fun setTaskSolution(solution: TaskSolution) {
        taskPresenter.setTaskSolution(solution)
    }
}