package hu.bme.aut.szoftarch.kozkincsker.ui.main

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
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.views.Mission
import hu.bme.aut.szoftarch.kozkincsker.views.ModeratorPlayerList
import hu.bme.aut.szoftarch.kozkincsker.views.NewMission
import hu.bme.aut.szoftarch.kozkincsker.views.Rating
import hu.bme.aut.szoftarch.kozkincsker.views.Session
import hu.bme.aut.szoftarch.kozkincsker.views.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class MainFragment: RainbowCakeFragment<MainViewState, MainViewModel>() {
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

    override fun render(viewState: MainViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mission = Mission()
                    val level1 = Level()
                    val level2 = Level()
                    val level3 = Level()
                    val task1 = Task()
                    val task2 = Task()
                    val task3 = Task()
                    val feedback = Feedback()
                    val user = User()
                    val user2 = User()
                    val session = Session()
                    task1.title = "Task1"
                    task2.title = "Task2"
                    task3.title = "Task3"
                    level1.taskList = mutableListOf(task1, task2)
                    level2.taskList = mutableListOf(task1)
                    level3.taskList = mutableListOf(task1, task2, task3)
                    mission.levelList = mutableListOf(level1, level2, level3)
                    feedback.comment = "Legjobb"
                    feedback.stars = 5.0
                    mission.feedbacks = mutableListOf(feedback)
                    mission.name = "Mission"
                    user.name = "Dizájnoló"
                    user2.name = "Jatekos"
                    mission.designer = user
                    session.mission = mission
                    session.players = mutableListOf(user, user2)

                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is MainContent -> ModeratorPlayerList(
                            session = session,
                            onUserClicked = ::onUserClicked,
                            onBackClick = { navigator?.pop() }
                        )


                        /*Rating(
                            session = session,
                            onSaveClicked = ::onSaveClicked,
                            onBackClick = { navigator?.pop() }
                        )*/

                        /*Task(
                            task = task1,
                            onSaveClicked = ::onSaveClicked,
                            onBackClick = { navigator?.pop() }
                        )*/

                        /*Session(
                            session = session,
                            onTaskClicked = ::onTaskClicked,
                            onBackClick = { navigator?.pop() }
                        )*/

                        /*Mission(
                            mission = mission,
                            onStartSession = ::onStartSession,
                            onBackClick = { navigator?.pop() }
                        )*/

                        /*NewTask(
                            task = task1,
                            onSaveClick = ::onSaveTask,
                            onDeleteClick = ::onDeleteTask,
                            onBackClick = { navigator?.pop() }
                        )*/

                        /*NewMission(
                            mission = mission,
                            onNewTask = {},
                            onTaskClicked = {},
                            onPostClick = ::onSaveMission,
                            onSaveClick = ::onSaveMission,
                            onBackClick = { navigator?.pop() }
                        )*/
                    }.exhaustive
                }
            }
        }
    }

    private fun onSaveMission(mission: Mission) {
        //viewModel.onSave()
    }

    private fun onSaveTask(task: Task) {
        //viewModel.onSave()
        //navigator?.pop()
    }

    private fun onDeleteTask(task: Task) {
        //viewModel.onDelete()
        //navigator?.pop()
    }

    private fun onStartSession(session: Session) {
        //viewModel.startSession()
    }

    private fun onTaskClicked(task: Task) {
        //viewModel.onTaskClicked()
    }

    private fun onSaveClicked() {

    }

    private fun onSaveClicked(feedback: Feedback) {

    }

    private fun onUserClicked(user: User) {

    }
}