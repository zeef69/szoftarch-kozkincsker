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
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.ui.new_task.NewTaskFragment
import hu.bme.aut.szoftarch.kozkincsker.views.NewMission
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class NewMissionFragment : RainbowCakeFragment<NewMissionViewState, NewMissionViewModel>(){
    override fun provideViewModel() = getViewModelFromFactory()
    companion object {
        private const val NEW_MISSION = "NEW_MISSION"
        private const val ADD_ELEMENT = "ADD_ELEMENT"

        fun newInstance(): NewMissionFragment {
            return NewMissionFragment().applyArgs {
                putParcelable(NEW_MISSION, Mission())
            }
        }

        fun newInstance(originalMission: Mission): NewMissionFragment {
            return NewMissionFragment().applyArgs {
                putParcelable(NEW_MISSION, originalMission)
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.load(
            arguments?.getParcelable(NEW_MISSION)!!
        )
        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: NewMissionViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is NewMissionContent -> NewMission(
                            mission = viewState.mission,
                            onNewTask = ::onNewTask,
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

    private fun onNewTask(mission:Mission, level: Level){
        var newTask = Task()
        level.taskList.add(newTask)
        navigator?.add(NewTaskFragment.newInstance(mission,newTask))
        //navigator?.replace(NewTaskFragment.newInstance(mission, newTask))
        //newTask.title = "task"+level.taskList.size.toString()

    }
}