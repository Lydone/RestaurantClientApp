package com.lydone.restaurantclientapp.data.repository

import com.lydone.restaurantclientapp.data.ApiService
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.domain.common.Storage
import com.lydone.restaurantclientapp.domain.order.OrderRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val apiService: ApiService) : OrderRepository {
    override suspend fun getDishes(table: Int) = apiService.getOrder(table).mapKeys { Storage.dishes.getValue(it.key) }

    override suspend fun addDishes(table: Int, dishes: Map<Dish, Int>) {
        apiService.addDishesToOrder(table, dishes.mapKeys { it.key.id })
    }
}