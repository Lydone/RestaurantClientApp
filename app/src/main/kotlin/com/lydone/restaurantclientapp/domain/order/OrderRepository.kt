package com.lydone.restaurantclientapp.domain.order

import com.lydone.restaurantclientapp.domain.common.Dish

interface OrderRepository {

    suspend fun getDishes(table: Int): Map<Dish, Int>

    suspend fun addDishes(table: Int, dishes: Map<Dish, Int>)
}