package com.example.choplaygroundkotlin.notelist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.adapters.NoteListAdapter
import com.example.choplaygroundkotlin.data.local.FolderItem
import com.example.choplaygroundkotlin.data.local.NoteItem
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

        val note = NoteItem(0, "n1")
        val folder = FolderItem(0, "n1")

        button_add_folder.setOnClickListener {
            //viewModel.insertNoteItem(note)
            viewModel.insertFolderItem(folder)
        }
    }

    private fun subscribeObservers() {
        viewModel.noteItems.observe(viewLifecycleOwner, Observer {
            Log.d("action", "notes: $it")
        })

        viewModel.folderItems.observe(viewLifecycleOwner, Observer {
            Log.d("action", "folders: $it")
        })
    }
}