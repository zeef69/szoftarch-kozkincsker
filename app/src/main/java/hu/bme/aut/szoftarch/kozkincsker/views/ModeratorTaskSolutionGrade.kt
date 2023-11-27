package hu.bme.aut.szoftarch.kozkincsker.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution

@Composable
fun ModeratorTaskSolutionGrade(
    task: Task? = null,
    taskSolution: TaskSolution? = null,
    onTaskSolutionGoodClicked: (TaskSolution) -> Unit,
    onTaskSolutionWrongClicked: (TaskSolution) -> Unit,
    onDownloadImage: (String) -> String,
    onBackClick: () -> Unit = {}
) {

    var imageURL by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Grade Task") },
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
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 24.sp, fontStyle = FontStyle.Italic))
                        append("Task name: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            if (task != null) {
                                append(task.title)
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
                        pushStyle(SpanStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic))
                        append("Score: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            if (task != null) {
                                append(task.score.toString())
                            }
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
                    withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)) {
                        if (task != null) {
                            append(task.description)
                        }
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            if(task != null && task.taskType == TaskType.TextAnswer) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic)) {
                            append("User answer:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)) {
                            if (taskSolution != null) {
                                append(taskSolution.userAnswer)
                            }
                        }
                    },
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                )
            }
            if(task != null && task.taskType == TaskType.ImageAnswer) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(
                        onClick = {
                            if (taskSolution != null) {
                                Log.i("Task_Answers", taskSolution.userAnswer)
                                imageURL = onDownloadImage(taskSolution.userAnswer)
                                Log.i("Task_Answers", imageURL)

                            }
                        }
                    ){
                        Text(text="Load image")
                    }
                    if(imageURL != ""){
                        AsyncImage(
                            model = imageURL,
                            contentDescription = "Image",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (taskSolution != null) {
                        onTaskSolutionGoodClicked(taskSolution)
                    }
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 5.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(backgroundColor = Green)
            ) {
                Text("Good")
            }

            Button(
                onClick = {
                    if (taskSolution != null) {
                        onTaskSolutionWrongClicked(taskSolution)
                    }
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 5.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(backgroundColor = Red)
            ) {
                Text("Wrong")
            }
        }
    }
}