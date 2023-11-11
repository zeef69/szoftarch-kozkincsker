package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RatingBar(
    rating: Double,
    maxRating: Int = 5,
    onRatingChanged: (Double) -> Unit
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        for (i in 1..maxRating) {
            IconButton(
                onClick = { onRatingChanged(i.toDouble()) }
            ) {
                if (i <= rating) Icon(Icons.Filled.StarRate, "")
                else Icon(Icons.Filled.StarBorder, "")
            }
        }
    }
}