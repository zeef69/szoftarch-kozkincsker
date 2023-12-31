package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class SessionPlayerViewState

object Loading : SessionPlayerViewState()

data class SessionPlayerContent(
    var session : Session? = null,
    var mission : Mission? = null,
    var designer : User? = null,
    var user: User? = null,
    var taskSolutionsAndTasks: List<Pair<TaskSolution, Task>> = emptyList(),
    val loading : Boolean = false
) : SessionPlayerViewState()