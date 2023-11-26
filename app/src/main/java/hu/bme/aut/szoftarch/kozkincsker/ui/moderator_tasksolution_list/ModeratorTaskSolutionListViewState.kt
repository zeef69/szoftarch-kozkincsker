package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_list

import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution

sealed class ModeratorTaskSolutionListViewState

data object Loading : ModeratorTaskSolutionListViewState()
data class ModeratorTaskSolutionListContent(
    var taskSolutionsAndTasks: List<Pair<TaskSolution, Task>> = emptyList(),
    val isLoading: Boolean = false
) : ModeratorTaskSolutionListViewState()