package com.example.composeexamples.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composeexamples.database.dao.FavouritesDao
import com.example.composeexamples.database.model.FavouriteCatFact
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [FavouriteCatFact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): FavouritesDao {
        return db.favouritesDao()
    }
}
