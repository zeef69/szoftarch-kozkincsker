package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Composable
fun Mission(
    mission: Mission,
    designer: User? = null,
    feedbacks: MutableList<Feedback> = ArrayList(),
    onStartSession: (Session) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var switchState by remember { mutableIntStateOf(0) }
    var checkedModerator by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Mission") },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(imageVector = Icons.Filled.ArrowBack, null)
                    },
                    onClick = onBackClick
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        append(mission.name)
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(mission.description)
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
            )

            if (mission.designerId != null && designer?.id == mission.designerId)
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(designer!!.name)
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

            SegmentedControl(
                listOf("Start Session", "Statistics"),
                switchState
            ) { switchState = it }

            if(switchState == 0) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("As Moderator:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                Switch(
                    checked = checkedModerator and mission.isPlayableWithoutModerator,
                    enabled = mission.isPlayableWithoutModerator,
                    onCheckedChange = {
                        checkedModerator = it
                    },

                )
            }
            else {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Stats:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Ratings:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxSize()
                ) {
                    itemsIndexed(feedbacks) { _, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                                .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        ) {
                            Text(
                                text = item.comment,
                                color = Color.Black,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(all = 2.dp).weight(0.6f, true)
                            )
                            Icon(
                                imageVector  = Icons.Filled.StarRate, null,
                                modifier = Modifier
                                    .padding(vertical = 2.dp, horizontal = 2.dp)
                                    .fillMaxWidth()
                                    .weight(0.4f, true),
                            )
                        }
                    }
                }
            }
        }
        if(switchState == 0)
            Column() {
                Button(
                    onClick = {
                        val newSession = Session()
                        newSession.missionId = mission.id
                        onStartSession(newSession)
                    },
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10),
                ) {
                    Text("Start")
                }
            }
    }
}

@Composable
@Preview
fun MissionPreview(){
    val mission = Mission()
    val level1 = Level()
    val level2 = Level()
    val level3 = Level()
    val task1_1 = hu.bme.aut.szoftarch.kozkincsker.data.model.Task()
    val task1_2 = hu.bme.aut.szoftarch.kozkincsker.data.model.Task()
    val task2 = hu.bme.aut.szoftarch.kozkincsker.data.model.Task()
    val task3_1 = hu.bme.aut.szoftarch.kozkincsker.data.model.Task()
    val task3_2 = hu.bme.aut.szoftarch.kozkincsker.data.model.Task()
    val task3_3 = hu.bme.aut.szoftarch.kozkincsker.data.model.Task()
    val feedback = Feedback()
    val user = User()
    val user2 = User()
    val session = Session()
    task1_1.title = "Task1.1"
    task1_2.title = "Task1.2"
    task2.title = "Task2"
    task3_1.title = "Task3.1"
    task3_2.title = "Task3.2"
    task3_3.title = "Task3.3"
    level1.taskList = mutableListOf(task1_1, task1_2)
    level2.taskList = mutableListOf(task2)
    level3.taskList = mutableListOf(task3_1, task3_2, task3_3)
    mission.levelList = mutableListOf(level1, level2, level3)
    feedback.comment = "Legjobb"
    feedback.stars = 5.0
    mission.feedbackIds = mutableListOf(feedback.id)
    var feedbacks = mutableListOf(feedback)
    mission.name = "MyMission"
    mission.description ="A new mission. Have fun!"
    mission.isPlayableWithoutModerator=false
    user.name = "Dizájnoló"
    user2.name = "Jatekos"
    mission.designerId = user.id
    session.missionId = mission.id
    session.playerIds = mutableListOf(user.id, user2.id)

    Mission(
        mission = mission,
        designer = user,
        feedbacks = feedbacks
    )
}