package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_grade

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.domain.ImageInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.TaskSolutionInteractor
import javax.inject.Inject

class ModeratorTaskSolutionGradePresenter @Inject constructor(
    private val taskSolutionInteractor: TaskSolutionInteractor,
    private val imageInteractor: ImageInteractor
) {
    suspend fun onTaskSolutionGraded(taskSolution: TaskSolution, grade: Boolean) = withIOContext {
        taskSolutionInteractor.onTaskSolutionGraded(taskSolution, grade)
    }

    suspend fun onDownloadImage(pathString: String){
        imageInteractor.downloadImageFromStorage(pathString)
    }
}