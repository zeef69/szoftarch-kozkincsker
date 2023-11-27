package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*

@Composable
fun CustomChart(
    average: Float,
    barValue: List<Float>,
    xAxisScale: List<String>
) {
    val context = LocalContext.current
    val barGraphHeight by remember { mutableStateOf(150.dp) }
    val barGraphWidth by remember { mutableStateOf(17.dp) }

    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            barValue.forEach {
                Box(
                    modifier = Modifier
                        .padding(start = barGraphWidth, bottom = 5.dp)
                        .clip(CircleShape)
                        .width(barGraphWidth)
                        .fillMaxHeight(it)
                        .background(PrimaryVariant)
                        .clickable {
                            Toast
                                .makeText(context, it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 80.sp, fontWeight = FontWeight.Bold)) {
                        append("%.1f".format(average))
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(20.dp, 10.dp, 0.dp, 0.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(start = barGraphWidth)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
        ) {
            xAxisScale.forEach {
                Text(
                    modifier = Modifier.width(barGraphWidth),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}