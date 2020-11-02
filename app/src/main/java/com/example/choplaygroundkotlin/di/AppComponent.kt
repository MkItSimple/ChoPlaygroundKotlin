package com.example.choplaygroundkotlin.di

import com.example.choplaygroundkotlin.framework.presentation.BaseApplication
import com.example.choplaygroundkotlin.framework.presentation.folderlist.FolderListFragment
import com.example.choplaygroundkotlin.framework.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(
    modules = [
        ProductionModule::class,
        AppModule::class,
        NoteViewModelModule::class,
        NoteFragmentFactoryModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance app: BaseApplication): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(folderListFragment: FolderListFragment)
}
