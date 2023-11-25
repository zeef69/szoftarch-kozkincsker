package hu.bme.aut.szoftarch.kozkincsker.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.views.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class TaskFragment : RainbowCakeFragment<TaskViewState, TaskViewModel>(){

    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val TASK = "TASK"
        private const val SESSION = "TASK_SESSION"

        fun newInstance(task : Task, session: Session): TaskFragment {
            return TaskFragment().applyArgs {
                putParcelable(TASK, task)
                putParcelable(SESSION, session)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.load(
            arguments?.getParcelable(TASK)!!,
            arguments?.getParcelable(SESSION)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }
    override fun render(viewState: TaskViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is TaskContent -> Task(
                        task = viewState.task,
                        session = viewState.session,
                        onSaveClicked = ::onSaveClicked,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onSaveClicked(task: Task, taskSolution: TaskSolution) {
        if(task.taskType.checkable){
            taskSolution.correct = task.taskType.solutionCheck(task.answers, taskSolution.userAnswer)
            taskSolution.checked = true
        }
        Log.i("taskSolution", taskSolution.toString())
        val id = viewModel.setTaskSolution(taskSolution)
        if (id != null) {
            Log.i("taskSolutionID", id)
        }
        navigator?.pop()
    }
}