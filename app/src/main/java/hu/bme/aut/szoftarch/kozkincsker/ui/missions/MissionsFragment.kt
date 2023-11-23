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
import hu.bme.aut.szoftarch.kozkincsker.ui.session_player.SessionPlayerFragment
import hu.bme.aut.szoftarch.kozkincsker.views.MissionsView
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class MissionsFragment : RainbowCakeFragment<MissionsViewState, MissionsViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

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
                        onJoinWithCode = ::onJoinWithCode,
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
        if(session != null)
            navigator?.add(SessionPlayerFragment.newInstance(session))
    }

    private fun onModifyMission(mission: Mission) {
        val designer = User()
        navigator?.add(NewMissionFragment.newInstance(designer, mission))
    }

    private fun onDeleteMission(mission: Mission) {
        viewModel.deleteMission(mission)
    }

    private fun onAddMission() {
        val designer = User()
        navigator?.add(NewMissionFragment.newInstance(designer))
    }

    private fun onItemClicked(mission: Mission) {
        navigator?.add(MissionStartFragment.newInstance(mission))
    }

    private fun onSessionClicked(session: Session) {
        navigator?.add(SessionPlayerFragment.newInstance(session))
    }
}