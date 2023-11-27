package hu.bme.aut.szoftarch.kozkincsker.ui.session_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.ui.rating.RatingFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.task.TaskFragment
import hu.bme.aut.szoftarch.kozkincsker.views.Session
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class SessionPlayerFragment : RainbowCakeFragment<SessionPlayerViewState, SessionPlayerViewModel>(){

    override fun provideViewModel() = getViewModelFromFactory()
    companion object {
        private const val SESSION = "SESSION"

        fun newInstance(session: Session): SessionPlayerFragment {
            return SessionPlayerFragment().applyArgs {
                putParcelable(SESSION, session)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.load(
            arguments?.getParcelable(SESSION)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }
    override fun render(viewState: SessionPlayerViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is SessionPlayerContent -> {
                        val levels = remember { mutableStateListOf<Level>() }
                        if(viewState.mission != null)
                            for(level in viewState.mission!!.levelList)
                                levels.add(level)
                        Session(
                            session = viewState.session,
                            mission = viewState.mission,
                            designer = viewState.designer,
                            user = viewState.user,
                            levels = levels,
                            taskSolutionsAndTasks = viewState.taskSolutionsAndTasks,
                            onBackClick = { navigator?.pop() },
                            onTaskClicked = ::onTaskClicked,
                            onRateSession = ::onRateSession
                        )
                    }
                }.exhaustive
            }
        }
    }

    private fun onTaskClicked(task : Task, session: Session, actualUser: User) {
        navigator?.add(TaskFragment.newInstance(task, session, actualUser))
    }

    private fun onRateSession(session: Session, score: Int) {
        navigator?.add(RatingFragment.newInstance(session, score))
    }
}