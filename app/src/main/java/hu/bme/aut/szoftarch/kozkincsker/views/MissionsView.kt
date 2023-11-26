package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Composable
fun MissionsView(
    missions: List<Mission>,
    sessions: List<Session>,
    id: String?,
    user: User?,
    onJoinWithCode: (String) -> Unit,
    onPrivateGameCode: (String) -> Mission?,
    onModifyMission: (Mission) -> Unit,
    onDeleteMission: (Mission) -> Unit,
    onAddMission: () -> Unit,
    onItemClicked: (Mission) -> Unit,
    onSessionClicked: (Session) -> Unit
) {
    var privacySwitchState by remember { mutableIntStateOf(0) }
    var showJoinDialog by remember { mutableStateOf(false) }
    var showPrivateGameDialog by remember { mutableStateOf(false) }
    var searchedMissions = missions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(0.dp, 0.dp, 0.dp, 70.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Missions") },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ) {
            var text by remember { mutableStateOf("") }

            searchedMissions = searchedMissions.filter { it.name.contains(text, true) }
            val generalMissions = searchedMissions.filter {
                (it.state == Mission.State.FINISHED) &&
                        ((it.visibility == Mission.Visibility.PUBLIC) ||
                                (it.designerId == id) ||
                                ((user != null) && user.privatePlayableMissionIds.contains(it.id)))
            }
            val runningSessions = sessions.filter { it.name.contains(text, true) }
            val ownMissions = searchedMissions.filter { it.designerId == id }

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
                /*trailingIcon = {
                    if (!typeSwitchState) {
                        IconButton(
                            onClick = { showMenu = !showMenu }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter_alt_24),
                                contentDescription = null
                            )
                        }
                    }
                },*/
                label = { Text("Kereső") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(){
                if (privacySwitchState == 0) {
                    items(generalMissions) {mission ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp)
                                .clickable(onClick = {
                                    onItemClicked(mission)
                                }),
                            shape = RoundedCornerShape(20),
                            elevation = 1.dp,
                            backgroundColor = Color.LightGray
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(vertical = 5.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                        .weight(0.7f, true)
                                        .width(90.dp)
                                ) {
                                    Text(
                                        text = mission.name, color = Color.Black, fontSize = 24.sp
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    if(user != null && user.isAdmin && mission.visibility==Mission.Visibility.PUBLIC)
                                        IconButton(
                                            onClick = {onDeleteMission(mission)}
                                        ) {
                                            Icon(imageVector  = Icons.Filled.Delete, "")
                                        }
                                }
                            }
                        }
                    }

                }
                else if (privacySwitchState == 1) {
                    items(runningSessions) {session ->  
                        if(session.moderator == id)  {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(1.dp)
                                    .clickable(onClick = {
                                        onSessionClicked(session)
                                    }),
                                shape = RoundedCornerShape(20),
                                elevation = 1.dp,
                                backgroundColor = Color.LightGray
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                            .weight(0.7f, true)
                                            .width(90.dp)
                                    ) {
                                        Text(
                                            text = session.name, color = Color.Black, fontSize = 24.sp
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Icon(imageVector = Icons.Filled.ContactPage, "Moderated Sessions")
                                    }
                                }
                            }
                        }
                        else{
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(1.dp)
                                    .clickable(onClick = {
                                        onSessionClicked(session)
                                    }),
                                shape = RoundedCornerShape(20),
                                elevation = 1.dp,
                                backgroundColor = Color.LightGray
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                            .weight(0.7f, true)
                                            .width(90.dp)
                                    ) {
                                        Text(
                                            text = session.name, color = Color.Black, fontSize = 24.sp
                                        )
                                    }
                                }
                            }
                        }                        
                    }
                }
                else {
                    items(ownMissions) {mission ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp)
                                .clickable(onClick = {
                                    if(mission.state == Mission.State.DESIGNING)
                                        onModifyMission(mission)
                                    else
                                        onItemClicked(mission)
                                }),
                            shape = RoundedCornerShape(20),
                            elevation = 1.dp,
                            backgroundColor = Color.LightGray
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(vertical = 5.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                        .weight(0.7f, true)
                                        .width(90.dp)
                                ) {
                                    Text(
                                        text = mission.name, color = Color.Black, fontSize = 24.sp
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    if(mission.state == Mission.State.DESIGNING)
                                        IconButton(
                                            onClick = {onModifyMission(mission)}
                                        ) {
                                            Icon(imageVector  = Icons.Filled.Edit, "")
                                        }
                                    IconButton(
                                        onClick = {onDeleteMission(mission)}
                                    ) {
                                        Icon(imageVector  = Icons.Filled.Delete, "")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (privacySwitchState == 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ){
                Button(
                    onClick = {
                        showJoinDialog = showJoinDialog.not()
                    }
                ) {
                    Text(text = "Join with code")
                }
                Button(
                    onClick = {
                        showPrivateGameDialog = showPrivateGameDialog.not()
                    }
                ) {
                    Text(text = "Private game code")
                }
            }
        } else if (privacySwitchState == 2) {
            FloatingActionButton(
                onClick = { onAddMission() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(all = 16.dp)) {
                Icon(Icons.Filled.Add, "Create Mission")
            }
        }
    }


    if (showJoinDialog) {
        Dialog(onDismissRequest = { showJoinDialog = false }) {
            var text by remember { mutableStateOf("") }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Code") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { showJoinDialog = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = { onJoinWithCode(text) },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
    if (showPrivateGameDialog) {
        Dialog(onDismissRequest = { showPrivateGameDialog = false }) {
            var text by remember { mutableStateOf("") }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Private game code") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { showPrivateGameDialog = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = {
                                val privateMission = onPrivateGameCode(text)
                                if (user != null && privateMission != null) {
                                    user.privatePlayableMissionIds.add(privateMission.id)
                                }
                                showPrivateGameDialog = false
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}