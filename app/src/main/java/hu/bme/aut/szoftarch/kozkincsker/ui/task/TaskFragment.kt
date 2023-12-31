package hu.bme.aut.szoftarch.kozkincsker.ui.task


import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.views.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class TaskFragment : RainbowCakeFragment<TaskViewState, TaskViewModel>(){


    override fun provideViewModel() = getViewModelFromFactory()
    private lateinit var actualUser: User
    companion object {
        private const val TASK = "TASK"
        private const val SESSION = "TASK_SESSION"
        private const val ACTUAL_USER = "ACTUAL_USER"

        fun newInstance(task : Task, session: Session, user: User): TaskFragment {
            return TaskFragment().applyArgs {
                putParcelable(TASK, task)
                putParcelable(SESSION, session)
                putParcelable(ACTUAL_USER, user)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.load(
            arguments?.getParcelable(TASK)!!,
            arguments?.getParcelable(SESSION)!!
        )
        actualUser = arguments?.getParcelable(ACTUAL_USER)!!
        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }
    override fun render(viewState: TaskViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is TaskContent -> {
                        val pattern =  '|'
                        val answerListSize = if(viewState.task.taskType == TaskType.ListedAnswer || viewState.task.taskType == TaskType.OrderAnswer) viewState.task.answers.split(pattern)[0].toInt() else 0
                        val choiceAnswerInputList = ArrayList<String> () //remember { mutableStateListOf<String>() }
                        val checkedStateList = remember { mutableStateListOf<Boolean>() }
                        var orderAnswerInputList = remember { mutableStateListOf<String>() }
                        for (i in 0..<answerListSize){
                            if (viewState.task.taskType == TaskType.ListedAnswer && viewState.task.answers.split(pattern).size > 12) {
                                checkedStateList.add(false)
                                choiceAnswerInputList.add(viewState.task.answers.split(pattern)[2 * i + 2])
                            }
                            if (viewState.task.taskType == TaskType.OrderAnswer && viewState.task.answers.split(pattern).size > 6) {
                                orderAnswerInputList.add(viewState.task.answers.split(pattern)[i+7])
                            }
                        }
                        Task(
                            task = viewState.task,
                            session = viewState.session,
                            answerListSize=answerListSize,
                            choiceAnswerInputList=choiceAnswerInputList,
                            checkedStateList=checkedStateList,
                            orderAnswerInputList=orderAnswerInputList,
                            onUploadImage = ::onUploadImage,
                            onSaveClicked = ::onSaveClicked,
                            onBackClick = { navigator?.pop() }
                        )
                    }
                }.exhaustive
            }
        }
    }

    private fun onSaveClicked(task: Task, taskSolution: TaskSolution) {
        if(task.taskType.checkable){
            taskSolution.correct = task.taskType.solutionCheck(task.answers, taskSolution.userAnswer)
            taskSolution.checked = true
        }
        taskSolution.userId = actualUser.id
        Log.i("taskSolution", taskSolution.toString())
        val id = viewModel.setTaskSolution(taskSolution)
        if (id != null) {
            Log.i("taskSolutionID", id)
        }
        navigator?.pop()
    }

    private fun onUploadImage(uri: Uri) : String {
        val byteArray: ByteArray? = context?.contentResolver
            ?.openInputStream(uri)
            ?.use { it.readBytes() }

        var imageNewUri : String? = null
        byteArray?.let{
            imageNewUri = viewModel.onUploadImage(byteArray)
        }
        return imageNewUri ?: ""
    }

}