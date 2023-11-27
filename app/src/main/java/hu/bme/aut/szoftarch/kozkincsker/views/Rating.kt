package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.RatingBar

@Composable
fun Rating(
    session: Session?,
    score: Int,
    onSaveClicked: (Feedback, String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    var commentInput by remember { mutableStateOf("") }
    var rating by remember { mutableDoubleStateOf(3.5) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Rating") },
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
                        append("Score: ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(
                            score.toString()
                        )
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
            )

            OutlinedTextField(
                value = commentInput,
                onValueChange = { commentInput = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Rating",
                        color = Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
            )

            RatingBar(
                rating = rating,
                onRatingChanged = { newRating ->
                    rating = newRating
                },
            )
        }
        Column( ) {
            Button(
                onClick = {
                    if(session != null) {
                        val newFeedback = Feedback()
                        newFeedback.missionId = session.missionId
                        newFeedback.stars = rating
                        newFeedback.comment = commentInput
                        onSaveClicked(newFeedback, session.missionId)
                    }
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