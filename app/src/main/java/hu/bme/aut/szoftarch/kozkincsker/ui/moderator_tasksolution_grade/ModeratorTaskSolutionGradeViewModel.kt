package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_grade

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ModeratorTaskSolutionGradeViewModel @Inject constructor(private val moderatorTaskSolutionGradePresenter: ModeratorTaskSolutionGradePresenter) : RainbowCakeViewModel<ModeratorTaskSolutionGradeViewState>(
    Loading
) {

    fun load(taskSolution: TaskSolution, task: Task) {
        viewState = ModeratorTaskSolutionGradeContent(false, task, taskSolution)
    }

    fun onTaskSolutionGraded(taskSolution: TaskSolution, grade: Boolean) = execute {
        moderatorTaskSolutionGradePresenter.onTaskSolutionGraded(taskSolution, grade)
    }

    fun onDownloadImage(pathString: String): String = runBlocking {
        return@runBlocking moderatorTaskSolutionGradePresenter.onDownloadImage(pathString)
    }
}