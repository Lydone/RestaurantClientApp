package com.lydone.restaurantclientapp.presentation.adddishes

import androidx.lifecycle.ViewModel
import com.lydone.restaurantclientapp.domain.common.Category
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.domain.common.Storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class AddDishesViewModel : ViewModel() {

    private var dishCart = mutableMapOf<Dish, Int>()

    private val _state = MutableStateFlow(State.Result(Storage.categories.values.toList(), Storage.dishes.values.toList()))
    val state = _state.asStateFlow()

    private val _cart = MutableStateFlow(emptyMap<Dish, Int>())
    val cart = _cart.asStateFlow()

    fun onSelectionUpdate(dish: Dish, quantity: Int) {
        if (quantity == 0) {
            dishCart.remove(dish)
        } else {
            dishCart[dish] = quantity
        }
        _cart.value = dishCart.toMap()
    }


    sealed class State {

        data class Result(
            val categories: List<Category>,
            val dishes: List<Dish>
        ) : State()
    }
}