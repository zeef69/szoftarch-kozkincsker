package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

/**
 *
 * from: https://github.com/aclassen/ComposeReorderable
 */
@Composable
fun VerticalReorderList(modifier: Modifier = Modifier,) {
    var valueList = mutableListOf<String>(
        "Item 0","Item 1", "Item 2","Item 4","Item 5"
    )
    val data = remember { mutableStateOf(valueList) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
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
                    key = valueList.indexOf(item),
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                    ) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                    Column(
                        modifier = Modifier
                            .shadow(elevation.value)
                            .background(MaterialTheme.colors.surface)
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        Text(text = item.toString())
                    }
                }
            }
        }
/*
        ReorderableItem(state,
            key = valueList.indexOf(data.value[0]),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) { isDragging ->
            val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
            Column(
                modifier = Modifier
                    .shadow(elevation.value)
                    .background(MaterialTheme.colors.surface)
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(text = data.value[0].toString())
            }
        }
        ReorderableItem(state,
            key = valueList.indexOf(data.value[1]),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) { isDragging ->
            val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
            Column(
                modifier = Modifier
                    .shadow(elevation.value)
                    .background(MaterialTheme.colors.surface)
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(text = data.value[1].toString())
            }
        }
        ReorderableItem(state,
            key = valueList.indexOf(data.value[2]),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) { isDragging ->
            val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
            Column(
                modifier = Modifier
                    .shadow(elevation.value)
                    .background(MaterialTheme.colors.surface)
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(text = data.value[2].toString())
            }
        }
        ReorderableItem(state,
            key = valueList.indexOf(data.value[3]),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) { isDragging ->
            val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
            Column(
                modifier = Modifier
                    .shadow(elevation.value)
                    .background(MaterialTheme.colors.surface)
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(text = data.value[3].toString())
            }
        }
        ReorderableItem(state,
            key = valueList.indexOf(data.value[4]),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) { isDragging ->
            val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
            Column(
                modifier = Modifier
                    .shadow(elevation.value)
                    .background(MaterialTheme.colors.surface)
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(text = data.value[4].toString())
            }
        }
    */}
}

