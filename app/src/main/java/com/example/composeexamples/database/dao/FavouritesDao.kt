package com.example.composeexamples.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.composeexamples.database.model.FavouriteCatFact
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM favourites")
    fun getAllFavouriteCatFacts(): Flow<List<FavouriteCatFact>>

    @Insert
    suspend fun insertCatFact(favouriteCatFact: FavouriteCatFact)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun deleteFact(id: Long)

    @Query("DELETE FROM favourites")
    suspend fun deleteAllFacts()
}
