package hu.bme.aut.szoftarch.kozkincsker.ui.new_task

import hu.bme.aut.szoftarch.kozkincsker.data.model.Task


sealed class NewTaskViewState

object Loading : NewTaskViewState()

data class NewTaskContent(
    var newTask: Task? = null,
    var isLoading: Boolean = true
) : NewTaskViewState()
