package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.views.theme.Teal200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AccordionMenu(modifier: Modifier = Modifier, model: AccordionModel) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier.padding(0.dp, 2.dp, 0.dp, 2.dp)) {
        AccordionHeader(title = model.header, isExpanded = expanded) {
            expanded = !expanded
        }
        AnimatedVisibility(visible = expanded) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray),
                elevation = 1.dp,
                //modifier = Modifier.padding(top = 8.dp)
            ) {
                LazyColumn {
                    items(model.rows) { row ->
                        AccordionRow(row)
                        Divider(color = Color.Blue, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

data class AccordionModel(
    val header: String,
    val rows: MutableList<Row>
) {
    enum class VisibleValue{
        @PropertyName("string-value-accordion")
        StringValue,
        @PropertyName("int-value-accordion")
        IntValue,
        @PropertyName("badge-value-accordion")
        BadgeValue
    }
    data class Row(
        val visibleValue: VisibleValue = VisibleValue.StringValue,
        val title: String = "",
        var valueString: String = "",
        var valueIntToString: String = "0"
    )
}

@Preview
@Composable
private fun AccordionRow(
    model: AccordionModel.Row = AccordionModel.Row(
        AccordionModel.VisibleValue.BadgeValue,
        "Accordion Row title",
        "Text separeted, by, résszel",
        "4"),

) {
    var stringInput by remember { mutableStateOf(model.valueString)}
    var intInput by remember { mutableStateOf(model.valueIntToString)}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(0.dp, 2.dp, 0.dp, 2.dp)
    ) {
        Text(text = model.title,
            modifier=Modifier
                .weight(0.3f, true)
                .padding(5.dp, 2.dp, 0.dp, 2.dp))
        if(model.visibleValue==AccordionModel.VisibleValue.StringValue){
            OutlinedTextField(
                value = stringInput,
                onValueChange = { stringInput = it },
                singleLine = false,
                placeholder = {
                    Text(
                        text = "value",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                    .weight(0.5f, true)
            )
        }
        if(model.visibleValue==AccordionModel.VisibleValue.IntValue){
            OutlinedTextField(
                value = intInput,
                onValueChange = { intInput = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "0",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                    .weight(0.2f, false)
            )
        }

        if(model.visibleValue==AccordionModel.VisibleValue.BadgeValue){
            OutlinedTextField(
                value = stringInput,
                onValueChange = { stringInput = it },
                singleLine = false,
                placeholder = {
                    Text(
                        text = "value",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp)
                    .weight(0.5f, true)
            )
            OutlinedTextField(
                value = intInput,
                onValueChange = { intInput = it },
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
                    .weight(0.2f, false)
            )
        }
    }
}

//@Preview
@Composable
private fun AccordionHeader(
    title: String = "Header",
    isExpanded: Boolean = true,
    onTapped: () -> Unit = {}
) {
    val degrees = if (isExpanded) 180f else 0f

    Surface(
        //color = Color.White,
        shape = RoundedCornerShape(10),
        border = BorderStroke(1.dp, Color.Gray),
        //elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable { onTapped() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Surface(shape = CircleShape) {
                Icon(
                    Icons.Outlined.ArrowDownward,
                    contentDescription = "arrow-down",
                    modifier = Modifier.rotate(degrees),
                    tint = Color.Black
                )
            }
        }
    }
}