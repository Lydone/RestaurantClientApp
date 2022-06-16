package com.lydone.restaurantclientapp.domain.common

object Storage {

    var table: Int = 0

    var dishes: Map<Int, Dish> = emptyMap()

    var categories: Map<Int, Category> = emptyMap()
}