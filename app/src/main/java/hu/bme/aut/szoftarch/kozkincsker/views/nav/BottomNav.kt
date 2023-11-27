package hu.bme.aut.szoftarch.kozkincsker.views.nav

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.szoftarch.kozkincsker.ui.account.AccountFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.missions.MissionsFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.user_list.UserListFragment
import hu.bme.aut.szoftarch.kozkincsker.views.theme.Teal200

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView(fragmentManager: FragmentManager, missionsFragment: MissionsFragment, accountFragment: AccountFragment, userListFragment: UserListFragment) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        bottomBar = { BottomNavigation(navController = navController) },
    ) {
        NavigationGraph(navController = navController, fragmentManager = fragmentManager, missionsFragment = missionsFragment, accountFragment = accountFragment, userListFragment = userListFragment)
    }
}
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Missions,
        BottomNavItem.Account,
        BottomNavItem.Users,
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Teal200,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, "") },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, fragmentManager: FragmentManager, missionsFragment: MissionsFragment, accountFragment: AccountFragment, userListFragment: UserListFragment) {
    NavHost(navController, startDestination = BottomNavItem.Missions.screenRoute) {
        composable(BottomNavItem.Missions.screenRoute) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, missionsFragment) }
            )
        }
        composable(BottomNavItem.Account.screenRoute) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, accountFragment) }
            )
        }
        composable(BottomNavItem.Users.screenRoute) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentManager = fragmentManager,
                commit = { add(it, userListFragment) }
            )
        }
    }
}

@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit
) {
    val containerId by rememberSaveable {
        mutableStateOf(View.generateViewId()) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            fragmentManager.findFragmentById(containerId)?.view
                ?.also { (it.parent as? ViewGroup)?.removeView(it) }
                ?: FragmentContainerView(context)
                    .apply { id = containerId }
                    .also {
                        fragmentManager.commit { commit(it.id) }
                    }
        },
        update = {}
    )
}