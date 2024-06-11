package com.yogadimas.yogadimas_foodmarketbwa.data.networking

import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.Wrapper
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.AuthResponse
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.checkout.CheckoutResponse
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.TransactionResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Observable<Wrapper<AuthResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("houseNumber") houseNumber: String,
        @Field("phoneNumber") phoneNumber: String,
    ): Observable<Wrapper<AuthResponse>>

    @Multipart
    @POST("user/photo")
    fun registerPhoto(@Part profileImage: MultipartBody.Part): Observable<Wrapper<Any>>


    @GET("food")
    fun home(
        @Query("page") page: Int?,
        @Query("per_page") size: Int?,
    ): Observable<Wrapper<HomeResponse>>

    @GET("food/types")
    suspend fun allFoodByTypes(
        @Query("types") type: String,
    ):Wrapper<HomeResponse>

    @FormUrlEncoded
    @POST("checkout")
    fun checkout(
        @Field("food_id") food_id: String,
        @Field("user_id") user_id: String,
        @Field("quantity") quantity: String,
        @Field("total") total: String,
        @Field("status") status: String,
    ): Observable<Wrapper<CheckoutResponse>>

    @GET("transaction")
    fun transaction(): Observable<Wrapper<TransactionResponse>>

    @FormUrlEncoded
    @POST("transaction/{id}")
    fun transactionUpdate(
        @Path(value = "id") userId: String,
        @Field("status") status: String,
    ): Observable<Wrapper<Any>>
}