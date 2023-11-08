package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ComboBox
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Composable
fun NewTask(
    task: Task,
    onSaveClick: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
    onBackClick: () -> Unit = {}
) {

    var privacySwitchState by remember { mutableIntStateOf(0) }

    val typeListNames = mutableListOf<String>()
    val typeList = mutableListOf<TaskType>()
    for(type in TaskType.values()) {
        if(type.checkable && privacySwitchState == 0) {
            typeListNames.add(type.name)
            typeList.add(type)
        }
        else if(!type.checkable && privacySwitchState == 1) {
            typeListNames.add(type.name)
            typeList.add(type)
        }
    }
    var typeSelectedIndexAutomatic by remember { mutableIntStateOf(0) }
    var typeSelectedIndexHuman by remember { mutableIntStateOf(0) }
    var typeExpanded by remember { mutableStateOf(false) }

    var titleInput by remember { mutableStateOf(task.title) }
    var descriptionInput by remember { mutableStateOf(task.descripion) }
    var answerInput by remember { mutableStateOf(task.answers) }
    var scoreInput by remember { mutableStateOf(task.score.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Task creator") },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(imageVector  = Icons.Filled.ArrowBack, null)
                    },
                    onClick = onBackClick
                )
            },
            actions = {
                IconButton(onClick = { onDeleteClick(task) }) {
                    Icon(Icons.Filled.Delete, null)
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ) {
            SegmentedControl (
                listOf("Automatic", "Human"),
                privacySwitchState
            ) { privacySwitchState = it }

            Box() {
                if(privacySwitchState == 0) {
                    ComboBox(
                        list = typeListNames,
                        selectedIndex = typeSelectedIndexAutomatic,
                        onIndexChanged = { typeSelectedIndexAutomatic = it },
                        isExpanded = typeExpanded,
                        onExpandedChanged = { typeExpanded = it },
                        textWidth = 130.dp
                    )
                } else if(privacySwitchState == 1) {
                    ComboBox(
                        list = typeListNames,
                        selectedIndex = typeSelectedIndexHuman,
                        onIndexChanged = { typeSelectedIndexHuman = it },
                        isExpanded = typeExpanded,
                        onExpandedChanged = { typeExpanded = it },
                        textWidth = 130.dp
                    )
                }
            }

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

            OutlinedTextField(
                value = answerInput,
                onValueChange = { answerInput = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Answer",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
            )

            OutlinedTextField(
                value = scoreInput,
                onValueChange = { scoreInput = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Score",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
            )
        }
        Column( ) {
            Button(
                onClick = {
                    val newTask = task
                    task.title = titleInput
                    if(privacySwitchState == 0) {
                        task.taskType = typeList[typeSelectedIndexAutomatic]
                    } else if(privacySwitchState == 1) {
                        task.taskType = typeList[typeSelectedIndexHuman]
                    }
                    task.descripion = descriptionInput
                    task.answers = answerInput
                    task.score = scoreInput.toInt()
                    onSaveClick(newTask)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text("Save")
            }
        }
    }
}

@Preview
@Composable
fun NewTaskPreview() {
    val task1 = Task()
    task1.title = "Task1"
    NewTask(
        task = task1,
        onSaveClick = {},
        onDeleteClick = {}
    )
}