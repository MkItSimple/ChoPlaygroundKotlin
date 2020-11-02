package com.example.choplaygroundkotlin.framework.presentation.common

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.framework.presentation.folderlist.FolderListFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class NoteFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val dateUtil: DateUtil,
    private val firestore: FirebaseFirestore
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            FolderListFragment::class.java.name -> {
                val fragment = FolderListFragment(viewModelFactory, dateUtil, firestore)
                fragment
            }

//            NoteListFragment::class.java.name -> {
//                val fragment = NoteListFragment(viewModelFactory, dateUtil)
//                fragment
//            }

//            NoteDetailFragment::class.java.name -> {
//                val fragment = NoteDetailFragment(viewModelFactory)
//                fragment
//            }
//
//            SplashFragment::class.java.name -> {
//                val fragment = SplashFragment(viewModelFactory)
//                fragment
//            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}