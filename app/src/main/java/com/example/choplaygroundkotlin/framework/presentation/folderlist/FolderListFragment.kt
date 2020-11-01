package com.example.choplaygroundkotlin.framework.presentation.folderlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.choplaygroundkotlin.BaseApplication
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.di.AppComponent
import com.example.choplaygroundkotlin.framework.common.TopSpacingItemDecoration
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListToolbarState
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListToolbarState.*
import kotlinx.android.synthetic.main.fragment_folder_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class FolderListFragment
constructor(
        private val viewModelFactory: ViewModelProvider.Factory,
        private val dateUtil: DateUtil
) : Fragment(R.layout.fragment_folder_list),
        FolderListAdapter.Interaction,
        ItemTouchHelperAdapter
{
        val viewModel: FolderListViewModel by viewModels {
                viewModelFactory
        }

        private var listAdapter: FolderListAdapter? = null
        private var itemTouchHelper: ItemTouchHelper? = null
        private var folders = mutableListOf<Folder>()

        private var ids =  mutableListOf<String>()
        private var folder_names =  mutableListOf<String>()
        private var n_counts =  mutableListOf<Int>()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                getAppComponent()

                //listFilm = ArrayList()

                postToList()

                setupRecyclerView()
                setupSwipeRefresh()
//                setupFAB()
                subscribeObservers()
//                restoreInstanceState(savedInstanceState)

        }

        private fun addToList(id: String, folder_name: String, folder_counts: Int) {
                ids.add(id)
                folder_names.add(folder_name)
                n_counts.add(folder_counts)
        }

        private fun getAppComponent(): AppComponent {
                return activity?.run {
                        (application as BaseApplication).appComponent
                }?: throw Exception("AppComponent is null.")
        }

        private fun postToList() {
                for (i in 1..25){
                        addToList("ID $i", "Folder $i", 23)
                }
        }



        private fun subscribeObservers() {
                viewModel.toolbarState.observe(viewLifecycleOwner, Observer{ toolbarState ->
                        when(toolbarState){

                                is MultiSelectionState -> {
                                        //enableMultiSelectToolbarState()
                                        //disableSearchViewToolbarState()
                                        Log.d("action", "enableMultiSelectToolbarState")
                                }

                                is SearchViewState -> {
                                        //enableSearchViewToolbarState()
                                        //disableMultiSelectToolbarState()
                                }
                        }
                })

                for (i in 1..25){
                        folders.add(Folder("id $i", "f$i", 23))
                }
                listAdapter?.submitList(folders)
                listAdapter?.notifyDataSetChanged()
        }

        private fun setupRecyclerView() {
                recycler_view.apply {
                        layoutManager = LinearLayoutManager(activity)
                        val topSpacingDecorator = TopSpacingItemDecoration(20)
                        addItemDecoration(topSpacingDecorator)
                        itemTouchHelper = ItemTouchHelper(
                                NoteItemTouchHelperCallback(
                                        this@FolderListFragment,
                                        viewModel.folderListInteractionManager
                                )
                        )
                        listAdapter = FolderListAdapter(
                                this@FolderListFragment,
                                viewLifecycleOwner,
                                viewModel.folderListInteractionManager.selectedFolders,
                                dateUtil
                        )
                        itemTouchHelper?.attachToRecyclerView(this)

                        adapter = listAdapter
                }
        }

        private fun enableMultiSelectToolbarState(){
                view?.let { v ->
                        val view = View.inflate(
                                v.context,
                                R.layout.layout_multiselection_toolbar_folder,
                                null
                        )
                        view.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        toolbar_content_container.addView(view)
                        setupMultiSelectionToolbar(view)
                }
        }

        private fun setupMultiSelectionToolbar(parentView: View){
                parentView
                        .findViewById<ImageView>(R.id.action_exit_multiselect_state)
                        .setOnClickListener {
                                viewModel.setToolbarState(SearchViewState())
                        }

                parentView
                        .findViewById<ImageView>(R.id.action_delete_folders)
                        .setOnClickListener {
                                //deleteNotes()
                        }
        }

        private fun enableSearchViewToolbarState(){
                view?.let { v ->
                        val view = View.inflate(
                                v.context,
                                R.layout.layout_searchview_toolbar_folder,
                                null
                        )
                        view.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        toolbar_content_container.addView(view)
                        setupSearchView()
                        setupFilterButton()
                }
        }

        private fun disableMultiSelectToolbarState(){
                view?.let {
                        val view = toolbar_content_container
                                .findViewById<Toolbar>(R.id.multiselect_toolbar)
                        toolbar_content_container.removeView(view)
                        viewModel.clearSelectedNotes()
                }
        }

        private fun disableSearchViewToolbarState(){
                view?.let {
                        val view = toolbar_content_container
                                .findViewById<Toolbar>(R.id.searchview_toolbar)
                        toolbar_content_container.removeView(view)
                }
        }

        override fun isMultiSelectionModeEnabled()
                = viewModel.isMultiSelectionStateActive()

        override fun activateMultiSelectionMode()
                = viewModel.setToolbarState(MultiSelectionState())

        private fun setupSwipeRefresh(){
                swipe_refresh.setOnRefreshListener {
                        //startNewSearch()
                        //swipe_refresh.isRefreshing = false
                        Log.d("action", "SwipeRefresh")
                }
        }


        override fun onItemSelected(position: Int, item: Folder) {
                Log.d("action", "item: $position")
        }

        override fun restoreListPosition() {
                Log.d("action", "restoreListPosition")
        }

        override fun isFolderSelected(folder: Folder): Boolean {
                Log.d("action", "isFolderSelected")
                return true
        }

        override fun onItemSwiped(position: Int) {
                Log.d("action", "onItemSwiped")
        }
}