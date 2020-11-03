package com.example.choplaygroundkotlin.framework.presentation.folderlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.DialogInputCaptureCallback
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.framework.presentation.common.BaseNoteFragment
import com.example.choplaygroundkotlin.framework.presentation.common.TopSpacingItemDecoration
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListToolbarState.*
import com.example.choplaygroundkotlin.util.AndroidTestUtils
import com.example.choplaygroundkotlin.util.printLogD
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_folder_list.*
import kotlinx.coroutines.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class FolderListFragment
constructor(
        private val viewModelFactory: ViewModelProvider.Factory,
        private val dateUtil: DateUtil,
        private val firestore: FirebaseFirestore
) : BaseNoteFragment(R.layout.fragment_folder_list),
        FolderListAdapter.Interaction,
        ItemTouchHelperAdapter
{
        @Inject
        lateinit var androidTestUtils: AndroidTestUtils

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
                setupFAB()
                subscribeObservers()
//                restoreInstanceState(savedInstanceState)
        }


        private fun addToList(id: String, folder_name: String, folder_counts: Int) {
                ids.add(id)
                folder_names.add(folder_name)
                n_counts.add(folder_counts)
        }

        override fun inject() {
                getAppComponent().inject(this)
        }

        private fun postToList() {
                for (i in 1..25){
                        addToList("ID $i", "Folder $i", 5 + i)
                }
        }

        private fun subscribeObservers() {
                viewModel.toolbarState.observe(viewLifecycleOwner, Observer{ toolbarState ->
                        when(toolbarState){

                                is MultiSelectionState -> {
                                        enableMultiSelectToolbarState()
                                        disableSearchViewToolbarState()
                                        Log.d("action", "enableMultiSelectToolbarState")
                                }

                                is SearchViewState -> {
                                        enableSearchViewToolbarState()
                                        disableMultiSelectToolbarState()
                                }
                        }
                })

//                for (i in 1..25){
//                        folders.add(Folder("id $i", "f$i", 23))
//                }
                listAdapter?.submitList(folders)
                //listAdapter?.notifyDataSetChanged()
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
//                                deleteNotes()
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


        private fun setupSearchView(){

                val searchViewToolbar: Toolbar? = toolbar_content_container
                        .findViewById<Toolbar>(R.id.searchview_toolbar)

                searchViewToolbar?.let { toolbar ->

                        val searchView = toolbar.findViewById<SearchView>(R.id.search_view)

                        val searchPlate: SearchView.SearchAutoComplete?
                                = searchView.findViewById(androidx.appcompat.R.id.search_src_text)

                        // can't use QueryTextListener in production b/c can't submit an empty string
                        when{
                                androidTestUtils.isTest() -> {
//                                        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//                                                override fun onQueryTextSubmit(query: String?): Boolean {
//                                                        viewModel.setQuery(query)
//                                                        startNewSearch()
//                                                        return true
//                                                }
//
//                                                override fun onQueryTextChange(newText: String?): Boolean {
//                                                        return true
//                                                }
//
//                                        })
                                }

                                else ->{
                                        searchPlate?.setOnEditorActionListener { v, actionId, _ ->
                                                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                                                        || actionId == EditorInfo.IME_ACTION_SEARCH ) {
                                                        val searchQuery = v.text.toString()
//                                                        viewModel.setQuery(searchQuery)
                                                        startNewSearch()
                                                }
                                                true
                                        }
                                }
                        }
                }
        }

        private fun disableMultiSelectToolbarState(){
                view?.let {
                        val view = toolbar_content_container
                                .findViewById<Toolbar>(R.id.multiselect_toolbar)
                        toolbar_content_container.removeView(view)
//                        viewModel.clearSelectedNotes()
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

        private fun setupFAB(){
                add_new_note_fab.setOnClickListener {
//                        Log.d("action", "add_new_note_fab")

                        uiController.displayInputCaptureDialog(
                                getString(com.example.choplaygroundkotlin.R.string.text_enter_a_title),
                                object: DialogInputCaptureCallback {
                                        override fun onTextCaptured(text: String) {
                                                Log.d("action", "add_new_note_fab $text")
//                                                val newNote = viewModel.createNewNote(title = text)
//                                                viewModel.setStateEvent(
//                                                        InsertNewNoteEvent(
//                                                                title = newNote.title
//                                                        )
//                                                )
                                        }
                                }
                        )
                }
        }

        private fun startNewSearch(){
                printLogD("DCM", "start new search")
//                viewModel.clearList()
//                viewModel.loadFirstPage()
        }

        private fun setupSwipeRefresh(){
                swipe_refresh.setOnRefreshListener {
                        startNewSearch()
                        swipe_refresh.isRefreshing = false
                }
        }

        private fun setupFilterButton(){
                val searchViewToolbar: Toolbar? = toolbar_content_container
                        .findViewById<Toolbar>(R.id.searchview_toolbar)
                searchViewToolbar?.findViewById<ImageView>(R.id.action_filter)?.setOnClickListener {
                        showFilterDialog()
                }
        }

        private fun showFilterDialog() {
//                activity?.let {
//                        val dialog = MaterialDialog(it)
//                                .noAutoDismiss()
//                                .customView(R.layout.layout_filter)
//
//                        val view = dialog.getCustomView()
//
//                        val filter = viewModel.getFilter()
//                        val order = viewModel.getOrder()
//
//                        view.findViewById<RadioGroup>(R.id.filter_group).apply {
//                                when (filter) {
//                                        NOTE_FILTER_DATE_CREATED -> check(R.id.filter_date)
//                                        NOTE_FILTER_TITLE -> check(R.id.filter_title)
//                                }
//                        }
//
//                        view.findViewById<RadioGroup>(R.id.order_group).apply {
//                                when (order) {
//                                        NOTE_ORDER_ASC -> check(R.id.filter_asc)
//                                        NOTE_ORDER_DESC -> check(R.id.filter_desc)
//                                }
//                        }
//
//                        view.findViewById<TextView>(R.id.positive_button).setOnClickListener {
//
//                                val newFilter =
//                                        when (view.findViewById<RadioGroup>(R.id.filter_group).checkedRadioButtonId) {
//                                                R.id.filter_title -> NOTE_FILTER_TITLE
//                                                R.id.filter_date -> NOTE_FILTER_DATE_CREATED
//                                                else -> NOTE_FILTER_DATE_CREATED
//                                        }
//
//                                val newOrder =
//                                        when (view.findViewById<RadioGroup>(R.id.order_group).checkedRadioButtonId) {
//                                                R.id.filter_desc -> "-"
//                                                else -> ""
//                                        }
//
//                                viewModel.apply {
//                                        saveFilterOptions(newFilter, newOrder)
//                                        setNoteFilter(newFilter)
//                                        setNoteOrder(newOrder)
//                                }
//
//                                startNewSearch()
//
//                                dialog.dismiss()
//                        }
//
//                        view.findViewById<TextView>(R.id.negative_button).setOnClickListener {
//                                dialog.dismiss()
//                        }
//
//                        dialog.show()
//                }
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