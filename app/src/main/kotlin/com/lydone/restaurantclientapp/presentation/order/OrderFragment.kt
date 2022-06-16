package com.lydone.restaurantclientapp.presentation.order

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.FragmentOrderBinding
import com.lydone.restaurantclientapp.domain.common.Storage
import com.lydone.restaurantclientapp.presentation.common.OffsetsItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.fragment_order) {

    private val viewModel: OrderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.table = 0
        with(FragmentOrderBinding.bind(view)) {
            val adapter = OrderAdapter { dish ->
                findNavController().navigate(R.id.action_dish_details, bundleOf("dish" to dish))
            }.also { recyclerView.adapter = it }
            recyclerView.addItemDecoration(
                OffsetsItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_medium))
            )
            swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }
            addDishesButton.setOnClickListener { findNavController().navigate(R.id.action_add_dishes) }
            callWaiterButton.setOnClickListener {
                Snackbar.make(
                    view,
                    R.string.waiter_has_been_called,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            lifecycleScope.launch {
                viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                    emptyOrderTextView.isVisible = state is OrderViewModel.State.Order && state.dishes.isEmpty()
                    swipeRefreshLayout.isRefreshing = state is OrderViewModel.State.Loading
                    totalTextView.isVisible = state.dishes.isNotEmpty()
                    totalTextView.text = getString(
                        R.string.total_price_placeholder,
                        state.dishes.entries.sumOf { it.key.price * it.value })
                    adapter.dishes = state.dishes
                }
            }
        }
    }

}