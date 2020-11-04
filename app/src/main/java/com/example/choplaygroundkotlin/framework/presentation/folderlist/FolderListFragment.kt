package com.example.choplaygroundkotlin.framework.presentation.folderlist

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
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
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.business.interactors.common.DeleteFolder.Companion.DELETE_FOLDER_PENDING
import com.example.choplaygroundkotlin.business.interactors.common.DeleteFolder.Companion.DELETE_FOLDER_SUCCESS
import com.example.choplaygroundkotlin.business.interactors.folderlist.DeleteMultipleFolders.Companion.DELETE_FOLDERS_ARE_YOU_SURE
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FOLDER_FILTER_DATE_CREATED
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FOLDER_FILTER_TITLE
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FOLDER_ORDER_ASC
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FOLDER_ORDER_DESC
import com.example.choplaygroundkotlin.framework.presentation.common.BaseNoteFragment
import com.example.choplaygroundkotlin.framework.presentation.common.TopSpacingItemDecoration
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListStateEvent.*
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListToolbarState.*
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import com.example.choplaygroundkotlin.util.AndroidTestUtils
import com.example.choplaygroundkotlin.util.TodoCallback
import com.example.choplaygroundkotlin.util.printLogD
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_folder_list.*
import kotlinx.coroutines.*
import javax.inject.Inject

