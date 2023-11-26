package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution

@Composable
fun ModeratorTaskSolutionGrade(
    task: Task? = null,
    taskSolution: TaskSolution? = null,
    onTaskSolutionGoodClicked: (TaskSolution) -> Unit,
    onTaskSolutionWrongClicked: (TaskSolution) -> Unit,
    onBackClick: () -> Unit = {}
) {
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
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        if (task != null) {
                            append(task.title)
                        }
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        if (task != null) {
                            append(task.description)
                        }
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            if(task != null && task.taskType == TaskType.TextAnswer) {
                Text(
                    buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic))
                        append("User answer: ")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            if (taskSolution != null) {
                                append(taskSolution.userAnswer)
                            }
                        }
                        pop()
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                )
            }

            if(task != null && task.taskType == TaskType.ImageAnswer) {
                //TODO kép
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("Wrong")
            }
        }
    }
}