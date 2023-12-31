package hu.bme.aut.szoftarch.kozkincsker.views

import java.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ComboBox
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.DatePicker
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*
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
    val checkable =  if (task.taskType.checkable) 0 else 1
    var privacySwitchState by remember { mutableIntStateOf(checkable) }
    /**
     * Task type lists preparation
     * */
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

    val scrollablemodifier =
        if(
            privacySwitchState == 0 &&
            (typeSelectedIndexAutomatic == typeList.indexOf(TaskType.MapAnswer))
        )  Modifier
        else Modifier.verticalScroll(rememberScrollState())

    val miniScrollablemodifier =
        if(
            privacySwitchState == 0 &&
            (typeSelectedIndexAutomatic == typeList.indexOf(TaskType.MapAnswer))
        )  Modifier.verticalScroll(rememberScrollState())
        else Modifier

    var titleInput by remember { mutableStateOf(task.title) }
    var descriptionInput by remember { mutableStateOf(task.description) }
    var scoreInput by remember { mutableStateOf(task.score.toString()) }

    /**
     * Answer values
     *-------------------------
     */
    val pattern =  '|'

    /**
     * Answers size list preparation
     */
    val answerNumberList = mutableListOf<String>()
    for(i in 2..6) answerNumberList.add(i.toString())
    var answersChoiceSelectedIndex by remember { mutableIntStateOf(
        if (task.taskType == TaskType.ListedAnswer
            && task.answers.split(pattern).size > 1)
            task.answers.split(pattern)[0].toInt()-2
        else 0
    ) }
    var answersChoiceExpanded by remember { mutableStateOf(false) }
    var answersOrderSelectedIndex by remember { mutableIntStateOf(
        if (task.taskType == TaskType.OrderAnswer && task.answers.split(pattern).size > 1)
            task.answers.split(pattern)[0].toInt()-2
        else 0
    ) }
    var answersOrderExpanded by remember { mutableStateOf(false) }

    /**
     * Choice answers by designer
     * */
    val choiceAnswerInputList = remember { mutableStateListOf<String>() }
    val checkedStateList = remember { mutableStateListOf<Boolean>() }
    for (i in 0..<6){
        if (task.taskType == TaskType.ListedAnswer && task.answers.split(pattern).size > 12){
            checkedStateList.add(task.answers.split(pattern)[2*i+1].toBoolean())
            choiceAnswerInputList.add(task.answers.split(pattern)[2*i+2])
        }
        else {
            checkedStateList.add(false)
            choiceAnswerInputList.add("")
        }
    }
    /**
     * Order answers by designer
     * */
    val orderAnswerInputList = remember { mutableStateListOf<String>() }
    for (i in 0..<6){
        if (task.taskType == TaskType.OrderAnswer && task.answers.split(pattern).size > 6){
            orderAnswerInputList.add(task.answers.split(pattern)[i+1])
        }
        else {
            orderAnswerInputList.add("")
        }
    }
    /**
     * Number answer by designer
     * */
    var answerNumberInput by remember { mutableStateOf(
        if(task.taskType == TaskType.NumberAnswer) task.answers
        else "0")
    }
    /**
     * Date answer by designer
     * */
    var datePickerState by remember { mutableStateOf(false) }
    var dateInput by remember {
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
            if(task.taskType == TaskType.DateAnswer)
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(task.answers)
            else Date())
        )
    }
    /**
     * Map answer by designer
     * */
    var radiusInput by remember { mutableStateOf(
        if(task.taskType == TaskType.MapAnswer && task.answers.split(pattern).size == 4) task.answers.split(pattern)[0]
        else "10") }
    var actualRadius = (if(radiusInput != "" ) radiusInput.toDouble() else 10.0)
    val markerState = rememberMarkerState(position =
        (if(task.taskType == TaskType.MapAnswer && task.answers.split(pattern).size == 4)
            LatLng(task.answers.split(pattern)[2].toDouble(), task.answers.split(pattern)[3].toDouble())
        else LatLng(47.497913, 19.040236))
    )
    val mapZoomState by remember { mutableFloatStateOf(
        if(task.taskType == TaskType.MapAnswer && task.answers.split(pattern).size == 4)
            task.answers.split(pattern)[1].toFloat()
        else 20f)
    }
    var mapInput by remember { mutableStateOf("") }
    mapInput = markerState.position.latitude.toString() + "|" + markerState.position.longitude.toString()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            markerState.position,
            mapZoomState)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.task_creator_title)) },
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
                modifier = scrollablemodifier
                    .fillMaxSize()
                    .padding(12.dp, 12.dp, 12.dp, 25.dp)
                    .weight(1f, false)
            ) {
                Column(
                    modifier = miniScrollablemodifier
                        .fillMaxWidth()
                ){
                SegmentedControl(
                    listOf(stringResource(R.string.task_evaluation_type_automic), stringResource(R.string.task_evaluation_type_human)),
                    privacySwitchState
                ) { privacySwitchState = it }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.5f, true)
                    ) {
                        if (privacySwitchState == 0) {
                            ComboBox(
                                list = typeListNames,
                                selectedIndex = typeSelectedIndexAutomatic,
                                onIndexChanged = { typeSelectedIndexAutomatic = it },
                                isExpanded = typeExpanded,
                                onExpandedChanged = { typeExpanded = it },
                                textWidth = 130.dp
                            )
                        } else if (privacySwitchState == 1) {
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        label = { Text(text = stringResource(R.string.score_title)) },
                        placeholder = {
                            Text(
                                text = "",
                                color = Gray
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
                            text = stringResource(R.string.new_task_title),
                            color = Gray
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
                            text = stringResource(R.string.new_task_description),
                            color = Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                )
            }
                Column(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if(privacySwitchState == 0) {
                        when(typeSelectedIndexAutomatic){
                            typeList.indexOf(TaskType.ListedAnswer)->{
                                Column(
                                    modifier= Modifier
                                        .fillMaxSize()
                                ){

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                    ){
                                        Text(text = stringResource(R.string.answer_options),
                                                modifier = Modifier
                                                .weight(0.5f, true))
                                        Box(
                                            modifier=Modifier
                                                .weight(0.1f, false)
                                        ){
                                            ComboBox(
                                                list = answerNumberList,
                                                selectedIndex = answersChoiceSelectedIndex,
                                                onIndexChanged = { answersChoiceSelectedIndex = it },
                                                isExpanded = answersChoiceExpanded,
                                                onExpandedChanged = { answersChoiceExpanded = it },
                                                textWidth = 130.dp
                                            )
                                        }
                                    }
                                    for(i in 0..<answerNumberList[answersChoiceSelectedIndex].toInt()){
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                        ){
                                            Checkbox(
                                                checked = checkedStateList[i],
                                                onCheckedChange = { checkedStateList[i] = it },
                                                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .weight(0.1f, false)
                                            )
                                            OutlinedTextField(
                                                value = choiceAnswerInputList[i],
                                                onValueChange = {if (it.isEmpty() || !it.contains(pattern)) {
                                                    choiceAnswerInputList[i] = it
                                                }},
                                                singleLine = false,
                                                placeholder = {
                                                    Text(
                                                        text = stringResource(R.string.answer_option) + " ${i+1}.",
                                                        color = Gray
                                                    )
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                                    .weight(0.8f, true)
                                            )
                                        }
                                    }
                                }
                            }
                            typeList.indexOf(TaskType.NumberAnswer)->{
                                Text(text = stringResource(R.string.number_answer))
                                OutlinedTextField(
                                    value = answerNumberInput,
                                    onValueChange = { answerNumberInput = it },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    placeholder = {
                                        Text(
                                            text = "0",
                                            color = Gray
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
                                Column(
                                    modifier= Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.radius),
                                            modifier = Modifier
                                                .weight(0.3f, true)
                                        )
                                        OutlinedTextField(
                                            value = radiusInput,
                                            onValueChange = {  radiusInput =  it },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                            singleLine = true,
                                            placeholder = {
                                                Text(
                                                    text = "0",
                                                    color = Gray
                                                )
                                            },
                                            modifier = Modifier
                                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                                .weight(0.4f, false)
                                        )
                                        Button(
                                            onClick = {
                                                if(radiusInput != "" && radiusInput.toDouble() >= 0.5){
                                                    actualRadius = radiusInput.toDouble()
                                                }
                                                else {
                                                    actualRadius = 10.0
                                                    radiusInput = "10"
                                                }
                                            },
                                            modifier = Modifier
                                                .padding(vertical = 2.dp, horizontal = 10.dp)
                                                .weight(0.4f, true),
                                            shape = RoundedCornerShape(10),
                                        ) {
                                            Text(stringResource(R.string.radius_save))
                                        }
                                    }
                                }
                                Box(
                                    modifier= Modifier
                                        .fillMaxSize()
                                        .heightIn(60.dp, 100.dp)
                                ){
                                    GoogleMap(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        cameraPositionState = cameraPositionState
                                    ) {
                                    Marker(
                                        state = markerState,
                                        draggable = true,

                                    )
                                    Circle(
                                        center = markerState.position,
                                        radius = actualRadius,
                                        strokeColor = Red
                                    )
                                }
                                }
                            }
                            typeList.indexOf(TaskType.OrderAnswer)->{
                                Column(
                                    modifier= Modifier
                                        .fillMaxSize()
                                ){

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                    ){
                                        Text(text = stringResource(R.string.answers_in_order),
                                            modifier = Modifier
                                                .weight(0.5f, true))
                                        Box(
                                            modifier=Modifier
                                                .weight(0.1f, false)
                                        ){
                                            ComboBox(
                                                list = answerNumberList,
                                                selectedIndex = answersOrderSelectedIndex,
                                                onIndexChanged = { answersOrderSelectedIndex = it },
                                                isExpanded = answersOrderExpanded,
                                                onExpandedChanged = { answersOrderExpanded = it },
                                                textWidth = 130.dp
                                            )
                                        }
                                    }
                                    for(i in 0..<answerNumberList[answersOrderSelectedIndex].toInt()){
                                        OutlinedTextField(
                                            value = orderAnswerInputList[i],
                                            onValueChange = {if (it.isEmpty() || !it.contains(pattern)) {orderAnswerInputList[i] = it}},
                                            singleLine = false,
                                            placeholder = {
                                                Text(
                                                    text = stringResource(R.string.answer_option),
                                                    color = Gray
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                        )
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                    else if(privacySwitchState == 1) {
                        when (typeSelectedIndexHuman) {
                            typeList.indexOf(TaskType.TextAnswer) -> {
                                Text(text = stringResource(R.string.user_text_answer_plaxehoder))
                            }
                            typeList.indexOf(TaskType.ImageAnswer) -> {
                                Text(text = stringResource(R.string.user_image_answer_plaxehoder))
                            }
                            else -> {}
                        }
                }
                }
            }
            Column( ) {
            Button(
                onClick = {
                    task.title = titleInput
                    task.description = descriptionInput
                    if(privacySwitchState == 0) {
                        task.taskType = typeList[typeSelectedIndexAutomatic]
                    } else if(privacySwitchState == 1) {
                        task.taskType = typeList[typeSelectedIndexHuman]
                    }
                    /**
                     * Answers save ->
                     * depends on the taskType
                     * */

                    val answerStringBuilder=StringBuilder()
                    if(task.taskType==TaskType.ListedAnswer) {
                        answerStringBuilder.append(answerNumberList[answersChoiceSelectedIndex])
                        for(i in 0..<6){
                            answerStringBuilder.append('|')
                            if(i < answerNumberList[answersChoiceSelectedIndex].toInt()) answerStringBuilder.append(checkedStateList[i]).append('|').append(choiceAnswerInputList[i])
                            else answerStringBuilder.append(false).append('|')
                        }
                    }
                    else if(task.taskType==TaskType.OrderAnswer) {
                        var tempAnswersList = mutableListOf<String>()
                        answerStringBuilder.append(answerNumberList[answersOrderSelectedIndex])

                        for(i in 0..<6){
                            answerStringBuilder.append('|')
                            if(i < answerNumberList[answersOrderSelectedIndex].toInt()){
                                tempAnswersList.add(orderAnswerInputList[i])
                                answerStringBuilder.append(orderAnswerInputList[i])
                            }
                        }
                        tempAnswersList.shuffle()
                        for(i in 0..<answerNumberList[answersOrderSelectedIndex].toInt()){
                            answerStringBuilder.append('|')
                            answerStringBuilder.append(tempAnswersList[i])
                        }
                    }
                    else if(task.taskType==TaskType.MapAnswer) {
                        answerStringBuilder
                            .append(radiusInput)
                            .append('|')
                            .append(cameraPositionState.position.zoom)
                            .append('|')
                            .append(mapInput)

                    }
                    task.answers = when(task.taskType){
                        TaskType.ListedAnswer, TaskType.MapAnswer, TaskType.OrderAnswer -> answerStringBuilder.toString()
                        TaskType.NumberAnswer -> if(answerNumberInput == "") "0" else answerNumberInput
                        TaskType.DateAnswer -> dateInput.toString()
                        TaskType.TextAnswer, TaskType.ImageAnswer -> ""
                    }
                    task.score = if(scoreInput!="") scoreInput.toInt() else 0
                    onSaveNewClick(task)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text(stringResource(R.string.save))
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
    task1.taskType= TaskType.ListedAnswer
    NewTask(
        task = task1,
        onSaveNewClick = {},
        onDeleteClick = {}
    )
}