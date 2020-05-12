package com.sfazleyrabbi.covid19tracker.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Fazley Rabbi on 19 April 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

class TopSpacingItemDecoration(
    private val padding: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = padding
    }
}