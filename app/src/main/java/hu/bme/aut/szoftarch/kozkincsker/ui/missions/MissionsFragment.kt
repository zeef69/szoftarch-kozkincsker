package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.ui.mission_start.MissionStartFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.new_mission.NewMissionFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator.SessionModeratorFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.session_player.SessionPlayerFragment
import hu.bme.aut.szoftarch.kozkincsker.views.MissionsView
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class MissionsFragment : RainbowCakeFragment<MissionsViewState, MissionsViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()
    private lateinit var actualUser: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addListener()
        actualUser = viewModel.getDesigner()!!
    }

    override fun render(viewState: MissionsViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is MissionsContent -> MissionsView(
                        missions = viewState.missions,
                        sessions = viewState.sessions,
                        id = viewState.id,
                        user = actualUser,
                        onJoinWithCode = ::onJoinWithCode,
                        onPrivateGameCode = ::onPrivateGameCode,
                        onModifyMission = ::onModifyMission,
                        onDeleteMission = ::onDeleteMission,
                        onAddMission = ::onAddMission,
                        onItemClicked = ::onItemClicked,
                        onSessionClicked = ::onSessionClicked
                    )
                }.exhaustive
            }
        }
    }

    private fun onJoinWithCode(code: String) {
        val session = viewModel.joinWithCode(code)
        if(session != null){
            actualUser.currentSessionIds.add(session.id)
            navigator?.add(SessionPlayerFragment.newInstance(session))
        }
    }

    private fun onPrivateGameCode(code: String): Mission? {
        return viewModel.joinPrivateGame(code)
    }

    private fun onModifyMission(mission: Mission) {
        val designer = viewModel.getDesigner()
        if(designer != null)
            navigator?.add(NewMissionFragment.newInstance(designer, mission))
    }

    private fun onDeleteMission(mission: Mission) {
        viewModel.deleteMission(mission)
    }

    private fun onAddMission() {
        val designer = viewModel.getDesigner()
        if(designer != null)
            navigator?.add(NewMissionFragment.newInstance(designer))
    }

    private fun onItemClicked(mission: Mission) {
        navigator?.add(MissionStartFragment.newInstance(mission))
    }

    private fun onSessionClicked(session: Session) {
        if(session.moderator == actualUser.id)
            navigator?.add(SessionModeratorFragment.newInstance(session))
        else navigator?.add(SessionPlayerFragment.newInstance(session))
    }
}