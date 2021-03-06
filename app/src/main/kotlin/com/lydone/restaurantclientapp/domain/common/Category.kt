package com.lydone.restaurantclientapp.domain.common

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
)