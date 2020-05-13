package com.sfazleyrabbi.covid19tracker.ui.main.global

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.sfazleyrabbi.covid19tracker.R
import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GLOBAL_VIEW_STATE_BUNDLE_KEY
import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalViewState
import com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel.loadInitialList
import com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel.refreshFromCache
import com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel.setLayoutManagerState
import com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel.setQuery
import com.sfazleyrabbi.covid19tracker.util.StateMessageCallback
import com.sfazleyrabbi.covid19tracker.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_global.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@FlowPreview
@ExperimentalCoroutinesApi
class GlobalFragment
@Inject
constructor(
    val viewmodelFactory: ViewModelProvider.Factory
) : BaseGlobalFragment(R.layout.fragment_global, viewmodelFactory),
    GlobalListAdapter.Interaction, SwipeRefreshLayout.OnRefreshListener {
    private val TAG: String = "AppDebug"

    private lateinit var searchView: SearchView
    private lateinit var recyclerAdapter: GlobalListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let { inState ->
            (inState[GLOBAL_VIEW_STATE_BUNDLE_KEY] as GlobalViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    /**
     * !IMPORTANT!
     * Must save ViewState b/c in event of process death the LiveData in ViewModel will be lost
     */
    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        //clear the list. Don't want to save a large list to bundle.
        viewState?.globalFields?.countryList = ArrayList()

        outState.putParcelable(
            GLOBAL_VIEW_STATE_BUNDLE_KEY,
            viewState
        )
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        subscribeObservers()
        setHasOptionsMenu(true)
        swipe_refresh.setOnRefreshListener(this)
        initRecyclerView()
    }

    private fun toolbarSetup() {
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        context?.let {
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        it, android.R.color.white
                    )
                )
            )
        }
        (activity as AppCompatActivity).app_bar?.elevation = 8F
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshFromCache()
    }

    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    private fun saveLayoutManagerState() {
        // check layoutManager state is null or not
        // Called when the LayoutManager should save its state. This is a good time to save your
        // scroll position, configuration and anything else that may be required to restore the same
        // layout state if the LayoutManager is recreated.
        global_list_recyclerview.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                recyclerAdapter.apply {
                    viewState.globalFields.countryList?.let {
                        Log.d(TAG, "LIST: $it")
                        submitList(
                            list = viewState.globalFields.countryList
                        )
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
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )
            }
        })
    }

    private fun initRecyclerView() {
        global_list_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@GlobalFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)
            recyclerAdapter = GlobalListAdapter(
                this@GlobalFragment
            )
            adapter = recyclerAdapter
        }
    }

    private fun initSearchView(menu: Menu) {
        activity?.apply {
            val searchManager: SearchManager = getSystemService(SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true
        }

        // ENTER ON COMPUTER KEYBOARD OR ARROW ON VIRTUAL KEYBOARD
        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText
        searchPlate.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery = v.text.toString()
                viewModel.setQuery(searchQuery).let {
                    onGlobalItemSearch()
                }
            }
            true
        }

        // SEARCH BUTTON CLICKED (in toolbar)
        val searchButton = searchView.findViewById(R.id.search_go_btn) as View
        searchButton.setOnClickListener {
            val searchQuery = searchPlate.text.toString()
            viewModel.setQuery(searchQuery).let {
                onGlobalItemSearch()
            }
        }
    }

    private fun onGlobalItemSearch() {
        viewModel.loadInitialList().let {
            resetUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        initSearchView(menu)
    }

    private fun resetUI() {
        global_list_recyclerview.smoothScrollToPosition(0)
        uiCommunicationListener.hideSoftKeyboard()
        focusable_view.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // clear references (can leak memory)
        global_list_recyclerview.adapter = null
    }

    override fun onRefresh() {
        onGlobalItemSearch()
        swipe_refresh.isRefreshing = false
    }

    override fun restoreListPosition() {
        viewModel.viewState.value?.globalFields?.layoutManagerState?.let { lmState ->
            global_list_recyclerview?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

}
