package com.lydone.restaurantclientapp.domain.order

import com.lydone.restaurantclientapp.domain.common.Category

interface CategoryRepository {

    suspend fun getCategories(): List<Category>
}