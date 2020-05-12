package com.sfazleyrabbi.covid19tracker.ui.main.global

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.sfazleyrabbi.covid19tracker.R
import com.sfazleyrabbi.covid19tracker.models.Country
import com.sfazleyrabbi.covid19tracker.util.GenericViewHolder
import kotlinx.android.synthetic.main.layout_global_list_item.view.*
import java.text.DecimalFormat

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

class GlobalListAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG: String = "AppDebug"
    private val NO_MORE_RESULTS = -1
    private val GLOBAL_ITEM = 0

    private val NO_MORE_RESULTS_ITEM_MARKER = Country(
        "NONE",
        0,
        0,
        0,
        0,
        0
    )

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.country == newItem.country
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        GlobalRecyclerChangeCallBack(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    // custom callback for when recyclerview items are changed
    // it can use with diffUtil to detect changed between 2 lists
    internal inner class GlobalRecyclerChangeCallBack(
        private val adapter: GlobalListAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            NO_MORE_RESULTS -> {
                return GenericViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_no_more_result, parent, false)
                )
            }

            GLOBAL_ITEM -> {
                return GlobalViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_global_list_item,
                        parent,
                        false
                    ),
                    interaction
                )
            }

            else -> {
                return GlobalViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_global_list_item,
                        parent,
                        false
                    ),
                    interaction
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GlobalViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (differ.currentList[position].country != "NONE" && differ.currentList.size > 0) {
            return GLOBAL_ITEM
        }
        return NO_MORE_RESULTS
    }

    fun submitList(list: List<Country>?) {
        // add a callback and restore list position on submit
        // Wait for the recyclerview data set, after set the data restore the position state
        val commitCallBack = Runnable {
            interaction?.restoreListPosition()
        }

        differ.submitList(list, commitCallBack)
    }

    class GlobalViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Country) = with(itemView) {
            val decimalFormatter = DecimalFormat("#,###,###")

            // set the widgets
            country_name_list.text = item.country
            affected_count.text = decimalFormatter.format(item.cases).toString()
            death_count.text = decimalFormatter.format(item.deaths).toString()
            recovered_count.text = decimalFormatter.format(item.recovered).toString()
            active_count.text = decimalFormatter.format(item.active).toString()
        }
    }

    interface Interaction {
        // Restore list position after all items set to recycler view
        fun restoreListPosition()
    }
}