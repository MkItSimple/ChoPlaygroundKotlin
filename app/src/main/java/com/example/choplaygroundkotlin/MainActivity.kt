package com.example.choplaygroundkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.choplaygroundkotlin.framework.common.NoteFragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: NoteFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        setFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setFragmentFactory(){
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    private fun inject(){
        (application as BaseApplication).appComponent
            .inject(this)
    }
}