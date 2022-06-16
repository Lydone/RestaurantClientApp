package com.lydone.restaurantclientapp.presentation.adddishes

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.lydone.restaurantclientapp.R
import com.lydone.restaurantclientapp.databinding.FragmentAddDishesBinding
import com.lydone.restaurantclientapp.presentation.common.OffsetsItemDecoration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddDishesFragment : Fragment(R.layout.fragment_add_dishes) {

    private val viewModel: AddDishesViewModel by viewModels()

    private var adapters: List<DishCategoryAdapter>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentAddDishesBinding.bind(view)) {
            recyclerView.addItemDecoration(OffsetsItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_medium)))
            toConfirmationButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_confirmation,
                    bundleOf("cart" to viewModel.cart.value)
                )
            }
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        viewModel.state.collect { state ->
                            val groups =
                                state.dishes.groupBy { dish -> state.categories.first { it.id == dish.categoryId } }
                                    .toSortedMap(compareBy { it.id })
                            recyclerView.adapter = ConcatAdapter(
                                ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
                                groups.map { (category, dishes) ->
                                    DishCategoryAdapter(
                                        category,
                                        dishes,
                                        { dish, quantity -> viewModel.onSelectionUpdate(dish, quantity) },
                                        {
                                            findNavController().navigate(
                                                R.id.action_dish_details,
                                                bundleOf("dish" to it)
                                            )
                                        }
                                    )
                                }.also { adapters = it },
                            )
                            val titlePositionToTabPosition = mutableListOf<Pair<Int, Int>>()
                            tabLayout.removeAllTabs()
                            var cnt = 0
                            for ((category, dishes) in groups) {
                                val tab = tabLayout.newTab().setText(category.name)
                                tabLayout.addTab(tab)
                                titlePositionToTabPosition.add(cnt to tab.position)
                                cnt += dishes.size + 1
                            }
                            Synchronizer(recyclerView, tabLayout, titlePositionToTabPosition)
                        }
                    }
                    launch {
                        viewModel.cart.collect { cart ->
                            adapters?.forEach { it.idToQuantity = cart }
                            toConfirmationButton.isVisible = cart.isNotEmpty()
                            toConfirmationButton.text = getString(
                                R.string.to_confirmation_placeholder,
                                cart.asIterable().fold(0) { acc, entry -> acc + entry.key.price * entry.value })
                        }
                    }
                }
            }
        }
    }
}