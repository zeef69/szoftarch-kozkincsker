package hu.bme.aut.szoftarch.kozkincsker.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.enums.LevelType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Session(
    session: Session?,
    mission: Mission?,
    designer: User?,
    user: User?,
    //levels: MutableList<Level>,
    taskSolutionsAndTasks: List<Pair<TaskSolution, Task>> = emptyList(),
    onTaskClicked: (Task, Session, User) -> Unit,
    onRateSession: (Session, Int) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val unknown = stringResource(R.string.value_unknown)
    val missionDeleted = stringResource(R.string.mission_deleted_message)

    val levels: MutableList<Level> = emptyList<Level>().toMutableList()
    if(mission != null)
        levels.addAll(mission.levelList)

    var lastLevelTrue by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Session") },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(imageVector  = Icons.Filled.ArrowBack, null)
                    },
                    onClick = onBackClick
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(0.1f, false)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ){

                    Text(
                        buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic))
                            append("Creator: ")

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                if((mission?.designerId != null) && (designer?.id == mission.designerId)) {
                                    append(designer?.name)
                                }
                                else{
                                    append(unknown)
                                }
                            }
                            pop()
                        },
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .weight(0.4f, false)
                    )

                Text(
                    buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 14.sp))
                        withStyle(
                            style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Starting date: ")
                        }
                        append(session?.startDate?.let {
                            SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH).format(
                                it
                            )
                        })
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .weight(0.4f, false)
                )
            }
            Text(
                buildAnnotatedString {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                    withStyle(
                        style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("Mission: ")
                    }
                    append(mission?.name ?: unknown)
                    pop()
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
                    withStyle(
                        style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("\nSession name: ")
                    }
                    append(session?.name)
                    pop()
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize=14.sp)) {
                        append(mission?.description ?: missionDeleted)
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
            )
        }
        if(mission != null){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp, 12.dp, 12.dp, 25.dp)
                    .weight(0.6f, true)
            ) {
                if(levels.isNotEmpty())
                    LazyColumn(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxSize()
                    ) {
                        itemsIndexed(levels) { index, level ->
                            if((index > 0 && levels[index-1].showNextLevel) || index == 0) {
                                var solutionsInLevel = 0
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(IntrinsicSize.Min)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .height(IntrinsicSize.Min)
                                            .padding(all = 5.dp)
                                            .fillMaxWidth()
                                            .weight(0.85f, true)
                                    ) {
                                        for(task in level.taskList) {
                                            Log.i("task", task.toString())
                                            var solution: TaskSolution? = null
                                            for(taskSolutionAndTask in taskSolutionsAndTasks) {
                                                if(task.id == taskSolutionAndTask.second.id) {
                                                    solutionsInLevel++
                                                    solution = taskSolutionAndTask.first
                                                    Log.i("solution", task.id + " " + taskSolutionAndTask.second.id)
                                                }
                                            }

                                            //if((level.levelType == LevelType.MaxOneTaskInLevel && solutionsInLevel > 0 && solution != null) || level.levelType == LevelType.MinOneTaskInLevel || level.levelType == LevelType.AllTaskInLevel)
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(IntrinsicSize.Max)
                                                    .background(CardBackGround)
                                                    .padding(5.dp, 5.dp, 5.dp, 5.dp)
                                                    .weight(1.0f, true)
                                                    .clickable {
                                                        if (session != null && user != null) {
                                                            onTaskClicked(task, session, user)
                                                        }
                                                    }
                                            ) {
                                                Text(
                                                    text = task.title, color = Black, fontSize = 18.sp, modifier = Modifier
                                                        .padding(all = 2.dp)
                                                        .weight(0.6f, true)
                                                )
                                                if(solution != null && !solution!!.checked && !task.taskType.checkable)
                                                    Icon(imageVector  = Icons.Filled.MoreHoriz, "", modifier = Modifier.weight(0.4f, true))
                                                else if(solution != null && solution!!.checked && solution!!.correct)
                                                    Icon(imageVector  = Icons.Filled.Done, "", modifier = Modifier.weight(0.4f, true))
                                                else if(solution != null && solution!!.checked && !solution!!.correct)
                                                    Icon(imageVector  = Icons.Filled.Close, "", modifier = Modifier.weight(0.4f, true))
                                            }
                                            Spacer(modifier = Modifier.width(5.dp))
                                            solution = null
                                        }
                                    }
                                }
                                if(!level.showNextLevel && level.levelType == LevelType.AllTaskInLevel && solutionsInLevel == level.taskList.size) {
                                    level.showNextLevel = true
                                    if(index == levels.size-1)
                                        lastLevelTrue = true
                                }
                                else if(!level.showNextLevel && level.levelType == LevelType.MinOneTaskInLevel && solutionsInLevel > 0) {
                                    level.showNextLevel = true
                                    Log.i("oksa", level.toString())
                                    if(index == levels.size-1)
                                        lastLevelTrue = true
                                }
                                else if(!level.showNextLevel && level.levelType == LevelType.MaxOneTaskInLevel && solutionsInLevel > 0) {
                                    level.showNextLevel = true
                                    if(index == levels.size-1)
                                        lastLevelTrue = true
                                }
                            }
                        }
                    }
            }
        }
        if(lastLevelTrue) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.1f, true)
            ) {
                Button(
                    onClick = {
                        if (session != null) {
                            var score = 0
                            for(level in levels) {
                                if(level.levelType == LevelType.MaxOneTaskInLevel) {
                                    var counter = 0
                                    for (task in level.taskList) {
                                        for (taskSolutionAndTask in taskSolutionsAndTasks) {
                                            if (task.id == taskSolutionAndTask.second.id && taskSolutionAndTask.first.checked && taskSolutionAndTask.first.correct) {
                                                if(counter == 0)
                                                    score += task.score
                                                counter++
                                            }
                                        }
                                    }
                                }
                                else {
                                    for (task in level.taskList) {
                                        for (taskSolutionAndTask in taskSolutionsAndTasks) {
                                            if (task.id == taskSolutionAndTask.second.id && taskSolutionAndTask.first.checked && taskSolutionAndTask.first.correct) {
                                                score += task.score
                                            }
                                        }
                                    }
                                }
                            }
                            onRateSession(session, score)
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10),
                ) {
                    Text("Rate session")
                }
            }
        }
    }
}


