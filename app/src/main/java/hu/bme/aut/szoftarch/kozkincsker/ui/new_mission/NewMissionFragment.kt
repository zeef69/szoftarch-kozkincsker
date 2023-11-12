package hu.bme.aut.szoftarch.kozkincsker.ui.new_mission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.views.NewMission
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class NewMissionFragment : RainbowCakeFragment<NewMissionViewState, NewMissionViewModel>(){
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
        viewModel.load()
    }

    override fun render(viewState: NewMissionViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mission = Mission()
                    mission.name = "MyMission"
                    mission.description ="A new mission. Have fun!"
                    mission.isPlayableWithoutModerator=true
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is NewMissionContent -> NewMission(
                            mission = mission,
                            onNewTask = {},
                            onTaskClicked = {},
                            onPostClick = ::onSaveMission,
                            onSaveClick = ::onSaveMission,
                            onBackClick = {}
                        )
                    }
                }
            }
        }
    }

    private fun onSaveMission(mission: Mission) {
        viewModel.uploadMission(mission)
    }
}