package com.lydone.restaurantclientapp.presentation.confirmation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.FragmentConfirmationBinding
import com.lydone.restaurantclientapp.domain.common.Dish
import com.lydone.restaurantclientapp.presentation.common.OffsetsItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmationFragment : Fragment(R.layout.fragment_confirmation) {

    private val viewModel by viewModels<ConfirmationViewModel>()

    private var adapter: ConfirmationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onAttach(requireArguments()["cart"] as Map<Dish, Int>)
        with(FragmentConfirmationBinding.bind(view)) {
            recyclerView.addItemDecoration(OffsetsItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_medium)))
            recyclerView.adapter = ConfirmationAdapter(
                { findNavController().navigate(R.id.action_dish_details, bundleOf("dish" to it)) },
                { dish, quantity -> viewModel.onQuantityChange(dish, quantity) }
            ).also { adapter = it }
            confirmButton.setOnClickListener { viewModel.onConfirmClick() }
            lifecycleScope.launch {
                viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                    if (it is ConfirmationViewModel.State.NavigateBack) {
                        findNavController().popBackStack(R.id.orderFragment, false)
                    }
                    if (it.dishes.isEmpty()) findNavController().popBackStack()
                    circularProgressIndicator.isVisible = it is ConfirmationViewModel.State.Loading
                    recyclerView.isVisible = it !is ConfirmationViewModel.State.Loading
                    imageView.isVisible = it !is ConfirmationViewModel.State.Loading
                    totalTextView.isVisible = it !is ConfirmationViewModel.State.Loading
                    confirmButton.isVisible = it !is ConfirmationViewModel.State.Loading
                    adapter?.cart = it.dishes
                    totalTextView.text = getString(
                        R.string.total_price_placeholder,
                        it.dishes.asIterable().fold(0) { acc, entry -> acc + entry.key.price * entry.value })
                }
            }
        }
    }
}