/*@Preview
@Composable
fun SessionPreview() {
    val designer = User(
        id="user_id_12345",
        name="reka_teszt"
    )
    val mission = Mission(
        id = "mission_id_9876",
        designerId = designer.id,
        name = "View teszt mission",
        description = "Ez egy vies teszt leírása. A nézet teszteléséhez és organikusabbá tételéhez szükséges. Nem baj ha nagyon hosszú, a lényeg, hogy lehessen látni, hogy jelenik meg hosszan."
    )
    val player1 = User(
        id="player1_id_125",
        name="player1_teszt"
    )

    val player2 = User(
        id="player2_id_45",
        name="player2_teszt"
    )
    val players = mutableListOf(player1, player2)
    val session = Session(
        id = "sessin_id_9876",
        name = "ez a sessin name",
        missionId = "mission_id_9876",
        playerIds = mutableListOf(player1.id, player2.id),
        startDate = Date(),
        accessCode = "ALMA123"
    )

    val level1 = Level()
    val level2 = Level()
    val level3 = Level()
    val task1 = Task()
    val task2 = Task()
    val task3 = Task()
    task1.title = "Task1"
    task2.title = "Task2"
    task3.title = "Task3"
    level1.taskList = mutableListOf(task1, task2)
    level2.taskList = mutableListOf(task1)
    level3.taskList = mutableListOf(task1, task2, task3)
    mission.levelList = mutableListOf(level1, level2, level3)
    fun onTaskClicked(t:Task, s:Session){}
    Session(
        session = session,
        mission = mission,
        designer = designer,
        onTaskClicked = ::onTaskClicked,
    )*/
//}