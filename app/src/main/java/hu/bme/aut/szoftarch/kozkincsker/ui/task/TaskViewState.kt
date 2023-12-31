package hu.bme.aut.szoftarch.kozkincsker.ui.task

import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task

sealed class TaskViewState

object Loading : TaskViewState()

data class TaskContent(
    var task: Task,
    var session: Session,
    var loading : Boolean = true
) : TaskViewState()