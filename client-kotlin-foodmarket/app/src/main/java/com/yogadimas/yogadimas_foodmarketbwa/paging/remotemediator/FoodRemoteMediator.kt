package com.yogadimas.yogadimas_foodmarketbwa.paging.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.RemoteKeys
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.room.FoodMarketDatabase
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.Endpoint
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.Wrapper
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.Data
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class FoodRemoteMediator(
    private val database: FoodMarketDatabase,
    private val apiService: Endpoint,
) : RemoteMediator<Int, FoodEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FoodEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val responseDataObservable: Observable<Wrapper<HomeResponse>> =
                apiService.home(page, state.config.pageSize)


            // Use withContext to switch to the IO dispatcher
            val responseData: List<Data> = withContext(Dispatchers.IO) {
                val wrapper = responseDataObservable.firstOrError().blockingGet()
                wrapper.data?.data ?: emptyList()
            }

            val endOfPaginationReached = responseData.isEmpty()

            val foodEntities: List<FoodEntity> = responseData.map { data ->
                Helpers.dataToFoodEntity(data)
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.foodDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = foodEntities.map {
                    RemoteKeys(id = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.foodDao().insert(foodEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }


    // untuk mengambil data dari RemoteKeys
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FoodEntity>): RemoteKeys? {

        // Ambil halaman terakhir yang tidak kosong
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }

        // Ambil item terakhir dari halaman tersebut
        val lastItem = lastPage?.data?.lastOrNull()

        // Kembalikan RemoteKeys berdasarkan id atau data lainnya dari lastItem
        return lastItem?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id.toString())
        }
    }

    // Mendapatkan remote key untuk item pertama dalam state
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FoodEntity>): RemoteKeys? {

        // Mendapatkan halaman pertama yang tidak kosong
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() }

        // Mendapatkan item pertama dari halaman tersebut
        val firstItem = firstPage?.data?.firstOrNull()

        // Mengembalikan remote key berdasarkan id item pertama
        return firstItem?.let { data ->

            // Memanggil DAO untuk mendapatkan RemoteKeys berdasarkan id item pertama
            database.remoteKeysDao().getRemoteKeysId(data.id.toString())

        }
    }

    // Mendapatkan remote key yang paling dekat dengan posisi saat ini
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FoodEntity>): RemoteKeys? {

        // Mendapatkan posisi anchor, yaitu posisi saat ini
        return state.anchorPosition?.let { position ->

            // Mendapatkan item yang paling dekat dengan posisi anchor
            state.closestItemToPosition(position)?.id?.let { id ->

                // Memanggil DAO untuk mendapatkan RemoteKeys berdasarkan id item tersebut
                database.remoteKeysDao().getRemoteKeysId(id.toString())
            }
        }
    }


}
