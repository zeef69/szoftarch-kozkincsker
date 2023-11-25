package hu.bme.aut.szoftarch.kozkincsker.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.DatePicker
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.VerticalReorderList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Task(
    task: Task,
    onSaveClicked: () -> Unit,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val pattern =  '|'
    /**
     * Válaszlista és checkbox érték a választós és a sorbarendezős feladatokhoz
     * */
    val answerListSize = if(task.taskType == TaskType.ListedAnswer || task.taskType == TaskType.OrderAnswer) task.answers.split(pattern)[0].toInt() else 0
    val choiceAnswerInputList = ArrayList<String> () //remember { mutableStateListOf<String>() }
    val checkedStateList = remember { mutableStateListOf<Boolean>() }
    val orderAnswerInputList = remember { mutableStateListOf<String>() }
    for (i in 0..<answerListSize){
        if (task.taskType == TaskType.ListedAnswer && task.answers.split(pattern).size > 12) {
            checkedStateList.add(false)
            choiceAnswerInputList.add(task.answers.split(pattern)[2 * i + 2])
        }
        if (task.taskType == TaskType.OrderAnswer && task.answers.split(pattern).size > 6) {
            orderAnswerInputList.add(task.answers.split(pattern)[i+1])
        }
    }
    /**
     * Számos feladathoz válasz mező érték
     * */
    var answerNumberInput by remember { mutableStateOf("0")}
    /**
     * Dátum feladathoz választó és mező érték
     * */
    var datePickerState by remember { mutableStateOf(false) }
    var dateInput by remember {
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()))
    }
    var textAnswerInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Task") },
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f, true)
                    .padding(bottom = 10.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ){
                    Text(
                        buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic))
                            append("Task name: ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(task.title)
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
                            pushStyle(SpanStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic))
                            append("Score: ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(task.score.toString())
                            }
                            pop()
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
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                            append("Task type: ")
                            append(stringResource(id = task.taskType.translation))
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .weight(0.4f, false)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize=14.sp)) {
                            append(task.description)
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.7f, false)
                    .fillMaxSize()
                    .padding(12.dp, 5.dp, 12.dp, 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                when(task.taskType){
                    TaskType.ListedAnswer -> {
                        Column(
                            modifier= Modifier
                                .fillMaxSize()
                        ){
                            Text(text = "Answer options",
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 2.dp))
                            for(i in 0..<answerListSize){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                ){
                                    Checkbox(
                                        checked = checkedStateList[i],
                                        onCheckedChange = { checkedStateList[i] = it },
                                        colors = CheckboxDefaults.colors(Color.Blue),
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .weight(0.1f, false)
                                    )
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(style = SpanStyle(fontSize = 18.sp,fontWeight = FontWeight.Bold)) {
                                                append(choiceAnswerInputList[i])
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                            .fillMaxWidth()
                                            .weight(0.8f, false)
                                    )
                                }
                            }
                        }
                    }
                    TaskType.NumberAnswer -> {
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
                    TaskType.DateAnswer -> {
                        Text(text = "Date answer")
                        DatePicker(
                            fromToday = false,
                            context = context,
                            datePickerState = datePickerState,
                            setDatePickerState = { datePickerState = it },
                            dateInput = dateInput,
                            onValueChange = { dateInput = it }
                        )
                    }
                    TaskType.MapAnswer -> {
                        Text(text = "Map answer")

                    }
                    TaskType.OrderAnswer -> {
                        Text(text = "Order answers")
                        VerticalReorderList(
                            listElements=orderAnswerInputList,
                            newListElements=orderAnswerInputList
                        )
                    }
                    TaskType.TextAnswer -> {
                        OutlinedTextField(
                            value = textAnswerInput,
                            onValueChange = { textAnswerInput = it },
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
                    }
                    TaskType.ImageAnswer -> {}
                }
            }
        }
        Column( ) {
            Button(
                onClick = {
                    for( i in 0..< orderAnswerInputList.size)
                        Log.i("order", orderAnswerInputList[i])
                    onSaveClicked()
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