package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Composable
fun MissionsView(
    onJoinWithCodeButtonClicked: () -> Unit,
    onModifyOwnMissionClicked: () -> Unit,
    onAddOwnMissionFloatingActionButtonClicked:() -> Unit
) {

    var privacySwitchState by remember { mutableIntStateOf(0) }
    var generalMissions : MutableList<Mission> = ArrayList()
    var runningMissions : MutableList<Mission> = ArrayList()
    var ownMissions : MutableList<Mission> = ArrayList()


    for (i in 1..3) {
        val generalMission = Mission()
        generalMission.name = "General Mission $i"
        val runningMission = Mission()
        runningMission.name = "Running Mission $i"
        val ownMission = Mission()
        ownMission.name = "Own Mission $i"
        generalMissions.add(generalMission)
        runningMissions.add(runningMission)
        ownMissions.add(ownMission)
    }

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
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null
                    )
                },
            )

            LazyColumn(){
                if (privacySwitchState == 0) {
                    items(generalMissions) {
                        mission -> OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mission.name,
                        onValueChange = {mission.name = it},
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                        )
                    }

                } else if (privacySwitchState == 1) {
                    items(runningMissions) {
                            mission -> OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mission.name,
                        onValueChange = {mission.name = it},
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                        )
                    }
                } else {
                    items(ownMissions) {
                            mission -> OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = mission.name,
                        onValueChange = {mission.name = it},
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        trailingIcon = {
                            Icon(
                                Icons.Filled.BorderColor,
                                contentDescription = null,
                                modifier = Modifier.clickable { onModifyOwnMissionClicked() }
                            )
                        }
                        )
                    }
                }
            }
        }
        if (privacySwitchState == 0) {
            Button(
                onClick = { onJoinWithCodeButtonClicked() },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Join with code")
            }
        } else if (privacySwitchState == 2) {
            FloatingActionButton(
                onClick = { onAddOwnMissionFloatingActionButtonClicked() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(all = 16.dp)) {
                Icon(Icons.Filled.Add, "Create new Own Mission")
            }
        }
    }
}

@Preview
@Composable
fun MissionViewPreview() {
    MissionsView (
        onJoinWithCodeButtonClicked = {},
        onModifyOwnMissionClicked = {},
        onAddOwnMissionFloatingActionButtonClicked = {}
    )
}