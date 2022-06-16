package com.lydone.restaurantclientapp.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.BottomSheetDialogFragmentDishBinding
import com.lydone.restaurantclientapp.domain.common.Dish

class DishBottomSheetDialogFragment() : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.bottom_sheet_dialog_fragment_dish, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dish = requireNotNull(requireArguments().getParcelable<Dish>(KEY_DISH))
        with(BottomSheetDialogFragmentDishBinding.bind(view)) {
            imageView.load(dish.url)
            nameTextView.text = dish.name
            descriptionTextView.text =
                root.context.getString(R.string.dish_description_placeholder, dish.description, dish.weight)
        }
    }

    companion object {

        private const val KEY_DISH = "dish"
    }
}