const val FOLDER_LIST_STATE_BUNDLE_KEY = "com.codingwithmitch.cleannotes.notes.framework.presentation.folderlist.state"

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

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                viewModel.setupChannel()
                arguments?.let { args ->
                        args.getParcelable<Folder>(FOLDER_PENDING_DELETE_BUNDLE_KEY)?.let { note ->
                                viewModel.setFolderPendingDelete(note)
                                showUndoSnackBarDeleteFolder()
                                clearArgs()
                        }
                }
        }

        private fun showUndoSnackBarDeleteFolder(){
                uiController.onResponseReceived(
                        response = Response(
                                message = DELETE_FOLDER_PENDING,
                                uiComponentType = UIComponentType.SnackBar(
                                        undoCallback = object : SnackbarUndoCallback {
                                                override fun undo() {
                                                        viewModel.undoDelete()
                                                }
                                        },
                                        onDismissCallback = object: TodoCallback {
                                                override fun execute() {
                                                        // if the folder is not restored, clear pending folder
                                                        viewModel.setFolderPendingDelete(null)
                                                }
                                        }
                                ),
                                messageType = MessageType.Info()
                        ),
                        stateMessageCallback = object: StateMessageCallback{
                                override fun removeMessageFromStack() {
                                        viewModel.clearStateMessage()
                                }
                        }
                )
        }

        private fun clearArgs(){
                arguments?.clear()
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                setupRecyclerView()
                setupSwipeRefresh()
                setupFAB()
                subscribeObservers()
                restoreInstanceState(savedInstanceState)
        }

        private fun restoreInstanceState(savedInstanceState: Bundle?) {
                savedInstanceState?.let { inState ->
                        (inState[FOLDER_LIST_STATE_BUNDLE_KEY] as FolderListViewState?)?.let { viewState ->
                                viewModel.setViewState(viewState)
                        }
                }
        }

        override fun onResume() {
                super.onResume()
                viewModel.retrieveNumFoldersInCache()
                viewModel.clearList()
                viewModel.refreshSearchQuery()
        }

        override fun onPause() {
                super.onPause()
                saveLayoutManagerState()
        }

        override fun inject() {
                getAppComponent().inject(this)
        }

        private fun saveLayoutManagerState(){
                recycler_view.layoutManager?.onSaveInstanceState()?.let { lmState ->
                        viewModel.setLayoutManagerState(lmState)
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

                viewModel.viewState.observe(viewLifecycleOwner, Observer{ viewState ->

                        if(viewState != null){
                                viewState.folderList?.let { folderList ->
                                        if(viewModel.isPaginationExhausted()
                                                && !viewModel.isQueryExhausted()){
                                                viewModel.setQueryExhausted(true)
                                        }
                                        listAdapter?.submitList(folderList)
                                        listAdapter?.notifyDataSetChanged()
                                }

                                // a folder been inserted or selected
                                viewState.newFolder?.let { newFolder ->
                                        //navigateToDetailFragment(newFolder)
                                }

                        }
                })

                viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, Observer {
                        //printActiveJobs()
                        uiController.displayProgressBar(it)
                })

                viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
                        stateMessage?.let { message ->
                                if(message.response.message?.equals(DELETE_FOLDER_SUCCESS) == true){
                                        showUndoSnackBarDeleteFolder()
                                }
                                else{
                                        uiController.onResponseReceived(
                                                response = message.response,
                                                stateMessageCallback = object:
                                                        StateMessageCallback {
                                                        override fun removeMessageFromStack() {
                                                                viewModel.clearStateMessage()
                                                                viewModel.refreshSearchQuery()
                                                        }
                                                }
                                        )
                                }
                        }
                })
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
                                deleteFolders()
                        }
        }

        private fun deleteFolders(){
                viewModel.setStateEvent(
                        CreateStateMessageEvent(
                                stateMessage = StateMessage(
                                        response = Response(
                                                message = DELETE_FOLDERS_ARE_YOU_SURE,
                                                uiComponentType = UIComponentType.AreYouSureDialog(
                                                        object : AreYouSureCallback {
                                                                override fun proceed() {
                                                                        viewModel.deleteFolders()
                                                                }

                                                                override fun cancel() {
                                                                        // do nothing
                                                                }
                                                        }
                                                ),
                                                messageType = MessageType.Info()
                                        )
                                )
                        )
                )
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
                                        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                                                override fun onQueryTextSubmit(query: String?): Boolean {
                                                        Log.d("action", "onQueryTextSubmit")
                                                        viewModel.setQuery(query)
                                                        startNewSearch()
                                                        return true
                                                }

                                                override fun onQueryTextChange(newText: String?): Boolean {
                                                        Log.d("action", "onQueryTextChange")
                                                        return true
                                                }

                                        })
                                }

                                else ->{
                                        searchPlate?.setOnEditorActionListener { v, actionId, _ ->
                                                Log.d("action", "searching folder . . .")
                                                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                                                        || actionId == EditorInfo.IME_ACTION_SEARCH ) {
                                                        val searchQuery = v.text.toString()
                                                        viewModel.setQuery(searchQuery)
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
                        viewModel.clearSelectedFolders()
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

                        uiController.displayInputCaptureDialog(
                                getString(com.example.choplaygroundkotlin.R.string.text_enter_a_title),
                                object: DialogInputCaptureCallback {
                                        override fun onTextCaptured(text: String) {
                                                val newFolder = viewModel.createNewFolder(folder_name = text)
                                                viewModel.setStateEvent(
                                                        InsertNewFolderEvent(
                                                                folder_name = newFolder.folder_name
                                                        )
                                                )
                                        }
                                }
                        )
                }
        }

        private fun startNewSearch(){
                printLogD("DCM", "start new search")
                viewModel.clearList()
                viewModel.loadFirstPage()
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
                activity?.let {
                        val dialog = MaterialDialog(it)
                                .noAutoDismiss()
                                .customView(R.layout.layout_filter)

                        val view = dialog.getCustomView()

                        val filter = viewModel.getFilter()
                        val order = viewModel.getOrder()

                        view.findViewById<RadioGroup>(R.id.filter_group).apply {
                                when (filter) {
                                        FOLDER_FILTER_DATE_CREATED -> check(R.id.filter_date)
                                        FOLDER_FILTER_TITLE -> check(R.id.filter_title)
                                }
                        }

                        view.findViewById<RadioGroup>(R.id.order_group).apply {
                                when (order) {
                                        FOLDER_ORDER_ASC -> check(R.id.filter_asc)
                                        FOLDER_ORDER_DESC -> check(R.id.filter_desc)
                                }
                        }

                        view.findViewById<TextView>(R.id.positive_button).setOnClickListener {

                                val newFilter =
                                        when (view.findViewById<RadioGroup>(R.id.filter_group).checkedRadioButtonId) {
                                                R.id.filter_title -> FOLDER_FILTER_TITLE
                                                R.id.filter_date -> FOLDER_FILTER_DATE_CREATED
                                                else -> FOLDER_FILTER_DATE_CREATED
                                        }

                                val newOrder =
                                        when (view.findViewById<RadioGroup>(R.id.order_group).checkedRadioButtonId) {
                                                R.id.filter_desc -> "-"
                                                else -> ""
                                        }

                                viewModel.apply {
                                        saveFilterOptions(newFilter, newOrder)
                                        setFolderFilter(newFilter)
                                        setFolderOrder(newOrder)
                                }

                                startNewSearch()

                                dialog.dismiss()
                        }

                        view.findViewById<TextView>(R.id.negative_button).setOnClickListener {
                                dialog.dismiss()
                        }

                        dialog.show()
                }
        }


        override fun onItemSelected(position: Int, item: Folder) {
                if(isMultiSelectionModeEnabled()){
                        viewModel.addOrRemoveFolderFromSelectedList(item)
                }
                else{
                        viewModel.setFolder(item)
                }
        }

        override fun restoreListPosition() {
                viewModel.getLayoutManagerState()?.let { lmState ->
                        recycler_view?.layoutManager?.onRestoreInstanceState(lmState)
                }
        }

        override fun isFolderSelected(folder: Folder): Boolean {
//                return viewModel.isFolderSelected(folder)
                return true
        }

        override fun onItemSwiped(position: Int) {
                if(!viewModel.isDeletePending()){
                        listAdapter?.getFolder(position)?.let { folder ->
                                viewModel.beginPendingDelete(folder)
                        }
                }
                else{
                        listAdapter?.notifyDataSetChanged()
                }
        }
}