package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.enums.LevelType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ChangingIconButton
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ComboBox
import java.util.Date
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.enums.MissionType
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

@Composable
fun NewMission(
    designer: User,
    mission: Mission,
    onNewTask: (Mission, Level) -> Unit,
    onTaskClicked: (Mission, Level, Task) -> Unit,
    onSaveClick: (Mission) -> Unit,
    onPostClick: (Mission) -> Unit,
    onBackClick: () -> Unit = {}
) {
    var titleInput by remember { mutableStateOf(mission.name) }
    var descriptionInput by remember { mutableStateOf(mission.description) }
    var privacySwitchState by remember { mutableIntStateOf(mission.visibility.ordinal) }
    var hoursToSolveStringInput by remember { mutableStateOf(mission.hoursToSolve.toString()) }
    var daysToSolveStringInput by remember { mutableStateOf(mission.daysToSolve.toString()) }
    val missionTypeListNames = mutableListOf<String>()
    val missionTypeList = mutableListOf<MissionType>()
    for(type in MissionType.values()) {
        missionTypeListNames.add(stringResource(type.translation))
        missionTypeList.add(type)
    }
    var missionTypeSelectedIndex by remember { mutableIntStateOf( missionTypeList.indexOf(mission.missionType)) }
    var missionTypeExpanded by remember { mutableStateOf(false) }

    val levels = mission.levelList
    val levelTypeListNames = mutableListOf<String>()
    val levelTypeList = mutableListOf<LevelType>()
    for(type in LevelType.values()) {
        levelTypeListNames.add(stringResource(type.translation))
        levelTypeList.add(type)
    }
    var levelTypeSelectedIndex by remember { mutableIntStateOf(0) }
    var levelTypeExpanded by remember { mutableStateOf(false) }

    val levelTypeSelectedIndexList = remember { mutableStateListOf<Int>() }
    val levelTypeExpandedList = remember { mutableStateListOf<Boolean>() }
    for (i in 0..<levels.size){
        levelTypeSelectedIndexList.add(levelTypeList.indexOf(levels[i].levelType))
        levelTypeExpandedList.add(false)
    }

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
                /*
                var playablePublic = true
                for(level in mission.levelList) {
                    for (task in level.taskList){
                        playablePublic=playablePublic && task.taskType.checkable
                    }
                }*/
                var iconList = mutableListOf(Icons.Filled.VisibilityOff)
                //if(playablePublic)
                    iconList.add(Icons.Filled.Public)
                ChangingIconButton(
                    iconList,
                    privacySwitchState,
                    modifier= Modifier
                        .weight(0.18f, true)
                        .padding(2.dp, 0.dp)
                ){privacySwitchState = it}

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ){
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
                        .weight(0.82f, false)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                ){
                OutlinedTextField(
                    value = daysToSolveStringInput,
                    onValueChange = { daysToSolveStringInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Solution days",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .weight(0.3f, false)
                        .padding(horizontal = 5.dp, vertical=2.dp)
                )
                OutlinedTextField(
                    value = hoursToSolveStringInput,
                    onValueChange = { hoursToSolveStringInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Solution hours",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .weight(0.3f, false)
                        .padding(horizontal = 5.dp, vertical=2.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(0.4f, true)
                        .padding(vertical = 2.dp)
                ) {
                    ComboBox(
                        list = missionTypeListNames,
                        selectedIndex = missionTypeSelectedIndex,
                        onIndexChanged = { missionTypeSelectedIndex = it },
                        isExpanded = missionTypeExpanded,
                        onExpandedChanged = { missionTypeExpanded = it },
                        textWidth = 130.dp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(all = 5.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(levels) { i, level ->
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
                            ComboBox(
                                list = levelTypeListNames,
                                selectedIndex = levelTypeSelectedIndexList[i],
                                onIndexChanged = { levelTypeSelectedIndexList[i] = it },
                                isExpanded = levelTypeExpandedList[i],
                                onExpandedChanged = { levelTypeExpandedList[i] = it },
                                textWidth = 0.dp
                            )
                            level.levelType = levelTypeList[levelTypeSelectedIndexList[i]]
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
                                        .clickable { onTaskClicked(mission, level, task) }
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
                                    mission.hoursToSolve = hoursToSolveStringInput.toInt()
                                    mission.daysToSolve = daysToSolveStringInput.toInt()
                                    mission.levelList = levels
                                    mission.missionType = missionTypeList[missionTypeSelectedIndex]
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
                    mission.hoursToSolve = hoursToSolveStringInput.toInt()
                    mission.daysToSolve = daysToSolveStringInput.toInt()
                    mission.levelList = levels
                    mission.missionType = missionTypeList[missionTypeSelectedIndex]
                    mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                    val level = Level()
                    onNewTask(mission, level)
                },
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                shape = RoundedCornerShape(10)
            ) {
                Icon(imageVector  = Icons.Filled.Add, null)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
            ){
                Button(
                    onClick = {
                        mission.name = titleInput
                        mission.description = descriptionInput
                        mission.hoursToSolve = hoursToSolveStringInput.toInt()
                        mission.daysToSolve = daysToSolveStringInput.toInt()
                        mission.levelList = levels
                        mission.missionType = missionTypeList[missionTypeSelectedIndex]
                        mission.modificationDate = Date()
                        var playableWithoutModerator = true
                        for(level in mission.levelList) for (task in level.taskList) playableWithoutModerator=playableWithoutModerator and task.taskType.checkable
                        mission.isPlayableWithoutModerator = playableWithoutModerator
                        mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                        mission.state = Mission.State.DESIGNING
                        onSaveClick(mission)
                    },
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                        .weight(0.4f, true),
                    shape = RoundedCornerShape(10),
                ) {
                    Text("Save")
                }
                Button(
                    onClick = {
                        mission.name = titleInput
                        mission.description = descriptionInput
                        mission.hoursToSolve = hoursToSolveStringInput.toInt()
                        mission.daysToSolve = daysToSolveStringInput.toInt()
                        mission.levelList = levels
                        mission.missionType = missionTypeList[missionTypeSelectedIndex]
                        mission.modificationDate = Date()
                        var playableWithoutModerator = true
                        for(level in mission.levelList) for (task in level.taskList) playableWithoutModerator=playableWithoutModerator and task.taskType.checkable
                        mission.isPlayableWithoutModerator = playableWithoutModerator
                        mission.visibility = if(privacySwitchState==0) Mission.Visibility.PRIVATE else Mission.Visibility.PUBLIC
                        mission.state = Mission.State.FINISHED
                        if(mission.visibility == Mission.Visibility.PRIVATE){
                            val allowedChars = ('A'..'Z') + ('0'..'9')
                            mission.accessCode = (1..8)
                                .map { allowedChars.random() }
                                .joinToString("")
                        }
                        onPostClick(mission)
                    },
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                        .weight(0.4f, true),
                    shape = RoundedCornerShape(10),
                ) {
                    Text("Post")
                }
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
    fun onTaskClicked(m:Mission, l:Level, t:Task){}
    NewMission(
        designer = user,
        mission = mission,
        onNewTask = ::onNewTask,
        onTaskClicked = ::onTaskClicked,
        onPostClick = {},
        onSaveClick = {},
    )
}
