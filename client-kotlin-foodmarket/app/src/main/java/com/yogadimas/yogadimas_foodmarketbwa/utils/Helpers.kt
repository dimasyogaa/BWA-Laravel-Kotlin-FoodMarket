package com.yogadimas.yogadimas_foodmarketbwa.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.yogadimas.yogadimas_foodmarketbwa.BuildConfig
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.entities.FoodEntity
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.Endpoint
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.Wrapper
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.Data
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse
import io.reactivex.Observable
import kotlinx.coroutines.rx2.await
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Helpers {

    fun showLoading(view: View, isLoading: Boolean) {
       view.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun TextView.formatPrice(value: String) {
        this.text = getCurrencyIDR(value.toDouble())
    }

    fun getCurrencyIDR(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "IDR " + format.format(price).replace(",".toRegex(), ".")
    }

    fun getDefaultGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                val formatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                formatServer.timeZone = TimeZone.getTimeZone("UTC")
                formatServer.parse(json.asString)
            })
            .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                val format = SimpleDateFormat("", Locale.ENGLISH)
                format.timeZone = TimeZone.getTimeZone("UTC")
                if (src != null) {
                    JsonPrimitive(format.format(src))
                } else {
                    null
                }
            }).create()
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.convertLongToTime(formatTanggal: String): String {
        val date = Date(this)
        val format = SimpleDateFormat(formatTanggal, Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
        return format.format(date)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun replaceLocalhostWithBaseUrl(url: String?): String {
        return url?.replace("http://localhost:8000/", BuildConfig.BASE_URL) ?: BuildConfig.BASE_URL
    }

    fun dataToFoodEntity(data: Data): FoodEntity {
        return FoodEntity(
            id = data.id?.toLong() ?: 0L,
            picturePath = data.picturePath,
            name = data.name,
            description = data.description,
            ingredients = data.ingredients,
            price = data.price,
            rate = data.rate,
            types = data.types ?: "",
            deletedAt = data.deletedAt,
            createdAt = data.createdAt,
            updatedAt = data.updatedAt,
        )
    }

    fun foodEntityToData(foodEntity: FoodEntity): Data {
        return Data(
            id = foodEntity.id.toInt(),
            picturePath = foodEntity.picturePath,
            name = foodEntity.name,
            description = foodEntity.description,
            ingredients = foodEntity.ingredients,
            price = foodEntity.price,
            rate = foodEntity.rate,
            types = foodEntity.types,
            deletedAt = foodEntity.deletedAt,
            createdAt = foodEntity.createdAt,
            updatedAt = foodEntity.updatedAt
        )
    }

    fun List<Data>.toFoodEntityList(): List<FoodEntity> {
        return this.map { dataToFoodEntity(it) }
    }


    suspend fun fetchHomeResponse(apiService: Endpoint, page: Int, pageSize: Int): HomeResponse? {
        val responseData: Observable<Wrapper<HomeResponse>> = apiService.home(page, pageSize)
        val wrapper = responseData.firstOrError().await()
        return wrapper.data
    }

    suspend fun isEndOfPaginationReached(responseData: Observable<Wrapper<HomeResponse>>): Boolean {
        return responseData.isEmpty().await()
    }



}
