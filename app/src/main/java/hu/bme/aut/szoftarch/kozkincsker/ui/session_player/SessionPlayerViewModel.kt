package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import android.util.Log
import androidx.lifecycle.viewModelScope
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SessionPlayerViewModel @Inject constructor(
    private val sessionPlayerPresenter: SessionPlayerPresenter
) : RainbowCakeViewModel<SessionPlayerViewState>(Loading) {
    fun load(session: Session) = execute {
        Log.i("session", session.toString())
        val mission = sessionPlayerPresenter.getMissionFromSession(session)
        Log.i("mission", mission.toString())
        val designer = mission?.let { sessionPlayerPresenter.getDesignerFromMission(it) }
        Log.i("designer", designer.toString())
        val user = sessionPlayerPresenter.getCurrentUser()
        /*viewState = SessionPlayerContent(
            session = session,
            mission = mission,
            designer = designer,
            user = user,
            loading = false
        )*/


        if(user != null)
            viewModelScope.launch {
                sessionPlayerPresenter.addListener(session.id, user.id).collect {
                    viewState = SessionPlayerContent(
                        loading = true
                    )
                    val taskSolutions = it
                    val tasks: MutableList<Task> = emptyList<Task>().toMutableList()
                    if(mission != null) {
                        for(level in mission.levelList)
                            for(task in level.taskList)
                                tasks.add(task)
                    }
                    val taskSolutionsAndTasks: MutableList<Pair<TaskSolution, Task>> = emptyList<Pair<TaskSolution, Task>>().toMutableList()
                    for(taskSolution in taskSolutions)
                        for(task in tasks)
                            if(taskSolution.taskId == task.id)
                                taskSolutionsAndTasks.add(Pair(taskSolution, task))

                    viewState = SessionPlayerContent(
                        taskSolutionsAndTasks = taskSolutionsAndTasks,
                        session = session,
                        mission = mission,
                        designer = designer,
                        user = user,
                        loading = false
                    )
                }
            }
    }

    fun getCurrentUser(): User? = runBlocking {
        return@runBlocking sessionPlayerPresenter.getCurrentUser()
    }
}