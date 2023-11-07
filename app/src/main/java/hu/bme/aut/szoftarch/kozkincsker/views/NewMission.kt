package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Composable
fun NewMission(
    mission: Mission,
    onBackClick: () -> Unit = {},
    onSaveClick: (Mission) -> Unit,
    onPostClick: (Mission) -> Unit
) {
    var titleInput by remember { mutableStateOf(mission.name) }
    var descriptionInput by remember { mutableStateOf("") }
    var privacySwitchState by remember { mutableIntStateOf(0) }

    val levels = mission.levelList

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
            OutlinedTextField(
                value = titleInput,
                onValueChange = { titleInput = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Title",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
            )

            OutlinedTextField(
                value = descriptionInput,
                onValueChange = { descriptionInput = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Description",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
            )

            SegmentedControl (
                listOf("Private", "Public"),
                privacySwitchState
            ) { privacySwitchState = it }

            LazyColumn(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(levels) { _, item ->
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
                                .weight(0.8f, true)
                        ) {
                            for(task in item.taskList) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(IntrinsicSize.Max)
                                        .background(Color.LightGray)
                                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                                        .weight(1.0f, true)
                                ) {
                                    Text(
                                        text = task.title, color = Color.Black, fontSize = 18.sp, modifier = Modifier.padding(all = 2.dp).weight(0.6f, true)
                                    )
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier
                                            .padding(vertical = 1.dp, horizontal = 1.dp)
                                            .weight(0.2f, false)
                                    ) {
                                        Icon(imageVector  = Icons.Filled.Edit, null)
                                    }
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier
                                            .padding(vertical = 1.dp, horizontal = 1.dp)
                                            .weight(0.2f, false)
                                    ) {
                                        Icon(imageVector  = Icons.Filled.Delete, null)
                                    }
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                        if(item.taskList.size < 3)
                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 5.dp, horizontal = 2.dp)
                                    .weight(0.2f, true),
                                shape = RoundedCornerShape(10)
                            ) {
                                Icon(imageVector  = Icons.Filled.Add, null)
                            }
                    }
                }
            }
        }
        Column( ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                shape = RoundedCornerShape(10)
            ) {
                Icon(imageVector  = Icons.Filled.Add, null)
            }
            Button(
                onClick = {
                    val newMission = Mission()
                    onSaveClick(newMission)
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
                    val newMission = Mission()
                    onPostClick(newMission)
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
    NewMission(
        mission = mission,
        onPostClick = {},
        onSaveClick = {}
    )
}
