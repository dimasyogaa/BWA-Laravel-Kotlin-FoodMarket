package com.yogadimas.yogadimas_foodmarketbwa.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.Wrapper
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse
import com.yogadimas.yogadimas_foodmarketbwa.repositories.FoodRepository
import com.yogadimas.yogadimas_foodmarketbwa.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFoodTypesViewmodel(private val foodRepository: FoodRepository) : ViewModel() {


    val foods: LiveData<PagingData<FoodEntity>> =
        foodRepository.getAllFoods().asLiveData().cachedIn(viewModelScope)

    val foodsNoPaging: LiveData<Result<List<FoodEntity>>> =
        foodRepository.getAllFoodsNoPaging().asLiveData()

    // =============================================================================================

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _newFood = MutableLiveData<Wrapper<HomeResponse>>()
    val newFood: LiveData<Wrapper<HomeResponse>> = _newFood

    private val _isError = MutableLiveData<Pair<Boolean?, String?>>()
    val isError: LiveData<Pair<Boolean?, String?>> = _isError

    fun getNewFood() {
        executeSuspendFunction {
            foodRepository.getAllFoodsByNewTaste(
                _isLoading,
                _newFood,
                _isError
            )
        }
    }

    // =============================================================================================

    /** cara pendek
     * ada cara yang lebih singkat dan efektif untuk mengelola beberapa pemicu pembaruan (refresh triggers) menggunakan LiveData. Anda dapat menggunakan MediatorLiveData untuk menggabungkan beberapa sumber LiveData dan mengelola pembaruan mereka dengan lebih efisien.
     *
     * tapi getAllFoods harus diekseksi pada onResume
     */
    /*private val _refreshTrigger = MutableLiveData<RefreshAction>()



    val getAllFoods: LiveData<Result<Wrapper<HomeResponse>>> = _refreshTrigger.switchMap { action ->
        when (action) {
            RefreshAction.Popular -> foodRepository.getAllFoodsByPopular().asLiveData()
            RefreshAction.Recommended -> foodRepository.getAllFoodsByRecommended().asLiveData()
        }
    }

    fun refreshFoods(action: RefreshAction) {
        _refreshTrigger.value = action
    }*/


    // cara panjang
    private val _refreshFoodPopular = MutableLiveData<Unit>()
    val getAllFoodsByPopular: LiveData<Result<Wrapper<HomeResponse>>> = _refreshFoodPopular.switchMap {
        foodRepository.getAllFoodsByPopular().asLiveData()
    }
    fun refreshPopularFoods() {
        _refreshFoodPopular.value = Unit
    }



    private val _refreshFoodRecommended = MutableLiveData<Unit>()
    val getAllFoodsByRecommended: LiveData<Result<Wrapper<HomeResponse>>> = _refreshFoodRecommended.switchMap {
        foodRepository.getAllFoodsByRecommended().asLiveData()
    }
    fun refreshRecommendedFoods() {
        _refreshFoodRecommended.value = Unit
    }

    // no refresh
    /*val getAllFoodsByPopularNoRefresh: LiveData<Result<Wrapper<HomeResponse>>> =
        foodRepository.getAllFoodsByPopular().asLiveData()


    val getAllFoodsByRecommendedNoRefresh: LiveData<Result<Wrapper<HomeResponse>>> =
        foodRepository.getAllFoodsByRecommended().asLiveData()*/

    // =============================================================================================

    fun getFoodById(id: Long): LiveData<Result<FoodEntity>> =
        foodRepository.getFoodById(id).asLiveData()

    // =============================================================================================

    fun deleteFoodAll() = executeSuspendFunction { foodRepository.deleteFoodAll() }

    // =============================================================================================

    private fun executeSuspendFunction(suspendFunction: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            suspendFunction()
        }
    }

    // FoodEntity
    // var foodList = MutableLiveData<List<FoodEntity>>()
    //     private set
    //
    // var foodDetail = MutableLiveData<FoodEntity>()
    //     private set
    //
    // var newTasteList = MutableLiveData<List<FoodEntity>>()
    //     private set
    //
    // var popularList = MutableLiveData<List<FoodEntity>>()
    //     private set
    //
    // var recommendedList = MutableLiveData<List<FoodEntity>>()
    //     private set


    // Data
    // var newTasteListData = MutableLiveData<List<Data>>()
    //     private set
    //
    // var popularListData = MutableLiveData<List<Data>>()
    //     private set
    //
    // var recommendedListData = MutableLiveData<List<Data>>()
    //     private set


    // fun setFood(type: String = "", value: List<Data>) = viewModelScope.launch {
    //     val data: List<Data> = value
    //     val foodEntity: List<FoodEntity> = data.map(Helpers::dataToFoodEntity)
    //     when (type) {
    //         HomeFragment.NEW_FOOD -> {
    //             newTasteListData.value = value
    //         }
    //
    //         HomeFragment.POPULAR -> {
    //             popularListData.value = value
    //         }
    //
    //         HomeFragment.RECOMMENDED -> {
    //             recommendedListData.value = value
    //         }
    //
    //         else -> foodRepository.insert(foodEntity)
    //     }
    // }


    // fun getAllFoods() = viewModelScope.launch {
    //     foodRepository.getAllFoods().collect { foods ->
    //         foodList.value = foods
    //     }
    // }
    //
    // fun getFoodById(id: Long) = viewModelScope.launch {
    //     foodRepository.getFoodById(id).collect { food ->
    //         foodDetail.value = food
    //     }
    // }
    //
    // fun getAllFoodsByNewTaste() = viewModelScope.launch {
    //     foodRepository.getAllFoodsByNewTaste().collect { foods ->
    //         newTasteList.value = foods
    //     }
    // }
    //
    // fun getAllFoodsByPopular() = viewModelScope.launch {
    //     foodRepository.getAllFoodsByPopular().collect { foods ->
    //         popularList.value = foods
    //     }
    // }
    //
    // fun getAllFoodsByRecommended() = viewModelScope.launch {
    //     foodRepository.getAllFoodsByRecommended().collect { foods ->
    //         recommendedList.value = foods
    //     }
    // }


}