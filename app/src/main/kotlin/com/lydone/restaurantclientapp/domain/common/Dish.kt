package com.lydone.restaurantclientapp.domain.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Dish(
    val id: Int,
    val url: String,
    val name: String,
    val description: String,
    val price: Int,
    val weight: Int,
    val categoryId: Int
) : Parcelable