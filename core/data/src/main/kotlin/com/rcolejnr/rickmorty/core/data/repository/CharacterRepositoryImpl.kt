package com.rcolejnr.rickmorty.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcolejnr.rickmorty.core.data.model.asEntity
import com.rcolejnr.rickmorty.core.data.util.FetchResult
import com.rcolejnr.rickmorty.core.database.dao.RMCharacterRemoteKeysDao
import com.rcolejnr.rickmorty.core.database.dao.RMDao
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.RMEpisodeEntity
import com.rcolejnr.rickmorty.core.database.model.RMLocationEntity
import com.rcolejnr.rickmorty.core.database.model.asRMCharacter
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.RMCharacter
import com.rcolejnr.rickmorty.core.network.RMDataSourceNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(
    private val rmDao: RMDao,
    private val remoteKeysDao: RMCharacterRemoteKeysDao,
    private val network: RMDataSourceNetwork,
): CharacterRepository {

    override fun fetchCharacters(
        characterFilters: CharacterFilters,
    ): Flow<PagingData<RMCharacterEntity>> {
        var name = ""
        var status = ""
        var gender = ""
        when (characterFilters) {
            CharacterFilters.RICK, CharacterFilters.MORTY -> {
                name = "%${characterFilters.filter}%"
            }
            CharacterFilters.DEAD, CharacterFilters.ALIVE -> {
                status = characterFilters.filter
            }
            CharacterFilters.MALE, CharacterFilters.FEMALE -> {
                gender = characterFilters.filter
            }
            CharacterFilters.NO_FILTER -> {}
        }

        val pagingSourceFactory = { rmDao.getCharacters(
            filterByName = name.isNotEmpty(),
            name = name,
            filterByStatus = status.isNotEmpty(),
            status = status,
            filterByGender = gender.isNotEmpty(),
            gender = gender
        ) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 2,
                initialLoadSize = 2
            ),
            remoteMediator = CharacterRemoteMediator(
                network = network,
                rmDao = rmDao,
                rmRemoteKeysDao = remoteKeysDao,
                characterFilter = characterFilters
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun fetchCharacterFavorites(ids: Set<Int>): Flow<List<RMCharacter>> =
        rmDao.getCharacters(
            useFilterIds = true,
            filterIds = ids
        )
            .map{ entities -> entities.asRMCharacter()}

    override fun fetchCharacter(id: Int): Flow<FetchResult<RMCharacterEntity>> = channelFlow {
        try {
            val character = network.fetchCharacter(
                id =  id
            )

            rmDao.insertCharacters(listOf(character.asEntity()))

            rmDao.getCharacter(id).collectLatest {
                send(FetchResult.Success(it))
            }

        } catch (e: IOException) {
            Log.e(TAG, e.localizedMessage ?: "IOException")
            send(FetchResult.Failure(e.localizedMessage ?: IO_Error))

        } catch (e: HttpException) {
            Log.e(TAG, e.localizedMessage ?: "HTTPException")
            send(FetchResult.Failure(e.localizedMessage ?: NETWORK_ERROR))
        }
    }

    override fun fetchEpisode(id: Int): Flow<FetchResult<RMEpisodeEntity>> = channelFlow {
        try {
            val episode = network.fetchEpisode(
                id =  id
            )

            rmDao.insertEpisode(episode.asEntity())

            rmDao.getEpisode(id).collectLatest {
                send(FetchResult.Success(it))
            }

        } catch (e: IOException) {
            Log.e(TAG, e.localizedMessage ?: "IOException")
            send(FetchResult.Failure(e.localizedMessage ?: IO_Error))

        } catch (e: HttpException) {
            Log.e(TAG, e.localizedMessage ?: "HTTPException")
            send(FetchResult.Failure(e.localizedMessage ?: NETWORK_ERROR))
        }
    }

    override fun fetchLocation(id: Int): Flow<FetchResult<RMLocationEntity>> = channelFlow {
        try {
            val location = network.fetchLocation(
                id =  id
            )

            rmDao.insertLocation(location.asEntity())

            rmDao.getLocation(id).collectLatest {
                send(FetchResult.Success(it))
            }

        } catch (e: IOException) {
            Log.e(TAG, e.localizedMessage ?: "IOException")
            send(FetchResult.Failure(e.localizedMessage ?: IO_Error))

        } catch (e: HttpException) {
            Log.e(TAG, e.localizedMessage ?: "HTTPException")
            send(FetchResult.Failure(e.localizedMessage ?: NETWORK_ERROR))
        }
    }

}

private const val TAG = "CharacterRepositoryImpl"
private const val IO_Error = "Error loading data"
private const val NETWORK_ERROR = "Network error"