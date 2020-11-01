package com.example.choplaygroundkotlin.di

import androidx.lifecycle.ViewModelProvider
import com.example.choplaygroundkotlin.framework.common.NoteViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Module
object NoteViewModelModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideNoteViewModelFactory(): ViewModelProvider.Factory{
        return NoteViewModelFactory()
    }

}