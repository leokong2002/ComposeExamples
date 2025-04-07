package com.example.composeexamples.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteCatFact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val message: String,
    val sizeOfMessage: Int,
    val sizeOfMessageWithoutSpace: Int,
)
