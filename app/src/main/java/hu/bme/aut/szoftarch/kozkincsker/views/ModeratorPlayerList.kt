package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

@Composable
fun ModeratorPlayerList(
    session: Session,
    mission: Mission,
    designer: User,
    players: MutableList<User> = ArrayList(),
    onUserClicked: (User) -> Unit,
    onBackClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Player List") },
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
                .weight(1f, false)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
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

            if(mission.designerId != null && designer?.id == mission.designerId)
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

            LazyColumn(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(players) { _, item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                            .padding(5.dp, 5.dp, 5.dp, 5.dp)
                            .clickable { onUserClicked(item) }
                    ) {
                        Text(
                            text = item.name,
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(all = 2.dp)
                                .weight(0.6f, true)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ModeratorPlayerListPreview(){
    val designer = User(
        id="user_id_12345",
        name="reka_teszt"
    )
    val mission = Mission(
        id = "mission_id_9876",
        designerId = designer.id,
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
        missionId = "mission_id_9876",
        playerIds = mutableListOf(player1.id, player2.id)
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
    fun onUserClicked(u:User){}
    ModeratorPlayerList(
        session = session,
        mission = mission,
        designer = designer,
        players = players,
        onUserClicked = ::onUserClicked,
        onBackClick = {}
    )
}
