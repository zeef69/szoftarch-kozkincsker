package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl

@Composable
fun Mission(
    mission: Mission,
    onBackClick: () -> Unit = {},
    onJoinCodeClick: () -> Unit = {}
) {
    var switchState by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = "Mission") },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(imageVector = Icons.Filled.ArrowBack, null)
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
                        append(mission.name)
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("mission.desctiption")
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
            )

            if (mission.designer != null)
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(mission.designer!!.name)
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

            SegmentedControl(
                listOf("Sessions", "Start Session", "Statistics"),
                switchState
            ) { switchState = it }

            if(switchState == 0) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Running sessions:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                /**/LazyColumn(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxSize()
                ) {
                    itemsIndexed(mission.levelList[0].taskList) { _, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                                .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        ) {
                            Text(
                                text = item.title,
                                color = Color.Black,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(all = 2.dp).weight(0.6f, true)
                            )
                            Button(
                                onClick = {
                                    onJoinCodeClick()
                                },
                                modifier = Modifier
                                    .padding(vertical = 2.dp, horizontal = 2.dp)
                                    .fillMaxWidth()
                                    .weight(0.4f, true),
                                shape = RoundedCornerShape(10),
                            ) {
                                Text("Join")
                            }
                        }
                    }
                }/**/
            }
            else if(switchState == 1) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("As Moderator:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                var checkedModerator by remember { mutableStateOf(false) }
                Switch(
                    checked = checkedModerator,
                    onCheckedChange = {
                        checkedModerator = it
                    }
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Private/Public:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                var checkedPrivate by remember { mutableStateOf(false) }
                Switch(
                    checked = checkedPrivate,
                    onCheckedChange = {
                        checkedPrivate = it
                    }
                )
            }
            else {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Stats:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Ratings:")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(((LocalConfiguration.current.screenWidthDp / 2) - 20).dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxSize()
                ) {
                    itemsIndexed(mission.feedbacks) { _, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                                .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        ) {
                            Text(
                                text = item.comment,
                                color = Color.Black,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(all = 2.dp).weight(0.6f, true)
                            )
                            Icon(
                                imageVector  = Icons.Filled.StarRate, null,
                                modifier = Modifier
                                    .padding(vertical = 2.dp, horizontal = 2.dp)
                                    .fillMaxWidth()
                                    .weight(0.4f, true),
                            )
                        }
                    }
                }
            }
        }
        if(switchState != 2)
            Column() {
                Button(
                    onClick = {
                        onJoinCodeClick()
                    },
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10),
                ) {
                    if(switchState == 0)
                        Text("Join with code")
                    if(switchState == 1)
                        Text("Start")
                }
            }
    }
}