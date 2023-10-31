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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import hu.bme.aut.szoftarch.kozkincsker.R

@Composable
fun Login(
    onLoginClick: (String, String, Context) -> Unit,
    onRegisterClick: (String, String, Context) -> Unit
) {
    var emailInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                .padding(12.dp, 25.dp, 12.dp, 100.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (
                    icon,
                    email,
                    password,
                    loginButton,
                    registerButton
                ) = createRefs()

                /*Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )*/

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
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(email) {
                            top.linkTo(icon.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
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
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .constrainAs(password) {
                            top.linkTo(email.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

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
                        .constrainAs(loginButton) {
                            top.linkTo(password.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

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
                            onRegisterClick(emailInput, passInput, context)
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                        .constrainAs(registerButton) {
                            top.linkTo(loginButton.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}