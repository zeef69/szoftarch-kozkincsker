package hu.bme.aut.szoftarch.kozkincsker.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MissionsAndAccountBottomAppBar() {
    Scaffold (
        bottomBar = {
            BottomAppBar(modifier = Modifier) {
                BottomNavigationBar()
            }
        }
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Example of a scaffold with a bottom app bar."
        )
    }
}

@Composable
fun BottomNavigationBar() {

}

sealed class NavigationItem(var route: String, val icon: ImageVector?, var title: String) {
    object Missions : NavigationItem("Missions", Icons.Rounded.Home, "Missions")
    object Account : NavigationItem("Account", Icons.Rounded.Info, "Account")
}