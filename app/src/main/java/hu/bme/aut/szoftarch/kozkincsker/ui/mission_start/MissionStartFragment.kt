package hu.bme.aut.szoftarch.kozkincsker.ui.mission_start

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
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator.SessionModeratorFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.session_player.SessionPlayerFragment
import hu.bme.aut.szoftarch.kozkincsker.views.Mission
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class MissionStartFragment : RainbowCakeFragment<MissionViewState, MissionStartViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_MISSION = "MISSION"

        fun newInstance(mission: Mission): MissionStartFragment {
            return MissionStartFragment().applyArgs {
                putParcelable(EXTRA_MISSION, mission)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setMission(
            arguments?.getParcelable(EXTRA_MISSION)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: MissionViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is MissionContent -> Mission(
                        mission = viewState.mission,
                        user = viewState.actualUser,
                        designer = viewState.designer,
                        onStartSession = ::onStartSession,
                        onDeleteFeedback = ::onDeleteFeedback,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onStartSession(session: Session, asModerator: Boolean) {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        session.accessCode = (1..6)
            .map { allowedChars.random() }
            .joinToString("")

        val id = viewModel.onStartSession(session, asModerator)
        if(id != null) {
            session.id = id
            if(asModerator)
                navigator?.add(SessionModeratorFragment.newInstance(session))
            else
                navigator?.add(SessionPlayerFragment.newInstance(session))
        }
    }

    private fun onDeleteFeedback(feedback: Feedback, missionId: String) {
        viewModel.deleteFeedback(feedback, missionId)
    }
}