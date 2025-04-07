package com.example.composeexamples.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composeexamples.R

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navBackEntry by navController.currentBackStackEntryAsState()

    val items = listOf(
        BottomNavItem(title = "Home", icon = R.drawable.icn_arrow_back),
        BottomNavItem(title = "Favourites", icon = R.drawable.icn_arrow_back),
        BottomNavItem(title = "List", icon = R.drawable.icn_arrow_back),
    )

    NavigationBar(modifier) {
        items.forEach { item ->
            val isSelected = navBackEntry?.destination?.route == item.title
            NavItem(
                screen = item,
                isSelected = isSelected,
                onClick = { navController.navigate(item.title) }
            )
        }
    }
}

@Composable
fun RowScope.NavItem(
    screen: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBarItem(
        label = { Text(text = screen.title) },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },
        selected = isSelected,
        alwaysShowLabel = true,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(),
        modifier = modifier,
    )
}

data class BottomNavItem(
    val title: String,
    val icon: Int,
)
