package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.SegmentedControl
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*

@Composable
fun UserList(
    users: List<User>,
    actualUser: User?,
    onEditUser: (User) -> Unit
){
    var userPageSwitchState by remember { mutableIntStateOf(0) }
    var searchedUsers = users

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(0.dp, 0.dp, 0.dp, 70.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        TopAppBar(
            title = { Text(text = stringResource(R.string.bottom_nav_title_users)) },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ){
            var searchedUserName by remember { mutableStateOf("") }
            searchedUsers = searchedUsers.filter { it.name.contains(searchedUserName, true) }
            val allUsers = searchedUsers
            val suppressedUsers = searchedUsers.filter{ !it.ableToEvaluate }
            val adminUsers = searchedUsers.filter{ it.isAdmin }
            SegmentedControl (
                if (actualUser != null && actualUser.isAdmin) listOf(stringResource(R.string.users_all_title), stringResource(R.string.users_suppressed_title), stringResource(R.string.users_admins_title)) else listOf(stringResource(R.string.users_all_title), stringResource(R.string.users_admins_title)),
                userPageSwitchState
            ) { userPageSwitchState = it }

            OutlinedTextField(
                value = searchedUserName,
                onValueChange = { searchedUserName = it },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(R.string.search)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(){
                if (userPageSwitchState == 0){
                    items(allUsers){user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp),
                            shape = RoundedCornerShape(20),
                            elevation = 1.dp,
                            backgroundColor = CardBackGround
                        ){
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
                                        text = user.name, color = Black, fontSize = 24.sp
                                    )
                                }
                                if (actualUser != null && actualUser.isAdmin){
                                    Row(
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        if(!user.isAdmin) {
                                            if(user.ableToEvaluate){
                                                IconButton(
                                                    modifier = Modifier
                                                        .weight(0.1f, false),
                                                    onClick = {
                                                        user.isAdmin = true
                                                        onEditUser(user)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.AdminPanelSettings,
                                                        ""
                                                    )
                                                }
                                                IconButton(
                                                    modifier = Modifier
                                                        .weight(0.1f, false),
                                                    onClick = {
                                                        user.ableToEvaluate = false
                                                        onEditUser(user)
                                                    }
                                                ) {
                                                    Icon(imageVector = Icons.Filled.Block, "")
                                                }
                                            }
                                            else{
                                                IconButton(
                                                    modifier = Modifier
                                                        .weight(0.1f, false),
                                                    onClick = {
                                                        user.ableToEvaluate = true
                                                        onEditUser(user)
                                                    }
                                                ) {
                                                    Icon(imageVector = Icons.Filled.HowToReg, "")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                else if (actualUser != null && actualUser.isAdmin && userPageSwitchState == 1){
                    items(suppressedUsers){user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp),
                            shape = RoundedCornerShape(20),
                            elevation = 1.dp,
                            backgroundColor = CardBackGround
                        ){
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
                                        text = user.name, color = Black, fontSize = 24.sp
                                    )
                                }
                                if (actualUser.isAdmin) {
                                    Row(
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(
                                            modifier = Modifier
                                                .weight(0.1f, false),
                                            onClick = {
                                                user.ableToEvaluate = true
                                                onEditUser(user)
                                            }
                                        ) {
                                            Icon(imageVector = Icons.Filled.HowToReg, "")
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                else {
                    items(adminUsers){user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp),
                            shape = RoundedCornerShape(20),
                            elevation = 1.dp,
                            backgroundColor = CardBackGround
                        ){
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
                                        text = user.name, color = Black, fontSize = 24.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}