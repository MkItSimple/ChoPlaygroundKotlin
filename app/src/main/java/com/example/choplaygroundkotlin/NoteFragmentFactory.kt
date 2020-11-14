package com.example.choplaygroundkotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.choplaygroundkotlin.adapters.NoteListAdapter
import com.example.choplaygroundkotlin.notelist.NoteListFragment
import javax.inject.Inject

class NoteFragmentFactory @Inject constructor(
    private val noteListAdapter: NoteListAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            NoteListFragment::class.java.name -> NoteListFragment(noteListAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}