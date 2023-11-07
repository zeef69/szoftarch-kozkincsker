package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Preview
@Composable
fun MissionsAndAccountBottomAppBar() {

    var privacySwitchState by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Missions") },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ) {
            var text by remember { mutableStateOf("") }

            SegmentedControl (
                listOf("General", "Running", "Own"),
                privacySwitchState
            ) { privacySwitchState = it }

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null
                    )
                },
            )
            LazyColumn {
                if (privacySwitchState == 0) {
                    items(5) { index ->
                        Text(text = "Mission $index")
                    }
                } else if (privacySwitchState == 1) {
                    items(5) { index ->
                        Text(text = "Running Mission $index")
                    }
                } else {
                    items(5) { index ->
                        Text(text = "My Mission $index")
                    }
                }
            }

        }
    }
}
