package com.example.composeexamples.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeexamples.ui.theme.Theme


@Composable
fun CatFactTile(
    message: String,
    sizeOfMessage: Int,
    sizeOfMessageWithoutSpace: Int,
    modifier: Modifier = Modifier,
    shouldShowDeleteButton: Boolean = false,
    onDeleteClick: () -> Unit = {},
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xFFF6F5EF))
            .padding(all = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f)
        ) {
            Text(text = message, fontSize = 24.sp)
            Text(text = "Size of the message: $sizeOfMessage", fontSize = 24.sp)
            Text(text = "Size of the message without space: $sizeOfMessageWithoutSpace", fontSize = 24.sp)
        }
        if (shouldShowDeleteButton) {
            Button(onClick = onDeleteClick) { Text(text = "Delete", fontSize = 16.sp) }
        }
    }
}

//region Preview
@Preview(showBackground = true)
@Composable
private fun CatFactTileWithNoDeleteButtonPreview() {
    Theme {
        CatFactTile(
            message = "This is the cat fact message",
            sizeOfMessage = 0,
            sizeOfMessageWithoutSpace = 0,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CatFactTileWithDeleteButtonPreview() {
    Theme {
        CatFactTile(
            message = "This is the cat fact message",
            sizeOfMessage = 0,
            sizeOfMessageWithoutSpace = 0,
            shouldShowDeleteButton = true,
            onDeleteClick = {},
        )
    }
}
//endregion
