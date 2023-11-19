package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.enums.LevelType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.AccordionMenu
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.AccordionModel
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ChangingIconButton
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ComboBox
import java.util.Date
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

@Composable
fun NewMission(
    designer: User,
    mission: Mission,
    onNewTask: (Mission, Level) -> Unit,
    onTaskClicked: (Task) -> Unit,
    onSaveClick: (Mission) -> Unit,
    onPostClick: (Mission) -> Unit,
    onBackClick: () -> Unit = {}
) {
    var titleInput by remember { mutableStateOf(mission.name) }
    var descriptionInput by remember { mutableStateOf(mission.description) }
    var privacySwitchState by remember { mutableIntStateOf(mission.visibility.ordinal) }
    var hoursToSolveInput by remember { mutableIntStateOf(mission.hoursToSolve) }
    var daysToSolveInput by remember { mutableIntStateOf(mission.daysToSolve) }
    var missionTags by remember { mutableStateOf("mission.missionTagIds") }
    val levels = mission.levelList
    mission.designerId = designer.id
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Mission creator") },
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ){
                OutlinedTextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.new_mission_title),
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                        .weight(0.82f, false)
                )
                var playablePublic = true
                //for(level in mission.levelList) for (task in level.taskList) playablePublic=playablePublic and task.taskType.checkable
                var iconList = mutableListOf(Icons.Filled.VisibilityOff)
                if(playablePublic) iconList.add(Icons.Filled.Public)
                ChangingIconButton(
                    iconList,
                    privacySwitchState,
                    modifier= Modifier
                        .weight(0.18f, true)
                        .padding(2.dp, 0.dp)
                ){privacySwitchState = it}

            }
            OutlinedTextField(
                value = descriptionInput,
                onValueChange = { descriptionInput = it },
                singleLine = false,
                placeholder = {
                    Text(
                        text = stringResource(R.string.new_mission_description),
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
            )

            var otherSettingsModel= AccordionModel(
                header = stringResource(R.string.other_settings),
                rows = mutableListOf(
                    AccordionModel.Row(AccordionModel.VisibleValue.IntValue, title="Solution days", valueIntToString=daysToSolveInput.toString()),
                    AccordionModel.Row(AccordionModel.VisibleValue.IntValue, title="Solution hours", valueIntToString=hoursToSolveInput.toString()),
                    AccordionModel.Row(AccordionModel.VisibleValue.StringValue, title="Mission tags", valueString=missionTags),
                    AccordionModel.Row(AccordionModel.VisibleValue.BadgeValue, title="Badge", valueString="Default", valueIntToString="0"),
                )
            )

            AccordionMenu(
                modifier = Modifier
                .fillMaxWidth(),
                model=otherSettingsModel )

            LazyColumn(
                modifier = Modifier
                    .padding(all = 5.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(levels) { _, level ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .padding(all = 5.dp)
                                .fillMaxWidth()
                                .weight(0.1f, true)
                        ) {
                            val typeListNames = mutableListOf<String>()
                            for(type in LevelType.values()) {
                                typeListNames.add(type.name)
                            }
                            var typeSelectedIndex by remember { mutableIntStateOf(0) }
                            var typeExpanded by remember { mutableStateOf(false) }

                            ComboBox(
                                list = typeListNames,
                                selectedIndex = typeSelectedIndex,
                                onIndexChanged = { typeSelectedIndex = it },
                                isExpanded = typeExpanded,
                                onExpandedChanged = { typeExpanded = it },
                                textWidth = 0.dp
                            )
                            println(LevelType.values().toList())
                            println(LevelType.values().toList()[typeSelectedIndex])
                            level.levelType = LevelType.values().toList()[typeSelectedIndex]
                        }
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(IntrinsicSize.Max)
                                        .background(Color.LightGray)
                                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                                        .weight(1.0f, true)
                                        .clickable { onTaskClicked(task) }
                                ) {
                                    Text(
                                        text = task.title, color = Color.Black, fontSize = 18.sp, modifier = Modifier
                                            .padding(all = 2.dp)
                                            .weight(0.6f, true)
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                        if(level.taskList.size < 3)
                            Button(
                                onClick = {
                                    mission.name = titleInput
                                    mission.description = descriptionInput
                                    mission.hoursToSolve = hoursToSolveInput
                                    mission.daysToSolve = daysToSolveInput
                                    mission.levelList = levels
                                    mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                                    onNewTask(mission, level) },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 5.dp, horizontal = 2.dp)
                                    .weight(0.15f, true),
                                shape = RoundedCornerShape(10),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(imageVector  = Icons.Filled.Add, null)
                            }
                    }
                }
            }
        }
        Column( ) {
            Button(
                onClick = {
                    mission.name = titleInput
                    mission.description = descriptionInput
                    mission.hoursToSolve = hoursToSolveInput
                    mission.daysToSolve = daysToSolveInput
                    mission.levelList = levels
                    mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                    val level = Level()
                    levels.add(level)
                    onNewTask(mission, level)
                },
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                shape = RoundedCornerShape(10)
            ) {
                Icon(imageVector  = Icons.Filled.Add, null)
            }
            Button(
                onClick = {
                    mission.name = titleInput
                    mission.description = descriptionInput
                    mission.hoursToSolve = hoursToSolveInput
                    mission.daysToSolve = daysToSolveInput
                    mission.levelList = levels
                    mission.modificationDate = Date()
                    var playableWithoutModerator = true
                    for(level in mission.levelList) for (task in level.taskList) playableWithoutModerator=playableWithoutModerator and task.taskType.checkable
                    mission.isPlayableWithoutModerator = playableWithoutModerator
                    mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                    mission.state = Mission.State.DESIGNING
                    onSaveClick(mission)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text("Save")
            }
            Button(
                onClick = {
                    mission.name = titleInput
                    mission.description = descriptionInput
                    mission.hoursToSolve = hoursToSolveInput
                    mission.daysToSolve = daysToSolveInput
                    mission.levelList = levels
                    mission.modificationDate = Date()
                    var playableWithoutModerator = true
                    for(level in mission.levelList) for (task in level.taskList) playableWithoutModerator=playableWithoutModerator and task.taskType.checkable
                    mission.isPlayableWithoutModerator = playableWithoutModerator
                    mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                    mission.state = Mission.State.FINISHED
                    onPostClick(mission)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text("Post")
            }
        }
    }
}

@Preview
@Composable
fun NewMissionPreview() {
    val user = User()
    user.name = "reka_teszt"
    val mission = Mission()
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
    fun onNewTask(m:Mission, l:Level){}
    NewMission(
        designer = user,
        mission = mission,
        onNewTask = ::onNewTask,
        onTaskClicked = {},
        onPostClick = {},
        onSaveClick = {},
    )
}
