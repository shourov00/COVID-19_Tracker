package com.sfazleyrabbi.covid19tracker.ui.main.info

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sfazleyrabbi.covid19tracker.R
import com.sfazleyrabbi.covid19tracker.util.Constants.Companion.MY_GITHUB_PROFILE
import com.sfazleyrabbi.covid19tracker.util.Constants.Companion.MY_INSTAGRAM_PROFILE
import com.sfazleyrabbi.covid19tracker.util.Constants.Companion.MY_WEBSITE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_info.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class InfoFragment
@Inject
constructor(
    private val requestOptions: RequestOptions,
    private var requestManager: RequestManager
) : BaseInfoFragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        setupGlide()

        uiCommunicationListener.expandAppBar()

        requestManager.load(context?.getDrawable(R.drawable.me))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(my_image)

        website_link.setOnClickListener {
            openLink(MY_WEBSITE)
        }

        github_link.setOnClickListener {
            openLink(MY_GITHUB_PROFILE)
        }

        instagram_link.setOnClickListener {
            openLink(MY_INSTAGRAM_PROFILE)
        }
    }

    private fun toolbarSetup() {
        (activity as AppCompatActivity).supportActionBar?.show()
        context?.let {
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        it, R.color.colorAccent
                    )
                )
            )
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(
            context?.getDrawable(
                R.drawable.ic_arrow_back_white_24dp
            )
        )
        (activity as AppCompatActivity).app_bar?.elevation = 0F
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun setupGlide() {
        activity?.let {
            requestManager = Glide.with(it)
                .applyDefaultRequestOptions(requestOptions)
        }
    }

}
