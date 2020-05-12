package com.sfazleyrabbi.covid19tracker.ui.main.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sfazleyrabbi.covid19tracker.R
import com.sfazleyrabbi.covid19tracker.models.Country
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HOME_VIEW_STATE_BUNDLE_KEY
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeStateEvent
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeViewState
import com.sfazleyrabbi.covid19tracker.util.Constants.Companion.HELPLINE_PHONE
import com.sfazleyrabbi.covid19tracker.util.StateMessageCallback
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@FlowPreview
@ExperimentalCoroutinesApi
class HomeFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory
) : BaseHomeFragment(R.layout.fragment_home, viewModelFactory) {
    private val TAG: String = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Restore state after process death
        savedInstanceState?.let { inState ->
            (inState[HOME_VIEW_STATE_BUNDLE_KEY] as HomeViewState?)?.let { homeViewState ->
                viewModel.setViewState(homeViewState)
            }
        }
    }

    /**
     * !IMPORTANT!
     * Must save ViewState b/c in event of process death the LiveData in ViewModel will be lost
     */
    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        outState.putParcelable(
            HOME_VIEW_STATE_BUNDLE_KEY,
            viewState
        )
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        subscribeObservers()
        uiCommunicationListener.expandAppBar()
        call_now_btn.setOnClickListener {
            if(uiCommunicationListener.isPhoneCallPermissionGranted()){
                //if permission granted call then
                val dial = "tel:" + HELPLINE_PHONE
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { homeViewState ->
            homeViewState?.let {
                it.homeFields.let { homeFields ->
                    homeFields.country?.let {
                        setViewProperties(country = it)
                    }
                }
            }
        })

        viewModel.numActiveJobs.observe(viewLifecycleOwner, Observer { jobCounter ->
            uiCommunicationListener.displayProgressBar(viewModel.areAnyJobsActive())
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            stateMessage?.let {
                uiCommunicationListener.onResponseReceived(
                    response = it.response,
                    stateMessageCallback = object: StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )
            }
        })
    }

    private fun setViewProperties(country: Country){
        val decimalFormatter = DecimalFormat("#,###,###")

        country_name.text = country.country
        affected_count.text =  decimalFormatter.format(country.cases)
        death_count.text = decimalFormatter.format(country.deaths)
        recovered_count.text = decimalFormatter.format(country.recovered)
        active_count.text = decimalFormatter.format(country.active)
        critical_count.text = decimalFormatter.format(country.critical)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStateEvent(HomeStateEvent.CountryDataEvent())
    }

}
