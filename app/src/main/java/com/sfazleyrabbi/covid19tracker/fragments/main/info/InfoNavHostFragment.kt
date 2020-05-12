package com.sfazleyrabbi.covid19tracker.fragments.main.info

import android.content.Context
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.sfazleyrabbi.covid19tracker.ui.main.MainActivity

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

class InfoNavHostFragment : NavHostFragment(){

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory = (activity as MainActivity).infoFragmentFactory
        super.onAttach(context)
    }

    companion object {
        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        // add fragment factory to bottom nav controller
        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): InfoNavHostFragment {
            var bundle: Bundle? = null
            if(graphId != 0){
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }

            val result = InfoNavHostFragment()

            if(bundle != null){
                result.arguments = bundle
            }
            return result
        }

    }
}