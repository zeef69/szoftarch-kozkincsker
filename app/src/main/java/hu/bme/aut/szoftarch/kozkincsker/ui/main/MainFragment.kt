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
import hu.bme.aut.szoftarch.kozkincsker.views.NewTask
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
                    val newmission = Mission()
                    val level1 = Level()
                    val level2 = Level()
                    val level3 = Level()
                    val task11 = Task()
                    val task12 = Task()
                    val task2 = Task()
                    val task31 = Task()
                    val task32 = Task()
                    val task33 = Task()
                    val feedback = Feedback()
                    val user = User()
                    val user2 = User()
                    val session = Session()
                    task11.title = "Task1.1"
                    task12.title = "Task1.2"
                    task2.title = "Task2"
                    task31.title = "Task3.1"
                    task32.title = "Task3.2"
                    task33.title = "Task3.3"
                    task11.description= "Task1.1 description, long text, ..... Looooooooong, Can you do that? Go to the tower and look around!"
                    level1.taskList = mutableListOf(task11, task12)
                    level2.taskList = mutableListOf(task2)
                    level3.taskList = mutableListOf(task31, task32, task33)
                    mission.levelList = mutableListOf(level1, level2, level3)
                    feedback.comment = "Legjobb"
                    feedback.stars = 5.0
                    feedback.writer = user2
                    feedback.mission = mission
                    mission.feedbacks = mutableListOf(feedback)
                    mission.name = "MyMission"
                    mission.description ="A new mission. Have fun!"
                    mission.isPlayableWithoutModerator=false
                    user.name = "Dizájnoló"
                    user2.name = "Jatekos"
                    mission.designer = user
                    session.mission = mission
                    session.players = mutableListOf(user, user2)

                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is MainContent -> /*ModeratorPlayerList(
                            session = session,
                            onUserClicked = ::onUserClicked,
                            onBackClick = { navigator?.pop() }
                        )*/


                        /*Rating(
                            session = session,
                            onSaveClicked = ::onSaveClicked,
                            onBackClick = { navigator?.pop() }
                        )*/

                        /*Task(
                            task = task11,
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

                        NewTask(
                            task = task11,
                            onSaveClick = ::onSaveTask,
                            onDeleteClick = ::onDeleteTask,
                            onBackClick = { navigator?.pop() }
                        )

                        /*NewMission(
                            mission = newmission,
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