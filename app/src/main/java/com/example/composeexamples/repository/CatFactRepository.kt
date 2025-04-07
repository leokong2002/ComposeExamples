package com.example.composeexamples.repository

import com.example.composeexamples.repository.model.CatFactData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

interface CatFactRepository {
    suspend fun getData(): CatFactData?
}

class CatFactRepositoryImpl @Inject constructor(
    private val okhttpClient: OkHttpClient,
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
) : CatFactRepository {

    override suspend fun getData(): CatFactData? {
        val gistJsonAdapter = moshi.adapter(CatFactData::class.java)

        val request = Request.Builder()
            .url("https://catfact.ninja/fact")
            .build()

        okhttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            return gistJsonAdapter.fromJson(response.body!!.source())
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object CatFactRepositoryModule {
    @Provides
    fun provideCatFactRepository(
        okhttpClient: OkHttpClient,
        moshi: Moshi
    ): CatFactRepository {
        return CatFactRepositoryImpl(okhttpClient, moshi)
    }
}
