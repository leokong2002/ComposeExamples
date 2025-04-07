package com.example.composeexamples.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composeexamples.ui.CatFactTile
import com.example.composeexamples.ui.theme.Theme

@Composable
fun HomePage(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onHandleEvent: (HomeViewModel.UiEvent) -> Unit = viewModel::handleEvent

    HomeScreen(
        title = state.title,
        message = state.message,
        sizeOfMessage = state.sizeOfMessage,
        sizeOfMessageWithoutSpace = state.sizeOfMessageWithoutSpace,
        onReloadClick = { onHandleEvent(HomeViewModel.UiEvent.ReloadCatFact) },
        onSaveClick = { onHandleEvent(HomeViewModel.UiEvent.AddToFavourites) },
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    title: String,
    message: String,
    sizeOfMessage: Int,
    sizeOfMessageWithoutSpace: Int,
    onReloadClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(text = title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        CatFactTile(
            message = message,
            sizeOfMessage = sizeOfMessage,
            sizeOfMessageWithoutSpace = sizeOfMessageWithoutSpace,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onReloadClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Reload", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onSaveClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save to Favourites", fontSize = 16.sp)
        }
    }
}

//region Preview
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    Theme {
        HomeScreen(
            title = "Cat Fact",
            message = "This is the cat fact message.",
            sizeOfMessage = 0,
            sizeOfMessageWithoutSpace = 0,
            onReloadClick = {},
            onSaveClick = {},
        )
    }
}
//endregion
