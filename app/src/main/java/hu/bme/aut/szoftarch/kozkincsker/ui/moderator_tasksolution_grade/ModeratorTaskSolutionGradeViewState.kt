package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_grade

import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution

sealed class ModeratorTaskSolutionGradeViewState

data object Loading : ModeratorTaskSolutionGradeViewState()

data class ModeratorTaskSolutionGradeContent(
    var isLoading: Boolean = false,
    var task: Task? = null,
    var taskSolution: TaskSolution? = null
) : ModeratorTaskSolutionGradeViewState()