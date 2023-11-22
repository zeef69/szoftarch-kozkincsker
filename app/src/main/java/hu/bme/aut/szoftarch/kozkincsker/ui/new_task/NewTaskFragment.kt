package hu.bme.aut.szoftarch.kozkincsker.ui.new_task

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
import co.zsmb.rainbowcake.navigation.popUntil
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.ui.new_mission.NewMissionFragment
import hu.bme.aut.szoftarch.kozkincsker.views.NewTask
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

class NewTaskFragment : RainbowCakeFragment<NewTaskViewState,NewTaskViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()
    private lateinit var originalMission: Mission
    private lateinit var designer: User
    private lateinit var level: Level
    companion object {
        private const val NEW_TASK_DESIGNER = "NEW_TASK_DESIGNER"
        private const val NEW_TASK = "NEW_TASK"
        private const val NEW_TASK_LEVEL = "NEW_TASK_LEVEL"
        private const val EDITED_MISSION = "EDITED_MISSION"
        fun newInstance(designer: User, mission: Mission, level: Level, newTask: Task): NewTaskFragment {
            return NewTaskFragment().applyArgs {
                putParcelable(NEW_TASK_DESIGNER, designer)
                putParcelable(EDITED_MISSION, mission)
                putParcelable(NEW_TASK_LEVEL, level)
                putParcelable(NEW_TASK, newTask)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.createNewTask(
            arguments?.getParcelable(NEW_TASK)!!
        )
        level=arguments?.getParcelable(NEW_TASK_LEVEL)!!
        originalMission = arguments?.getParcelable(EDITED_MISSION)!!
        designer = arguments?.getParcelable(NEW_TASK_DESIGNER)!!
        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: NewTaskViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when(viewState){
                        is Loading -> FullScreenLoading()
                        is NewTaskContent -> viewState.newTask?.let {
                            NewTask(
                                task = it,
                                onSaveNewClick = ::onSaveNewTask,
                                onDeleteClick = ::onDeleteTask,
                                onBackClick = { navigator?.pop() }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onSaveNewTask(task: Task) {
        if(!level.taskList.contains(task))
            level.taskList.add(task)
        if(!originalMission.levelList.contains(level)) originalMission.levelList.add(level)
        navigator?.pop()
        //navigator?.replace(NewMissionFragment.newInstance(designer, originalMission))
    }


    private fun onDeleteTask(task: Task) {
        level.taskList.remove(task)
        if(level.taskList.size==0) originalMission.levelList.remove(level)
        navigator?.pop()
        //navigator?.replace(NewMissionFragment.newInstance(designer, originalMission))
        //navigator?.pop()
    }

    private fun onBackClick(){
        if(level.taskList.size==0) originalMission.levelList.remove(level)
        navigator?.pop()
        //navigator?.replace(NewMissionFragment.newInstance(designer, originalMission))
    }
}