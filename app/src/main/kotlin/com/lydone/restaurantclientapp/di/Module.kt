package com.lydone.restaurantclientapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lydone.restaurantclientapp.data.ApiService
import com.lydone.restaurantclientapp.data.repository.CategoryRepositoryImpl
import com.lydone.restaurantclientapp.data.repository.DishRepositoryImpl
import com.lydone.restaurantclientapp.data.repository.OrderRepositoryImpl
import com.lydone.restaurantclientapp.domain.order.CategoryRepository
import com.lydone.restaurantclientapp.domain.order.DishRepository
import com.lydone.restaurantclientapp.domain.order.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideApiService() = Retrofit.Builder()
        .baseUrl("https://lydone-restaurant-backend-app.herokuapp.com/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build().create(ApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binding {

        @Binds
        fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository

        @Binds
        fun bindDishRepository(impl: DishRepositoryImpl): DishRepository

        @Binds
        fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
    }
}