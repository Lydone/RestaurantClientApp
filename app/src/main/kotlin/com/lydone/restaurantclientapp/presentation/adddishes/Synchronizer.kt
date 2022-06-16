package com.lydone.restaurantclientapp.presentation.adddishes

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class Synchronizer(
    recyclerView: RecyclerView,
    tabLayout: TabLayout,
    titlePositionToTabPosition: List<Pair<Int, Int>>
) {

    private var isScrollingProgrammatically = false

    private val onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (isScrollingProgrammatically) return
            val visiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            for (i in titlePositionToTabPosition.lastIndex downTo 0) {
                if (titlePositionToTabPosition[i].first <= visiblePosition) {
                    requireNotNull(tabLayout.getTabAt(titlePositionToTabPosition[i].second)).let { tab ->
                        if (!tab.isSelected) {
                            tabLayout.removeOnTabSelectedListener(onTabSelectedListener)
                            tab.select()
                            tabLayout.addOnTabSelectedListener(onTabSelectedListener)
                        }
                    }
                    return
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                isScrollingProgrammatically = false
            }
        }
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            recyclerView.smoothSnapToPosition(titlePositionToTabPosition.first { it.second == tab.position }.first)
            isScrollingProgrammatically = true
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}

        override fun onTabReselected(tab: TabLayout.Tab) {

        }

    }

    init {
        tabLayout.clearOnTabSelectedListeners()
        recyclerView.clearOnScrollListeners()
        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
        recyclerView.addOnScrollListener(onScrollListener)
    }

    fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }
}