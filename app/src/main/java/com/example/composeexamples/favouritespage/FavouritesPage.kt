package com.example.composeexamples.favouritespage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composeexamples.database.model.FavouriteCatFact
import com.example.composeexamples.ui.CatFactTile
import com.example.composeexamples.ui.theme.Theme

@Composable
fun FavouritesPage(
    viewModel: FavouritesViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onHandleEvent: (FavouritesViewModel.UiEvent) -> Unit = viewModel::handleEvent

    FavouritesScreen(
        numberOfFavourites = state.numberOfFavourites,
        facts = state.catFacts,
        onDeleteAllClick = { onHandleEvent(FavouritesViewModel.UiEvent.DeleteAllFacts) },
        onTileDeleteClick = { id -> onHandleEvent(FavouritesViewModel.UiEvent.DeleteFact(id)) },
        modifier = modifier
    )
}

@Composable
private fun FavouritesScreen(
    numberOfFavourites: Int,
    facts: List<FavouriteCatFact>,
    onDeleteAllClick: () -> Unit,
    onTileDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            Text(
                text = "$numberOfFavourites Favourite Cat Facts",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onDeleteAllClick) {
                Text(text = "Delete All")
            }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(space = 16.dp)) {
            items(facts) { fact ->
                CatFactTile(
                    message = fact.message,
                    sizeOfMessage = fact.sizeOfMessage,
                    sizeOfMessageWithoutSpace = fact.sizeOfMessageWithoutSpace,
                    shouldShowDeleteButton = true,
                    onDeleteClick = { onTileDeleteClick(fact.id) },
                )
            }
        }
    }
}

//region Preview
@Preview(showBackground = true)
@Composable
private fun FavouritesPreview() {
    Theme {
        FavouritesScreen(
            numberOfFavourites = 2,
            facts = listOf<FavouriteCatFact>(
                FavouriteCatFact(
                    message = "This is the message.",
                    sizeOfMessage = 0,
                    sizeOfMessageWithoutSpace = 0,
                ),
                FavouriteCatFact(
                    message = "This is the message.",
                    sizeOfMessage = 0,
                    sizeOfMessageWithoutSpace = 0,
                ),
            ),
            onDeleteAllClick = {},
            onTileDeleteClick = {},
        )
    }
}
//endregion
