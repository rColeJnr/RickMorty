package com.rcolejnr.rickmorty.core.network

import com.rcolejnr.rickmorty.core.network.model.RMCharacterNetwork
import com.rcolejnr.rickmorty.core.network.model.RMCharacterResponse
import com.rcolejnr.rickmorty.core.network.model.RMEpisodeNetwork
import com.rcolejnr.rickmorty.core.network.model.RMLocationNetwork
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RMRetrofitNetworkApi {

    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("gender") gender: String?,
    ): RMCharacterResponse

    @GET("character/{id}")
    suspend fun fetchCharacter(
        @Path("id") id: Int
    ): RMCharacterNetwork

    @GET("location")
    suspend fun fetchLocation(
        @Path("id") id: Int
    ): RMLocationNetwork

    @GET("episode")
    suspend fun fetchEpisode(
        @Path("id") id: Int
    ): RMEpisodeNetwork
}

@Singleton
internal class RMRetrofitNetwork @Inject constructor(): RMDataSourceNetwork {

    private val networkApi = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .build()
        .create(RMRetrofitNetworkApi::class.java)

    override suspend fun fetchCharacters(
        page: Int,
        name: String?,
        status: String?,
        gender: String?
    ): RMCharacterResponse =
        networkApi.fetchCharacters(page = page, name = name, status = status, gender = gender)

    override suspend fun fetchCharacter(id: Int): RMCharacterNetwork =
        networkApi.fetchCharacter(id)

    override suspend fun fetchEpisode(id: Int): RMEpisodeNetwork =
        networkApi.fetchEpisode(id)

    override suspend fun fetchLocation(id: Int): RMLocationNetwork =
        networkApi.fetchLocation(id)

}