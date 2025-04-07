package com.example.composeexamples.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeexamples.favouritespage.FavouritesPage
import com.example.composeexamples.favouritespage.FavouritesViewModel
import com.example.composeexamples.homepage.HomePage
import com.example.composeexamples.homepage.HomeViewModel
import com.example.composeexamples.listpage.ListPage
import com.example.composeexamples.listpage.ListViewModel
import com.example.composeexamples.ui.BottomNavigationBar

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = modifier.padding(contentPadding)
        ) {
            composable("Home") { HomePage(viewModel = hiltViewModel<HomeViewModel>()) }
            composable("Favourites") { FavouritesPage(viewModel = hiltViewModel<FavouritesViewModel>()) }
            composable("List") { ListPage(viewModel = hiltViewModel<ListViewModel>()) }
        }
    }
}
