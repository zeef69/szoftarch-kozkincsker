package hu.bme.aut.szoftarch.kozkincsker.ui.task

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.domain.ImageInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.TaskSolutionInteractor
import javax.inject.Inject

class TaskPresenter @Inject constructor(
    private val taskSolutionInteractor: TaskSolutionInteractor,
    private val imageInteractor: ImageInteractor
) {
    suspend fun setTaskSolution(solution : TaskSolution): String? = withIOContext {
        return@withIOContext taskSolutionInteractor.setTaskSolution(solution)
    }

    suspend fun onUploadImage(byteArray: ByteArray): String? = withIOContext {
        return@withIOContext imageInteractor.uploadImageToStorage(byteArray)
    }
}