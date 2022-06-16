package com.lydone.restaurantclientapp.data

import com.lydone.restaurantclientapp.domain.common.Category
import com.lydone.restaurantclientapp.domain.common.Dish
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("api/dishes")
    suspend fun getDishDetails(): List<Dish>

    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    @GET("api/order/{table}")
    suspend fun getOrder(@Path("table") tableId: Int): Map<Int, Int>

    @POST("api/order/{table}")
    suspend fun addDishesToOrder(@Path("table") tableId: Int, @Body dishes: Map<Int, Int>)
}