package hu.bme.aut.szoftarch.kozkincsker.views

import java.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Level
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ComboBox
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.DatePicker
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.VerticalReorderList
import java.util.Date
import java.util.Locale


@Composable
fun NewTask(
    task: Task,
    onSaveNewClick: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var checkable =  if (task.taskType.checkable) 0 else 1
    var privacySwitchState by remember { mutableIntStateOf(checkable) }
    val typeListNames = mutableListOf<String>()
    val typeList = mutableListOf<TaskType>()
    for(type in TaskType.values()) {
        if(type.checkable && privacySwitchState == 0) {
            typeListNames.add(stringResource(type.translation))
            typeList.add(type)
        }
        else if(!type.checkable && privacySwitchState == 1) {
            typeListNames.add(stringResource(type.translation))
            typeList.add(type)
        }
    }
    var typeSelectedIndexAutomatic by remember { mutableIntStateOf(if(task.taskType.checkable) typeList.indexOf(task.taskType) else 0) }
    var typeSelectedIndexHuman by remember { mutableIntStateOf(if(!task.taskType.checkable) typeList.indexOf(task.taskType) else 0) }
    var typeExpanded by remember { mutableStateOf(false) }

    var titleInput by remember { mutableStateOf(task.title) }
    var descriptionInput by remember { mutableStateOf(task.description) }
    var scoreInput by remember { mutableStateOf(task.score.toString()) }

    var scrolabblemodifier =
        if(
            privacySwitchState == 0 &&
            typeSelectedIndexAutomatic == typeList.indexOf(TaskType.OrderAnswer)
            )  Modifier
        else Modifier.verticalScroll(rememberScrollState())
    /**
     * Answer values
     *
     */
    //TODO kicserélni mindet task.answer feldologzása után
    var answerAInput by remember { mutableStateOf("") }
    var answerBInput by remember { mutableStateOf("") }
    var answerCInput by remember { mutableStateOf("") }
    var answerDInput by remember { mutableStateOf("") }
    var answerNumberInput by remember { mutableStateOf("0") }
    var datePickerState by remember { mutableStateOf(false) }
    var dateInput by remember {
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()))
    }

    when(task.taskType){

        else -> {}
    }

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
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = scrolabblemodifier
                    .fillMaxSize()
                    .padding(12.dp, 12.dp, 12.dp, 25.dp)
                    .weight(1f, false)

            ) {
                SegmentedControl (
                    listOf("Automatic", "Human"),
                    privacySwitchState
                ) { privacySwitchState = it }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                ){
                    Box(
                        modifier=Modifier
                            .weight(0.5f, true)
                    ) {
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
                        value = scoreInput,
                        onValueChange = { scoreInput = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        label = {Text(text = "Score")},
                        placeholder = {
                            Text(
                                text = "",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .weight(0.5f, true)
                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                    )
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
                    singleLine = false,
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
                Column(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if(privacySwitchState == 0) {
                        when(typeSelectedIndexAutomatic){
                            typeList.indexOf(TaskType.ListedAnswer)->{
                                Text(text = "Answer options")
                                OutlinedTextField(
                                    value = answerAInput,
                                    onValueChange = { answerAInput = it },
                                    singleLine = false,
                                    placeholder = {
                                        Text(
                                            text = "Answer A",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                )
                                OutlinedTextField(
                                    value = answerBInput,
                                    onValueChange = { answerBInput = it },
                                    singleLine = false,
                                    placeholder = {
                                        Text(
                                            text = "Answer B",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                )
                                OutlinedTextField(
                                    value = answerCInput,
                                    onValueChange = { answerCInput = it },
                                    singleLine = false,
                                    placeholder = {
                                        Text(
                                            text = "Answer C",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                )
                                OutlinedTextField(
                                    value = answerDInput,
                                    onValueChange = { answerDInput = it },
                                    singleLine = false,
                                    placeholder = {
                                        Text(
                                            text = "Answer D",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                )
                                OutlinedTextField(
                                    value = answerAInput,
                                    onValueChange = { answerAInput = it },
                                    singleLine = false,
                                    placeholder = {
                                        Text(
                                            text = "Answer A",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                )
                            }
                            typeList.indexOf(TaskType.NumberAnswer)->{
                                Text(text = "Number answer")
                                OutlinedTextField(
                                    value = answerNumberInput,
                                    onValueChange = { answerNumberInput = it },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    placeholder = {
                                        Text(
                                            text = "0",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                )
                            }
                            typeList.indexOf(TaskType.DateAnswer)->{
                                DatePicker(
                                    fromToday = false,
                                    context = context,
                                    datePickerState = datePickerState,
                                    setDatePickerState = { datePickerState = it },
                                    dateInput = dateInput,
                                    onValueChange = { dateInput = it }
                                )
                            }
                            typeList.indexOf(TaskType.MapAnswer)->{
                                //TODO Térkép ide
                            }
                            typeList.indexOf(TaskType.OrderAnswer)->{
                                Text(text = "Answer options")
                                VerticalReorderList(
                                /*    listElements = mutableListOf(
                                            OutlinedTextField(
                                                value = answerAInput,
                                                onValueChange = { answerAInput = it },
                                                singleLine = false,
                                                placeholder = {
                                                    Text(
                                                        text = "Answer A",
                                                        color = Color.Gray
                                                    )
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                            ),
                                            OutlinedTextField(
                                                value = answerBInput,
                                                onValueChange = { answerBInput = it },
                                                singleLine = false,
                                                placeholder = {
                                                    Text(
                                                        text = "Answer B",
                                                        color = Color.Gray
                                                    )
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                            ),
                                            OutlinedTextField(
                                                value = answerCInput,
                                                onValueChange = { answerCInput = it },
                                                singleLine = false,
                                                placeholder = {
                                                    Text(
                                                        text = "Answer C",
                                                        color = Color.Gray
                                                    )
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                            ),
                                                    OutlinedTextField(
                                                    value = answerDInput,
                                            onValueChange = { answerDInput = it },
                                            singleLine = false,
                                            placeholder = {
                                                Text(
                                                    text = "Answer D",
                                                    color = Color.Gray
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                        )
                                    )
                                */
                                )
                            }
                            else -> {}
                        }

                    }
                    else if(privacySwitchState == 1) {
                    when (typeSelectedIndexHuman) {
                        typeList.indexOf(TaskType.TextAnswer) -> {}
                        typeList.indexOf(TaskType.ImageAnswer) -> {}
                        else -> {}
                    }
                }
                }
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
                    task.description = descriptionInput
                    task.answers = answerAInput
                    task.score = scoreInput.toInt()
                    onSaveNewClick(newTask)
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
}

@Preview
@Composable
fun NewTaskPreview() {
    val task1 = Task()
    task1.title = "Task1"
    task1.taskType= TaskType.OrderAnswer
    NewTask(
        task = task1,
        onSaveNewClick = {},
        onDeleteClick = {}
    )
}