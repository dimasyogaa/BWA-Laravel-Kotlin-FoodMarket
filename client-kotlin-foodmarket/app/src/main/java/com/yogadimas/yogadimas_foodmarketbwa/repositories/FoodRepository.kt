package com.yogadimas.yogadimas_foodmarketbwa.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.room.FoodMarketDatabase
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.Endpoint
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.Wrapper
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse
import com.yogadimas.yogadimas_foodmarketbwa.paging.remotemediator.FoodRemoteMediator
import com.yogadimas.yogadimas_foodmarketbwa.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class FoodRepository private constructor(
    private val foodMarketDatabase: FoodMarketDatabase, private val apiService: Endpoint,
) {

    suspend fun insertFood(food: List<FoodEntity>) {
        foodMarketDatabase.foodDao().insert(food)
    }

    suspend fun deleteFood(food: FoodEntity) {
        foodMarketDatabase.foodDao().delete(food)
    }

    suspend fun deleteFoodAll() {
        foodMarketDatabase.apply {
            withTransaction {
                remoteKeysDao().deleteRemoteKeys()
                foodDao().deleteAll()
            }
        }

    }


    // fun getAllFoods(): Flow<List<FoodEntity>> {
    //     return foodDao.getAllFoods().asFlow()
    // }

    fun getAllFoods(): Flow<PagingData<FoodEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 15),
            remoteMediator = FoodRemoteMediator(foodMarketDatabase, apiService),
            pagingSourceFactory = { foodMarketDatabase.foodDao().getAllFoods() }
        ).flow
    }

    fun getFoodById(id: Long): Flow<Result<FoodEntity>> = flow {
        try {
            emit(Result.Loading())
            val data = foodMarketDatabase.foodDao().getFoodById(id)
            emit(Result.Success(data))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Result.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllFoodsNoPaging(): Flow<Result<List<FoodEntity>>> = flow {
        try {
            emit(Result.Loading())
            val local = foodMarketDatabase.foodDao().getAllFoodsNoPaging()
            emit(Result.Success(local))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Result.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun getAllFoodsByNewTaste(
        isLoading: MutableLiveData<Boolean>,
        data: MutableLiveData<Wrapper<HomeResponse>>,
        isError: MutableLiveData<Pair<Boolean?, String?>>
    ) {
        isLoading.postValue(true)
        try {
            val response = apiService.allFoodByTypes("new_food")
            data.postValue(response)
            isLoading.postValue(false)
            isError.postValue(Pair(false, null))
        } catch (e: UnknownHostException) {
            isLoading.postValue(false)
            isError.postValue(Pair(true, e.localizedMessage))
        } catch (e: HttpException) {
            isLoading.postValue(false)
            isError.postValue(Pair(true, e.localizedMessage))
        } catch (e: SocketTimeoutException) {
            isLoading.postValue(false)
            isError.postValue(Pair(true, e.localizedMessage))
        } catch (e: SocketException) {
            isLoading.postValue(false)
            isError.postValue(Pair(true, e.localizedMessage))
        }
    }

    fun getAllFoodsByPopular(): Flow<Result<Wrapper<HomeResponse>>> = flow {
        try {
            emit(Result.Loading())
            // val data = foodMarketDatabase.foodDao().getAllFoodsByPopular()
            val data = apiService.allFoodByTypes("popular")
            emit(Result.Success(data))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Result.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)



    fun getAllFoodsByRecommended(): Flow<Result<Wrapper<HomeResponse>>> = flow {
        try {
            emit(Result.Loading())
            // val data = foodMarketDatabase.foodDao().getAllFoodsByRecommended()
            val data = apiService.allFoodByTypes("recommended")
            emit(Result.Success(data))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Result.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)


    companion object {

        @Volatile
        private var instance: FoodRepository? = null
        fun getInstance(
            foodMarketDatabase: FoodMarketDatabase,
            apiService: Endpoint,
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(foodMarketDatabase, apiService)
            }.also { instance = it }
    }

}