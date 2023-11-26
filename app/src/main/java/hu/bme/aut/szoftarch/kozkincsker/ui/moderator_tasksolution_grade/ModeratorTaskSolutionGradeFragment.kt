package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_grade

import android.os.Bundle
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
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.views.ModeratorTaskSolutionGrade
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class ModeratorTaskSolutionGradeFragment : RainbowCakeFragment<ModeratorTaskSolutionGradeViewState, ModeratorTaskSolutionGradeViewModel>(){
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val TASK_SOLUTION = "TASK_SOLUTION"
        private const val TASK = "TASK"

        fun newInstance(taskSolution: TaskSolution, task: Task): ModeratorTaskSolutionGradeFragment {
            return ModeratorTaskSolutionGradeFragment().applyArgs {
                putParcelable(TASK_SOLUTION, taskSolution)
                putParcelable(TASK, task)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.load(
            arguments?.getParcelable(TASK_SOLUTION)!!,
            arguments?.getParcelable(TASK)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: ModeratorTaskSolutionGradeViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is ModeratorTaskSolutionGradeContent -> ModeratorTaskSolutionGrade(
                        task = viewState.task,
                        taskSolution = viewState.taskSolution,
                        onTaskSolutionGoodClicked = ::onTaskSolutionGoodClicked,
                        onTaskSolutionWrongClicked = ::onTaskSolutionWrongClicked,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onTaskSolutionGoodClicked(taskSolution: TaskSolution) {
        viewModel.onTaskSolutionGraded(taskSolution, true)
        navigator?.pop()
    }

    private fun onTaskSolutionWrongClicked(taskSolution: TaskSolution) {
        viewModel.onTaskSolutionGraded(taskSolution, false)
        navigator?.pop()
    }
}