package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

@Composable
fun ComboBox(
    list: List<String>,
    selectedIndex: Int,
    onIndexChanged: (Int) -> Unit,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    textWidth: Dp
) {
    Button(
        content = {
            if(list.isNotEmpty()) {
                Row() {
                    Text(
                        text = list[selectedIndex],
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .width(textWidth)
                    )

                    Icon(imageVector  = Icons.Filled.KeyboardArrowDown, null)
                }
            }
        },
        onClick = { onExpandedChanged(true) },
    )

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onExpandedChanged(false) },
    ) {
        list.forEachIndexed { index, s ->
            DropdownMenuItem(
                onClick = {
                    onIndexChanged(index)
                    onExpandedChanged(false)
                }
            ) {
                Text(text = s)
            }
        }
    }
}