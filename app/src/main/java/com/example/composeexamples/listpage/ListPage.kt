package com.example.composeexamples.listpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composeexamples.ext.ScrollHelper
import com.example.composeexamples.repository.model.FactDataOfCat
import com.example.composeexamples.ui.CatFactTile
import com.example.composeexamples.ui.theme.Theme

@Composable
fun ListPage(
    viewModel: ListViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onHandleEvent: (ListViewModel.UiEvent) -> Unit = viewModel::handleEvent

    ListScreen(
        numberOfFacts = state.numberOfFacts,
        facts = state.listOfFact,
        onReachTheEndOfTheList = { onHandleEvent(ListViewModel.UiEvent.GetCatFact) },
        modifier = modifier
    )
}

@Composable
fun ListScreen(
    numberOfFacts: Int,
    facts: List<FactDataOfCat>,
    onReachTheEndOfTheList: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loadMoreFacts by rememberUpdatedState(onReachTheEndOfTheList)

    val density = LocalDensity.current
    val scrollState = remember {
        ScrollHelper(
            maxOffset = 55.dp,
            initialOffset = 55.dp,
            density = density,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollState.nestedScrollConnection)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = scrollState.offset + 45.dp)
        ) {
            items(facts) { fact ->
                CatFactTile(
                    message = fact.message,
                    sizeOfMessage = fact.sizeOfMessage,
                    sizeOfMessageWithoutSpace = fact.sizeOfMessageWithoutSpace,
                )
            }
            item {
                Text(
                    text = "Loading more...",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                LaunchedEffect(Unit) {
                    loadMoreFacts()
                }
            }
        }
        Text(
            text = "$numberOfFacts Cat facts are loaded",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = scrollState.offset)
        )
        Text(
            text = "Nested Scroll",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = Color(0xFFFAF8FF))
        )
    }
}

//region Preview
@Preview(showBackground = true)
@Composable
private fun ListScreenPreview() {
    Theme {
        ListScreen(
            numberOfFacts = 2,
            facts = listOf<FactDataOfCat>(
                FactDataOfCat(
                    message = "This is the message",
                    sizeOfMessage = 0,
                    sizeOfMessageWithoutSpace = 0,
                ),
                FactDataOfCat(
                    message = "This is the message",
                    sizeOfMessage = 0,
                    sizeOfMessageWithoutSpace = 0,
                ),
            ),
            onReachTheEndOfTheList = {},
        )
    }
}
//endregion
