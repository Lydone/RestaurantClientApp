package com.lydone.restaurantclientapp.presentation.confirmation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.ViewHolderAddDishBinding
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.presentation.common.AddDishViewHolder

class ConfirmationAdapter(
    private val onDishClick: (Dish) -> Unit,
    private val onQuantityChange: (Dish, Int) -> Unit
) : RecyclerView.Adapter<AddDishViewHolder>() {

    var cart: Map<Dish, Int> = emptyMap()
        set(value) {
            field = value
            dishes = value.keys.toList()
            notifyDataSetChanged()
        }

    private var dishes: List<Dish> = cart.keys.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddDishViewHolder(
        ViewHolderAddDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AddDishViewHolder, position: Int) {
        val dish = dishes[position]
        with(holder.binding) {
            root.setOnClickListener { onDishClick(dish) }
            imageView.load(dish.url)
            nameTextView.text = dish.name
            priceTextView.text = root.context.getString(R.string.price_placeholder, dish.price)
            addQuantityView.quantity = cart[dish] ?: 0
            addQuantityView.onQuantityChange = { onQuantityChange(dish, it) }
        }
    }

    override fun getItemCount() = dishes.size
}