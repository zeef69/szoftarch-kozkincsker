package hu.bme.aut.szoftarch.kozkincsker.ui.task

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskPresenter: TaskPresenter
) : RainbowCakeViewModel<TaskViewState>(Loading) {
    fun load(task: Task, session: Session) = execute {
        viewState = TaskContent(
            task = task,
            session = session,
            loading = false
        )
    }

    fun setTaskSolution(solution: TaskSolution): String? = runBlocking {
        return@runBlocking taskPresenter.setTaskSolution(solution)
    }

    fun onUploadImage(byteArray: ByteArray): String? = runBlocking {
        return@runBlocking taskPresenter.onUploadImage(byteArray)
    }
}