package com.example.composeexamples

import com.example.composeexamples.ext.sizeWithoutSpace
import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtTest {
    @Test
    fun `test sizeWithoutSpace returns correct Int`() {
        assertEquals(32, "This is a Jetpack Compose example app.".sizeWithoutSpace())
    }
}
