package hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_list

import android.net.Uri
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
import hu.bme.aut.szoftarch.kozkincsker.ui.moderator_tasksolution_grade.ModeratorTaskSolutionGradeFragment
import hu.bme.aut.szoftarch.kozkincsker.views.ModeratorTaskSolutionList
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class ModeratorTaskSolutionListFragment : RainbowCakeFragment<ModeratorTaskSolutionListViewState, ModeratorTaskSolutionListViewModel>(){
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val SESSION = "SESSION"
        private const val PLAYER = "PLAYER"

        fun newInstance(session: String, player: String): ModeratorTaskSolutionListFragment {
            return ModeratorTaskSolutionListFragment().applyArgs {
                putString(SESSION, session)
                putString(PLAYER, player)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.addListener(
            arguments?.getString(SESSION)!!,
            arguments?.getString(PLAYER)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: ModeratorTaskSolutionListViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is ModeratorTaskSolutionListContent -> ModeratorTaskSolutionList(
                        taskSolutionsAndTasks = viewState.taskSolutionsAndTasks,
                        onTaskSolutionClicked = ::onTaskSolutionClicked,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onTaskSolutionClicked(taskSolution: TaskSolution, task: Task) {
        navigator?.add(ModeratorTaskSolutionGradeFragment.newInstance(taskSolution, task))
    }
}