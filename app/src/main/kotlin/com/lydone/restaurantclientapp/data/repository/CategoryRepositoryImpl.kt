package com.lydone.restaurantclientapp.data.repository

import com.lydone.restaurantclientapp.data.ApiService
import com.lydone.restaurantclientapp.domain.common.Category
import com.lydone.restaurantclientapp.domain.order.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val apiService: ApiService) : CategoryRepository {

    override suspend fun getCategories() = apiService.getCategories()
}