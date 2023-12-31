package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.model.User

@Composable
fun AccountView(
    user: User,
    userEmail: String,
    onEmailChange: (String?, String?) -> Unit,
    onPasswordChange: () -> Unit,
    onLogout: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(0.dp, 0.dp, 0.dp, 70.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector  = Icons.Filled.AccountCircle,
                    null,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                    )
            }

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                        append(stringResource(R.string.app_name))
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                        append(user.name)
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                        append(userEmail)
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            Row(
                modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(user.designedMissionIds.size > 0)
                    Image(
                        painter = painterResource(id = R.drawable.builder),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                if(user.designedMissionIds.size >= 5)
                    Image(
                        painter = painterResource(id = R.drawable.architect),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                if(user.designedMissionIds.size >= 15)
                    Image(
                        painter = painterResource(id = R.drawable.king),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(user.currentSessionIds.size > 0)
                    Image(
                        painter = painterResource(id = R.drawable.key),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                if(user.currentSessionIds.size >= 5)
                    Image(
                        painter = painterResource(id = R.drawable.treasuremap),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                if(user.currentSessionIds.size >= 15)
                    Image(
                        painter = painterResource(id = R.drawable.gold),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
            }
        }
        Column( ) {
            Button(
                onClick = {
                    showDialog = showDialog.not()
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text(stringResource(R.string.change_email))
            }
            Button(
                onClick = {
                    onPasswordChange()
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text(stringResource(R.string.change_password))
            }
            Button(
                onClick = {
                    onLogout()
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            var pass by remember { mutableStateOf("") }
            var newEmail by remember { mutableStateOf("") }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
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
                        value = pass,
                        onValueChange = { pass = it },
                        label = { Text(stringResource(R.string.title_password)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newEmail,
                        onValueChange = { newEmail = it },
                        label = { Text(stringResource(R.string.new_email)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { showDialog = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(R.string.dismiss))
                        }
                        TextButton(
                            onClick = { onEmailChange(pass, newEmail) },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(R.string.confirm))
                        }
                    }
                }
            }
        }
    }
}