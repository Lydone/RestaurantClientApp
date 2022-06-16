package com.lydone.restaurantclientapp.presentation.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.domain.common.Storage
import com.lydone.restaurantclientapp.domain.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {

    private var cart = mutableMapOf<Dish, Int>()

    private var _state = MutableStateFlow<State>(State.Data(cart.toMap()))
    var state = _state.asStateFlow()

    fun onAttach(cart: Map<Dish, Int>) {
        this.cart = cart.toMutableMap()
        _state.value = State.Data(cart.toMap())
    }

    fun onQuantityChange(dish: Dish, quantity: Int) {
        if (quantity == 0) {
            cart.remove(dish)
        } else {
            cart[dish] = quantity
        }
        _state.value = State.Data(cart.toMap())
    }

    fun onConfirmClick() {
        viewModelScope.launch {
            _state.update { State.Loading(it.dishes) }
            repository.addDishes(Storage.table, state.value.dishes)
            _state.update { State.NavigateBack(it.dishes) }
        }
    }


    sealed interface State {

        val dishes: Map<Dish, Int>

        data class Data(override val dishes: Map<Dish, Int>) : State

        data class Loading(override val dishes: Map<Dish, Int>) : State

        data class NavigateBack(override val dishes: Map<Dish, Int>) : State
    }
}