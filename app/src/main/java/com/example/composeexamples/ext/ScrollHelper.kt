package com.example.composeexamples.ext

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

interface ConnectionScrollState {
    val nestedScrollConnection: NestedScrollConnection
    val offset: Dp
}

/**
 * This class is going to connect two composable as one nested scroll composable (in a Box)
 * and basically "scroll" the whole Box by changing the height of the composable that needs to be the first to appear/ disappear with every scroll
 * making it looks like being scrolled away/ back
 * @param maxOffset The max height of the composable that needs to be the first to appear/ disappear with every scroll
 * @param initialOffset The initial height of the above
 * @param density As it would be nice to have the Px as float, pass the density to here, and not use the dpToPx from our framework this time
 * On the view, it works By adding `.nestedScroll(scrollState.nestedScrollConnection)` to the box and then
 * adding `Modifier.height(scrollState.offset)` to the composable that needs to be the first to appear/ disappear with every scroll
 */

class ScrollHelper(
    maxOffset: Dp,
    initialOffset: Dp,
    private val density: Density,
) : ConnectionScrollState {
    private val maxOffsetPx = with(density) { maxOffset.toPx() }
    private val initialOffsetPx = with(density) { initialOffset.toPx() }
    private var _offsetPx by mutableFloatStateOf(initialOffsetPx)
    override val offset: Dp
        get() = with(density) { _offsetPx.toDp() }

    override val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val consumedY = doScroll(available.y)
            return Offset(0f, consumedY)
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            val consumedY = doScroll(available.y)
            return Offset(0f, consumedY)
        }
    }

    private fun doScroll(delta: Float): Float {
        val oldOffset = _offsetPx
        _offsetPx = (_offsetPx + delta).coerceIn(0f, maxOffsetPx)
        return _offsetPx - oldOffset
    }
}
