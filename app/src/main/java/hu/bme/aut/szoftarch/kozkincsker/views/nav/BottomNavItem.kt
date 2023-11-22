package hu.bme.aut.szoftarch.kozkincsker.views.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screenRoute:String){
    data object Missions : BottomNavItem("Missions", Icons.Filled.Menu,"missions")
    data object Account: BottomNavItem("Account", Icons.Filled.AccountCircle,"account")
}