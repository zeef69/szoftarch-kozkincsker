package hu.bme.aut.szoftarch.kozkincsker.ui.new_task

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task

class NewTaskViewModel : RainbowCakeViewModel<NewTaskViewState>(Loading) {
    fun createNewTask(task: Task) = execute {
        viewState = NewTaskContent(
            newTask = task,
            isLoading = false
        )
    }

}