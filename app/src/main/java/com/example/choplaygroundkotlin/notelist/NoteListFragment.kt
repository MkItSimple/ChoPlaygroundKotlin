package com.example.choplaygroundkotlin.notelist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.adapters.NoteListAdapter
import com.example.choplaygroundkotlin.data.local.FolderCacheEntity
import com.example.choplaygroundkotlin.data.local.NoteCacheEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_list.*
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment @Inject constructor(
    val noteListAdapter: NoteListAdapter
) : Fragment(R.layout.fragment_note_list) {

    val viewModel: NoteListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()

        val note = NoteCacheEntity(0, "n1", 1)
        val folder = FolderCacheEntity(0, "n1")

        button_add_folder.setOnClickListener {
            viewModel.insertNoteItem(note)
//            viewModel.insertFolderItem(folder)
//            viewModel.deleteAll()
        }
    }

    private fun subscribeObservers() {
        viewModel.noteItems.observe(viewLifecycleOwner, Observer {
            Log.d("action", "notes: $it")
        })
        viewModel.folderItems.observe(viewLifecycleOwner, Observer {
            Log.d("action", "folders: $it")
        })
        viewModel.fetchFoldersWithNotes.observe(viewLifecycleOwner, Observer {
//            val foldersWithNotesList = it
//            val targetValue = it[0].notes.size
//            Log.d("action", "folders with notes: $it")
//            Log.d("action", "notes count: $targetValue")
        })
    }
}