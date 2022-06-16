package com.lydone.restaurantclientapp.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.restaurantclientapp.domain.common.Category
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.domain.common.Storage
import com.lydone.restaurantclientapp.domain.order.CategoryRepository
import com.lydone.restaurantclientapp.domain.order.DishRepository
import com.lydone.restaurantclientapp.domain.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val dishRepository: DishRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading(emptyMap()))
    val state = _state.asStateFlow()

    var dishes: Deferred<Map<Int, Dish>>? = null

    var categories: Deferred<Map<Int, Category>>? = null

    init {
        dishes = viewModelScope.async { dishRepository.getDishes().associateBy { it.id }.also { Storage.dishes = it } }
        categories = viewModelScope.async {
            categoryRepository.getCategories().associateBy { it.id }.also { Storage.categories = it }
        }
    }

    var table: Int
        get() = Storage.table
        set(value) {
            Storage.table = value
            loadOrder(value)
        }

    fun onRefresh() = loadOrder(table)

    private fun loadOrder(table: Int) {
        viewModelScope.launch {
            _state.update { State.Loading(it.dishes) }
            dishes?.await()
            categories?.await()
            _state.value = State.Order(orderRepository.getDishes(table))
        }
    }


    sealed class State(val dishes: Map<Dish, Int>) {
        class Loading(dishes: Map<Dish, Int>) : State(dishes)
        class Order(dishes: Map<Dish, Int>) : State(dishes)
    }
}