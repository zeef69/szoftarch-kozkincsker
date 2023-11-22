package hu.bme.aut.szoftarch.kozkincsker.ui.task

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
import hu.bme.aut.szoftarch.kozkincsker.views.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class TaskFragment : RainbowCakeFragment<TaskViewState, TaskViewModel>(){

    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val TASK = "TASK"

        fun newInstance(task : Task): TaskFragment {
            return TaskFragment().applyArgs {
                putParcelable(TASK, task)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.load(
            arguments?.getParcelable(TASK)!!
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
                        onSaveClicked = ::onSaveClicked,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onSaveClicked() {
        //viewModel.setTaskSolution(TaskSolution())
    }
}