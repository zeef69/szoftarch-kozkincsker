package hu.bme.aut.szoftarch.kozkincsker.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*

@Composable
fun Login(
    onLoginClick: (String, String, Context) -> Unit,
    onRegisterClick: (String, String, String, Context) -> Unit
) {
    var emailInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }

    var switchState by remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(12.dp, 25.dp, 12.dp, 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SegmentedControl(
                listOf("Login", "Register"),
                switchState
            ) { switchState = it }

            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(imageVector  = Icons.Filled.Email, null)
                },
                label = {
                    Text(
                        text = stringResource(R.string.title_email),
                        color = Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 0.dp)
            )

            OutlinedTextField(
                value = passInput,
                onValueChange = { passInput = it },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(imageVector  = Icons.Filled.Key, null)
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, null)
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.title_password),
                        color = Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 0.dp)
            )

            if(switchState == 1)
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    label = {
                        Text(
                            text = "Name",
                            color = Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                )

            if(switchState == 0)
                Button(
                    content = {
                        Text(
                            text = stringResource(R.string.login),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        if(emailInput == "") {
                            Toast.makeText(context, R.string.missing_email, Toast.LENGTH_SHORT).show()
                        } else if(passInput == "") {
                            Toast.makeText(context, R.string.missing_password, Toast.LENGTH_SHORT).show()
                        } else {
                            onLoginClick(emailInput, passInput, context)
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                )

            if(switchState == 1)
                Button(
                    content = {
                        Text(
                            text = stringResource(R.string.register),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(25.dp, 0.dp)
                        )
                    },
                    onClick = {
                        if(emailInput == "") {
                            Toast.makeText(context, R.string.missing_email, Toast.LENGTH_SHORT).show()
                        } else if(passInput == "") {
                            Toast.makeText(context, R.string.missing_password, Toast.LENGTH_SHORT).show()
                        } else {
                            onRegisterClick(nameInput, emailInput, passInput, context)
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                )
        }
    }
}