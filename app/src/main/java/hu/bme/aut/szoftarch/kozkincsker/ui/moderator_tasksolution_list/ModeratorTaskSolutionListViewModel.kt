package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_list

import androidx.lifecycle.viewModelScope
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ModeratorTaskSolutionListViewModel @Inject constructor(
    private val moderatorTaskSolutionListPresenter:  ModeratorTaskSolutionListPresenter
) : RainbowCakeViewModel< ModeratorTaskSolutionListViewState>(Loading) {

    fun addListener(sessionId: String, playerId: String) = execute {
        viewModelScope.launch {
            moderatorTaskSolutionListPresenter.addListener(sessionId, playerId).collect {
                viewState = ModeratorTaskSolutionListContent(
                    isLoading = true
                )
                val taskSolutions = it
                val mission = moderatorTaskSolutionListPresenter.getMission(sessionId)
                val tasks: MutableList<Task> = emptyList<Task>().toMutableList()
                if(mission != null) {
                    for(level in mission.levelList)
                        for(task in level.taskList)
                            tasks.add(task)
                }
                val taskSolutionsAndTasks: MutableList<Pair<TaskSolution, Task>> = emptyList<Pair<TaskSolution, Task>>().toMutableList()
                for(taskSolution in taskSolutions)
                    for(task in tasks) {
                        if(taskSolution.taskId == task.id && !task.taskType.checkable && !taskSolution.checked)
                            taskSolutionsAndTasks.add(Pair(taskSolution, task))
                    }

                viewState = ModeratorTaskSolutionListContent(
                    taskSolutionsAndTasks = taskSolutionsAndTasks,
                    isLoading = false
                )
            }
        }
    }
}