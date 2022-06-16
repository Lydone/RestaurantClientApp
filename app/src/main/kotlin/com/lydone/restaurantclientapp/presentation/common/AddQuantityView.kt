package com.lydone.restaurantclientapp.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.lydone.restaurantclientapp.databinding.ViewAddQuantityBinding

class AddQuantityView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private val binding = ViewAddQuantityBinding.inflate(LayoutInflater.from(context), this)

    var quantity = 0
        set(value) {
            field = value
            with(binding) {
                addButton.visibility = if (value == 0) View.VISIBLE else View.INVISIBLE
                minusButton.visibility = if (value != 0) View.VISIBLE else View.INVISIBLE
                textView.visibility = if (value != 0) View.VISIBLE else View.INVISIBLE
                textView.text = quantity.toString()
                plusButton.visibility = if (value != 0) View.VISIBLE else View.INVISIBLE
            }
        }

    var onQuantityChange: (Int) -> Unit = {}

    init {
        with(binding) {
            quantity = 0
            addButton.setOnClickListener { onQuantityChange(++quantity) }
            minusButton.setOnClickListener { onQuantityChange(--quantity) }
            plusButton.setOnClickListener { onQuantityChange(++quantity) }
        }
    }
}