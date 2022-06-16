package com.lydone.restaurantclientapp.presentation.common

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView
import com.lydone.restaurantclientapp.R

class OffsetsItemDecoration(
    @Dimension
    private val offset: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        with(outRect) {
            top = if (position != 0) offset / 2 else offset
            bottom = if (position != parent.adapter?.itemCount?.minus(1)) offset / 2 else offset
        }
    }
}