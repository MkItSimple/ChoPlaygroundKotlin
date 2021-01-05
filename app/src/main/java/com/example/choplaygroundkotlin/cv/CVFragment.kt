package com.example.choplaygroundkotlin.cv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.choplaygroundkotlin.R
import kotlinx.android.synthetic.main.fragment_cv.*


class CVFragment : Fragment() {

    private lateinit var adapter: CVAdapter
    private var cvItems = ArrayList<CVItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create a list of cv items
        cvItems.add(CVItem("20 April 2016", getString(R.string.lorem_text)))
        cvItems.add(CVItem("20 May 2016", getString(R.string.lorem_text2)))
        cvItems.add(CVItem("25 July 2016", getString(R.string.lorem_text)))
        cvItems.add(CVItem("20 April 2016", getString(R.string.lorem_text2)))
        adapter = CVAdapter(cvItems)
        rv_cv.let{
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }

    }
}