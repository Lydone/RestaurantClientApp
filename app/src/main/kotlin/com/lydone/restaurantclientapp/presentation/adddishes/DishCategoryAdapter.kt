package com.lydone.restaurantclientapp.presentation.adddishes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.ViewHolderAddDishBinding
import com.lydone.restaurantclientapp.databinding.ViewHolderCategoryTitleBinding
import com.lydone.restaurantclientapp.domain.common.Category
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.presentation.common.AddDishViewHolder

class DishCategoryAdapter(
    private val category: Category,
    private val dishes: List<Dish>,
    private val onQuantityChange: (dish: Dish, quantity: Int) -> Unit,
    private val onDishClick: (Dish) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var idToQuantity = emptyMap<Dish, Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (ViewType.values()[viewType]) {
        ViewType.TITLE -> CategoryTitleViewHolder(
            ViewHolderCategoryTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        ViewType.DISH -> AddDishViewHolder(
            ViewHolderAddDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (ViewType.values()[getItemViewType(position)]) {
            ViewType.TITLE -> {
                (holder as CategoryTitleViewHolder).binding.root.text = category.name
            }
            ViewType.DISH -> {
                val dish = dishes[position - 1]
                with((holder as AddDishViewHolder).binding) {
                    root.setOnClickListener { onDishClick(dish) }
                    imageView.load(dish.url)
                    nameTextView.text = dish.name
                    priceTextView.text = root.context.getString(R.string.price_placeholder, dish.price)
                    addQuantityView.quantity = idToQuantity[dish] ?: 0
                    addQuantityView.onQuantityChange = { onQuantityChange(dish, it) }
                }
            }
        }
    }

    override fun getItemCount() = dishes.size + 1

    override fun getItemViewType(position: Int) = position.coerceAtMost(1)


    enum class ViewType {
        TITLE, DISH
    }


    class CategoryTitleViewHolder(val binding: ViewHolderCategoryTitleBinding) : RecyclerView.ViewHolder(binding.root)
}