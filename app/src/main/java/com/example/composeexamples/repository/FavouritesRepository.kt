package com.example.composeexamples.repository

import com.example.composeexamples.database.dao.FavouritesDao
import com.example.composeexamples.database.model.FavouriteCatFact
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface FavouritesRepository {
    val favouriteCatFacts: Flow<List<FavouriteCatFact>>
    suspend fun insertCatFact(
        message: String,
        sizeOfMessage: Int,
        sizeOfMessageWithoutSpace: Int
    )

    suspend fun deleteFact(id: Long)
    suspend fun deleteAllFacts()
}

@Singleton
class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesDao: FavouritesDao
) : FavouritesRepository {

    override val favouriteCatFacts: Flow<List<FavouriteCatFact>> = favouritesDao.getAllFavouriteCatFacts()

    override suspend fun insertCatFact(
        message: String,
        sizeOfMessage: Int,
        sizeOfMessageWithoutSpace: Int
    ) {
        favouritesDao.insertCatFact(
            FavouriteCatFact(
                message = message,
                sizeOfMessage = sizeOfMessage,
                sizeOfMessageWithoutSpace = sizeOfMessageWithoutSpace,
            )
        )
    }

    override suspend fun deleteFact(id: Long) {
        favouritesDao.deleteFact(id)
    }

    override suspend fun deleteAllFacts() {
        favouritesDao.deleteAllFacts()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object FavouritesRepositoryModule {
    @Provides
    fun provideFavouritesRepository(
        favouritesDao: FavouritesDao
    ): FavouritesRepository {
        return FavouritesRepositoryImpl(favouritesDao)
    }
}
