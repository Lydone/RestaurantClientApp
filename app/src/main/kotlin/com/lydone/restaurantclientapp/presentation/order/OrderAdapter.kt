package com.lydone.restaurantclientapp.presentation.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.ViewHolderOrderBinding
import com.lydone.restaurantclientapp.domain.common.Dish
import java.util.function.BiFunction

class OrderAdapter(private val onClick: (Dish) -> Unit) : ListAdapter<Pair<Dish, Int>, OrderAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Pair<Dish, Int>>() {

        override fun areItemsTheSame(oldItem: Pair<Dish, Int>, newItem: Pair<Dish, Int>) =
            oldItem.first.id == newItem.first.id

        override fun areContentsTheSame(oldItem: Pair<Dish, Int>, newItem: Pair<Dish, Int>) =
            oldItem.first.name == newItem.first.name
                    && oldItem.first.description == newItem.first.description
                    && oldItem.first.price == newItem.first.price
                    && oldItem.second == newItem.second

    }
) {

    var dishes: Map<Dish, Int> = emptyMap()
        set(value) {
            field = value
            submitList(value.toList())
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ViewHolderOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val dish: Dish
            val count: Int
            with(getItem(position)) {
                dish = first
                count = second
            }
            imageView.load(dish.url)
            nameTextView.text = dish.name
            priceTextView.text = with(root.context) {
                if (count == 1) {
                    getString(
                        R.string.price_placeholder,
                        dish.price
                    )
                } else {
                    getString(R.string.quantity_price_placeholder, count, dish.price)
                }
            }
            root.setOnClickListener { onClick(dish) }
        }
    }


    class ViewHolder(val binding: ViewHolderOrderBinding) : RecyclerView.ViewHolder(binding.root)
}