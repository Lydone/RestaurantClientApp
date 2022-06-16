package com.lydone.restaurantclientapp.domain.order

import com.lydone.restaurantclientapp.domain.common.Dish

interface DishRepository {

    suspend fun getDishes(): List<Dish>
}