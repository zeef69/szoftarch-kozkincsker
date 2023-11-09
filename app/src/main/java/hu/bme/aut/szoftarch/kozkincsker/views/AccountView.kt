package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AccountView(
    onEditAccountInformationFloatingActionButtonPressed: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    var UserName: String = "Heck Elek"
    var UserEmail: String = "Example@mail.com"
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Account")
                },
                actions = {
                    IconButton(onClick = { onDeleteAccountClick() }) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditAccountInformationFloatingActionButtonPressed }) {
                Icon(Icons.Default.BorderColor, contentDescription = "Add")
            }
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = UserName,
                onValueChange = {UserName = it},
                label = {
                    Text("Name: ")
                })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = UserEmail,
                onValueChange = {UserEmail = it},
                label = {
                    Text("Email: ")
                })
        }
    }
}

@Preview
@Composable
fun AccountViewPreview() {
    AccountView(
        onEditAccountInformationFloatingActionButtonPressed = {},
        onDeleteAccountClick = {}
    )
}