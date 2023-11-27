package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.views.theme.Black
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

/**
 *
* from: https://github.com/aclassen/ComposeReorderable
*/
@Composable
fun VerticalReorderList(listElements: MutableList<String>,
                        modifier: Modifier = Modifier,
                        newListElements: MutableList<String>,
) {
    var answerAInput by remember { mutableStateOf(value="a") }
    var answerBInput by remember { mutableStateOf("b") }
    var answerCInput by remember { mutableStateOf("c") }
    var answerDInput by remember { mutableStateOf("d") }
    var valueList = mutableListOf(
        answerAInput, answerBInput, answerCInput, answerDInput
    )
    var data = remember { mutableStateOf(listElements) }
    var state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }, onDragEnd = {from, to -> newListElements.apply { add(to, removeAt(from)) } })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 12.dp, 12.dp, 25.dp)
            //.weight(1f, false)
    ){
       LazyColumn(
            state = state.listState,
            modifier = Modifier
                .reorderable(state)
                .detectReorderAfterLongPress(state)
                .padding(all = 5.dp)
                .fillMaxHeight()
        ) {
            items(data.value, { it }) { item ->
                ReorderableItem(state,
                    key = listElements.indexOf(item),
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .detectReorderAfterLongPress(state),
                    
                    ) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 28.dp else 0.dp)
                    Column(
                        modifier = Modifier
                            .shadow(elevation.value)
                            .background(MaterialTheme.colors.surface)
                            .padding(10.dp,5.dp, 10.dp, 5.dp)
                            .border(width=1.dp, color = Black)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold)) {
                                        append(item)
                                    }
                                },
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .background(color= if(isDragging) MaterialTheme.colors.primary else MaterialTheme.colors.secondary)
                                    .padding(10.dp, 10.dp, 10.dp, 10.dp)
                                    .fillMaxWidth()
                            )
                    }
                }
            }
        }
    }
}

