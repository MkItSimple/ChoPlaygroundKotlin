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
import com.example.choplaygroundkotlin.domain.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_list.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment @Inject constructor(
    val noteListAdapter: NoteListAdapter
) : Fragment(R.layout.fragment_note_list) {

    val viewModel: NoteListViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()

        button_add_folder.setOnClickListener {
            val folder = FolderCacheEntity(UUID.randomUUID().toString(), "f1")
            viewModel.insertFolderItem(folder)
        }

        button_add_note.setOnClickListener {
            val note = Note(UUID.randomUUID().toString(), "n1", "1fdf2f5f-d5e6-4dfb-ad89-43205da6c725")
            viewModel.insertNoteItem(note)
        }

        button_delete_all_notes.setOnClickListener {
            viewModel.deleteAllNotes()
            //viewModel.deleteAllFolders()
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
//            Log.d("action", "folders with notes: $it")
            if (it.isNotEmpty()){
//                val foldersWithNotesList = it
//                val targetValue = it[0].notes.size
//                Log.d("action", "folders with notes: $it")
//                Log.d("action", "notes count: $targetValue")
            } else {
                //viewModel.insertFolderItem(folder)
            }
        })
    }
}