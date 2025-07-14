package com.rcolejnr.rickmorty.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.rcolejnr.rickmorty.core.data.model.asEntity
import com.rcolejnr.rickmorty.core.database.dao.RMCharacterRemoteKeysDao
import com.rcolejnr.rickmorty.core.database.dao.RMDao
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.RMCharacterRemoteKeys
import com.rcolejnr.rickmorty.core.network.RMDataSourceNetwork
import com.rcolejnr.rickmorty.core.network.model.RMCharacterNetwork
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val rmDao: RMDao,
    private val rmRemoteKeysDao: RMCharacterRemoteKeysDao,
    private val network: RMDataSourceNetwork,
    private val name: String?,
    private val status: String?,
    private val gender: String?,
): RemoteMediator<Int, RMCharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RMCharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                Log.d("Errorrr", "appending ${remoteKeys?.nextKey}")
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }

            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.REFRESH -> return MediatorResult.Success(endOfPaginationReached = false)
        }

        try {
            val response = network.fetchCharacters(
                page ?: STARTING_PAGE_INDEX,
                name = name,
                status = status,
                gender = gender
            )
            val characters = response.results
            val endOfPaginationReached = false

            if (loadType == LoadType.REFRESH) {
                val ids = characters.map(RMCharacterNetwork::id)
                rmRemoteKeysDao.clearRemoteKeys(ids)
                rmDao.clearCharacters(ids)
            }

            val nextKey = if (endOfPaginationReached) null else page?.plus(1)
            val keys = characters.map {
                RMCharacterRemoteKeys(
                    id = it.id,
                    prevKey = null,
                    nextKey = nextKey
                )
            }

            rmRemoteKeysDao.insertAll(keys)
            rmDao.insertCharacters(characters.map(RMCharacterNetwork::asEntity))

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RMCharacterEntity>): RMCharacterRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.id?.let { user ->
                // Get the remote keys of the last item retrieved
                rmRemoteKeysDao.remoteKeysId(user)
            }
    }
}

private const val STARTING_PAGE_INDEX = 1